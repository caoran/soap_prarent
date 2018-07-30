package com.boco.soap.cmnet.check.pair.query.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.pair.query.*;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.check.result.impl.CheckDataImpl;
import com.boco.soap.cmnet.check.result.impl.DataImpl;
import com.boco.soap.cmnet.common.util.CheckExpansion;
import com.boco.soap.cmnet.common.util.CheckExpansionCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuzzyCodeQueryEngine implements IDataQueryEngine
{
    private static final Logger logger = LoggerFactory.getLogger(FuzzyCodeQueryEngine.class);

    private Map<String, ExpanDataEnitites> engineCheckDatas = new HashMap();
    private Map<String, ExpanDataEnitites> engineCheckCommonDatas = new HashMap();

    private List<String> tmplistArray = new ArrayList();

    @Override
    public DataQueryResult query(ICheckData sourceCheckData) {
        DataQueryResult result = null;

        String key = sourceCheckData.getData().getKey();

        if (logger.isDebugEnabled()) {
            logger.debug("配对数据原始key为[{}]", key);
        }

        if ((this.engineCheckDatas.containsKey(key)) &&
                (((ExpanDataEnitites)this.engineCheckDatas
                        .get(key))
                        .getData() != null))
        {
            if (logger.isDebugEnabled()) {
                logger.debug("原始key直接查找到结果。");
            }

            result = QueryResultFactory.getInstance().createQueryResult();

            ExpanDataEnitites queryData = (ExpanDataEnitites)this.engineCheckDatas.get(key);

            result = analyzeOnceResult(sourceCheckData, queryData);
        }
        else if ((this.engineCheckCommonDatas.containsKey(key)) &&
                (((ExpanDataEnitites)this.engineCheckCommonDatas
                        .get(key))
                        .getData() != null)) {
            if (logger.isDebugEnabled()) {
                logger.debug("原始code直接查找到结果。");
            }

            result = QueryResultFactory.getInstance().createQueryResult();
            ExpanDataEnitites queryData = (ExpanDataEnitites)this.engineCheckCommonDatas.get(key);

            result = analyzeOnceResult(sourceCheckData, queryData);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("原始key没有直接查找到结果。准备缩位查找");
            }
            result = abbreviatedQuery(sourceCheckData);

            if (!result.isHasResult()) {
                if (logger.isDebugEnabled()) {
                    logger.debug("原始key没有直接查找到结果。准备扩位查找");
                }
                result = expansionQuery(sourceCheckData, key);
            }
        }

        return result;
    }


    private DataQueryResult abbreviatedQuery(ICheckData sourceCheckData)
    {
        DataQueryResult result = QueryResultFactory.getInstance()
                .createQueryResult();
        result.setHasResult(false);

        IBusinessInstruction instruction = sourceCheckData.getData().getInstruction();



        boolean isFuzzy = instruction.isFuzzyCheck();

        if (!isFuzzy) {
            if (logger.isDebugEnabled()) {
                logger.debug("不允许模糊查找");
            }
            result.setHasResult(false);
        }
        else {
            int position = instruction.getPosition();
            String query = sourceCheckData.getData().getQuery();
            String code = sourceCheckData.getData().getCode();

            code = code.substring(0, code.length() - 1);

            while (code.length() >= position) {
                String key = "";
                if (query.equals(""))
                    key = code;
                else {
                    key = query + "|" + code;
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("新的查询主键为[{}]", key);
                }
                if ((this.engineCheckDatas.containsKey(key)) &&
                        (((ExpanDataEnitites)this.engineCheckDatas
                                .get(key))
                                .getData() != null))
                {
                    ExpanDataEnitites queryData = (ExpanDataEnitites)this.engineCheckDatas
                            .get(key);

                    if ((queryData != null) && (queryData.getData() != null))
                    {
                        ICheckData targetCheckData = queryData.getData();
                        String targetKey = targetCheckData.getData().getKey();
                        if (targetKey.equals(key)) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("查询结果唯一，且源数据与目标数据的核查主键一至");
                            }
                            result.setHasResult(true);
                            result.setQueryResult(queryData.getData());
                            result.setQueryType(EnumQueryType.ABBREVIATED);
                        } else {
                            if (logger.isDebugEnabled()) {
                                logger.debug("查询结果唯一，且源数据与目标数据的核查主键不一至");
                            }
                            result.setHasResult(false);
                        }

                        break;
                    }if (logger.isDebugEnabled()) {
                    logger.debug("查询结果不唯一");
                }
                    result.setHasResult(false);

                    break;
                }
                code = code.substring(0, code.length() - 1);
            }

        }

        return result;
    }

    private DataQueryResult expansionQuery(ICheckData sourceCheckData, String key)
    {
        DataQueryResult result = QueryResultFactory.getInstance()
                .createQueryResult();
        result.setHasResult(false);

        IBusinessInstruction instruction = sourceCheckData.getData()
                .getInstruction();
        boolean isFuzzy = instruction.isFuzzyCheck();

        if (!isFuzzy) {
            if (logger.isDebugEnabled()) {
                logger.debug("不允许模糊查找");
            }
            result.setHasResult(false);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("配对数据原始key为[{}]", key);
            }
            ExpanDataEnitites queryData = (ExpanDataEnitites)this.engineCheckDatas.get(key);
            if ((queryData != null) && (queryData.getListdata() != null)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("查找的结果个数为1个结果,且源数据与目标数据的key相同");
                }
                result.setHasResult(true);
                result.setQueryResult(queryData.getListdata());
                result.setQueryType(EnumQueryType.EXTEND);

                if ((sourceCheckData.getData().getCode() != null) &&
                        (!sourceCheckData
                                .getData().getCode().equals(""))) {
                    getStdExtCheckData(sourceCheckData, result, sourceCheckData
                            .getData().getCode());
                }

            }

        }

        return result;
    }

    private DataQueryResult analyzeOnceResult(ICheckData sourceCheckData, ExpanDataEnitites queryData)
    {
        DataQueryResult result = QueryResultFactory.getInstance()
                .createQueryResult();

        IBusinessInstruction instruction = sourceCheckData.getData()
                .getInstruction();
        boolean isFuzzy = instruction.isFuzzyCheck();
        if ((queryData != null) && (queryData.getData() != null))
        {
            ICheckData targetCheckData = queryData.getData();
            String targetKey = targetCheckData.getData().getKey();
            if (targetKey.equals(sourceCheckData.getData().getKey())) {
                if (logger.isDebugEnabled()) {
                    logger.debug("查找的结果个数为1个结果,且源数据与目标数据的key相同");
                }
                result.setHasResult(true);
                result.setQueryResult(queryData.getData());
                result.setQueryType(EnumQueryType.ACCURACY);
            }
            else if (isFuzzy) {
                if (logger.isDebugEnabled()) {
                    logger.debug("查找的结果个数为1个结果,源数据与目标数据的key不相同,允许模糊查询");
                }
                result.setHasResult(true);
                result.setQueryResult(queryData.getListdata());
                result.setQueryType(EnumQueryType.EXTEND);
            } else {
                if (logger.isDebugEnabled()) {
                    logger.debug("查找的结果个数为1个结果,源数据与目标数据的key不相同,不允许模糊查询");
                }
                result.setHasResult(false);
            }

        }
        else if (isFuzzy) {
            if (logger.isDebugEnabled()) {
                logger.debug("查找的结果个数不唯一,允许模糊查询");
            }
            result.setHasResult(true);
            result.setQueryResult(queryData.getListdata());
            result.setQueryType(EnumQueryType.EXTEND);
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("查找的结果个数不唯一,不允许模糊查询");
            }
            result.setHasResult(false);
        }

        return result;
    }

    public void setTargetCheckDatas(List<ICheckData> checkDatas)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("开始构造模糊查询目标数据结构");
        }
        this.engineCheckDatas = CheckExpansion.getexpansion(checkDatas);
    }

    public void setTargetCheckDatasCommon(List<ICheckData> checkDatas)
    {
        this.engineCheckCommonDatas = CheckExpansionCommon.getexpansion(checkDatas);
    }

    private DataQueryResult getStdExtCheckData(ICheckData sourceCheckData, DataQueryResult result, String code)
    {
        DataQueryResult extresult = result;

        List tmplist = new ArrayList();

        for (int i = 0; i <= 9; i++) {
            String newCode = code + i;
            ExpanDataEnitites expandDataEntites = (ExpanDataEnitites)this.engineCheckCommonDatas.get(newCode);
            if ((expandDataEntites != null) && (expandDataEntites.getListdata() != null) && (expandDataEntites.getListdata().size() > 0)) {
                tmplist.add(Integer.valueOf(i));
                this.tmplistArray.add(newCode);
            }
        }
        if ((tmplist.size() != 10) && (tmplist.size() != 0))
        {
            for (int k = 0; k < 10; k++) {
                String lostcode = code + k;
                if ((!tmplist.contains(Integer.valueOf(k))) && (!this.tmplistArray.contains(lostcode))) {
                    tmplist = new ArrayList();
                    ICheckData newGenerateCheckData = generateSourceCheckData(sourceCheckData, lostcode);
                    extresult.setStdDataResult(newGenerateCheckData);
                    getStdExtCheckData(newGenerateCheckData, extresult, lostcode);
                }
            }
        }
        return extresult;
    }

    private ICheckData generateSourceCheckData(ICheckData sourceCheckData, String newCode)
    {
        EnumCheckDataType dataType = sourceCheckData.getDataType();
        for (IDataItem item : sourceCheckData.getData().getItems()) {
            if (EnumFieldUsage.CODE_FILED.equals(item.getParam()
                    .getFieldUsage())) {
                item.setChineseValue(newCode);
                item.setEnglishValue(newCode);
            }
        }

        Object newDate = new DataImpl(sourceCheckData.getData().getItems(), sourceCheckData
                .getData().getStandData());
        ((IData)newDate).setInstruction(sourceCheckData.getData().getInstruction());
        ICheckData newCheckData = new CheckDataImpl((IData)newDate, dataType);

        for (IDataItem itemNew : newCheckData.getData().getItems()) {
            if (EnumFieldUsage.CODE_FILED.equals(itemNew.getParam()
                    .getFieldUsage())) {
                itemNew.setChineseValue(newCode);
                itemNew.setEnglishValue(newCode);
            }
        }
        return newCheckData;
    }
}
