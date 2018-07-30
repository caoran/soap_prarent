package com.boco.soap.cmnet.check.pair.impl;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.pair.DataPairConfig;
import com.boco.soap.cmnet.check.pair.ICheckDataPair;
import com.boco.soap.cmnet.check.pair.query.DataQueryFactory;
import com.boco.soap.cmnet.check.pair.query.DataQueryResult;
import com.boco.soap.cmnet.check.pair.query.EnumQueryType;
import com.boco.soap.cmnet.check.pair.query.IDataQueryEngine;
import com.boco.soap.cmnet.check.result.ICheckData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CodeDataPairImpl implements ICheckDataPair {

    private final Logger logger = LoggerFactory.getLogger(CodeDataPairImpl.class);


    @Override
    public List<ICheckData> dataPair(List<ICheckData> sourceCheckDatas, List<ICheckData> targetCheckDatas, DataPairConfig dataPairConfig, Object extObj)
    {
        List checkstddata = new ArrayList();

        IDataQueryEngine queryEngine = DataQueryFactory.getInstance()
                .getDataQueryEngine(dataPairConfig, EnumFullPartCheck.FULLCHECK);

        queryEngine.setTargetCheckDatas(targetCheckDatas);
        queryEngine.setTargetCheckDatasCommon(targetCheckDatas);

        for (ICheckData sourceCheckData : sourceCheckDatas)
        {
            DataQueryResult queryResult = queryEngine.query(sourceCheckData);

            if (queryResult.isHasResult())
            {
                List queryResultDatas = queryResult.getQueryResult();
                EnumQueryType queryType = queryResult.getQueryType();

                setRelation(queryType, sourceCheckData, queryResultDatas, dataPairConfig);
            }

            if ((queryResult.getResultStdData() != null) && (queryResult.getResultStdData().size() > 0))
            {
                checkstddata.addAll(queryResult.getResultStdData());
            }
        }

        checkstddata.addAll(sourceCheckDatas);
        return checkstddata;
    }

    private void setRelation(EnumQueryType queryType, ICheckData sourceCheckData, List<ICheckData> queryResultDatas, DataPairConfig dataPairConfig)
    {
        int isCombine = dataPairConfig.isCombine;
        if (this.logger.isDebugEnabled())
            this.logger.debug("key:{}", sourceCheckData.getData().getKey());
        String code;
        if (EnumQueryType.ACCURACY.equals(queryType))
        {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("精确查找，核查源数据[{}] , 核查目标数据[{}]]", sourceCheckData.getData().getKey(), ((ICheckData)queryResultDatas.get(0)).getData().getKey());
            }

            sourceCheckData.setKeyEquData((ICheckData)queryResultDatas.get(0));
            ((ICheckData)queryResultDatas.get(0)).setKeyEquData(sourceCheckData);
        }
        else if (EnumQueryType.ABBREVIATED.equals(queryType)) {
            if (dataPairConfig.isAllowAbbreviated()) {
                code = sourceCheckData.getData().getInstruction().getId();
                boolean isunionFuzzyPair = isunionFuzzyPair(code);
                if (isunionFuzzyPair) {
                    if ((sourceCheckData.isUnion()) && (isCombine == 1))
                    {
                        if (this.logger.isDebugEnabled()) {
                            this.logger.debug("缩位查找，核查源数据[{}] , 核查目标数据[{}]]", sourceCheckData.getData().getKey(), ((ICheckData)queryResultDatas.get(0)).getData().getKey());
                        }
                        sourceCheckData.setKeyAbbrData((ICheckData)queryResultDatas.get(0));
                        ((ICheckData)queryResultDatas.get(0)).setKeyExtDatas(sourceCheckData);
                    }
                } else {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("缩位查找，核查源数据[{}] , 核查目标数据[{}]]", sourceCheckData.getData().getKey(), ((ICheckData)queryResultDatas.get(0)).getData().getKey());
                    }
                    sourceCheckData.setKeyAbbrData((ICheckData)queryResultDatas.get(0));
                    ((ICheckData)queryResultDatas.get(0)).setKeyExtDatas(sourceCheckData);
                }
            }
            else {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("二次核查全量精确查找，核查源数据[{}] , 核查目标数据[{}]]", sourceCheckData.getData().getKey(), ((ICheckData)queryResultDatas.get(0)).getData().getKey());
                }
                sourceCheckData.setKeyEquData((ICheckData)queryResultDatas.get(0));
                ((ICheckData)queryResultDatas.get(0)).setKeyEquData(sourceCheckData);
            }
        } else if (EnumQueryType.EXTEND.equals(queryType))
        {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("扩位查找，核查源数据[{}]", sourceCheckData.getData().getKey());
            }

            sourceCheckData.setKeyExtDatas(queryResultDatas);

            for (ICheckData checkData : queryResultDatas) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("扩位查找，核查目标数据[{}]", checkData.getData().getKey());
                }
                checkData.setKeyAbbrData(sourceCheckData);
            }
        }
    }

    private boolean isunionFuzzyPair(String instructionid)
    {
        boolean isunionFuzzyPair = false;
        String unionFuzzyPairid = "";//SpringPropertyResourceReader.getProperty("unionFuzzyPairid");

        String inscode = "";

        if ((instructionid != null) && (instructionid.length() > 6))
        {
            inscode = instructionid.substring(0, 6);
        }
        else {
            return isunionFuzzyPair;
        }

        if (unionFuzzyPairid != null)
        {
            String[] solids = unionFuzzyPairid.split(",");
            for (String str : solids)
            {
                if (str.trim().equals(inscode))
                {
                    isunionFuzzyPair = true;
                    break;
                }
            }
        }

        return isunionFuzzyPair;
    }
}
