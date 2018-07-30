package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.BusiDictItem;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.beans.enums.EnumParamType;
import com.boco.soap.cmnet.check.checkdata.*;
import com.boco.soap.cmnet.check.checkdata.paramchange.IParamChange;
import com.boco.soap.cmnet.check.exception.CreateStdDataException;
import com.boco.soap.cmnet.check.instruction.CheckOperateMnum;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.check.result.IDataParamDefine;
import com.boco.soap.cmnet.check.result.impl.CheckDataImpl;
import com.boco.soap.cmnet.check.result.impl.DataImpl;
import com.boco.soap.cmnet.check.result.impl.DataItemImpl;
import com.boco.soap.cmnet.check.result.impl.DataParamDefineImpl;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.pojo.MatchResultElement;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SourceDatasGeneratorImpl implements ISourceDatasGenerator {

    private static final Logger log = LoggerFactory.getLogger(SourceDatasGeneratorImpl.class)
            ;
    private Map<String, ICheckData> map = new ConcurrentHashMap<>();

    public Map<String, ICheckData> createSourceCheckDatas(BusinessSubTaskContext subTaskContext, String[] neName, BusiDict busiDict, String resultKey, Object obj)
    {
        IBusinessCurNetData businessCurNetData = (IBusinessCurNetData) SpringContextHolder.getBean("businessCurNetDataImpl");
        List<Map<String, String>> standDatas = businessCurNetData.getStdDataInfo(busiDict,neName);
        //ICreateSourceDatasConfig createSourceDatasConfig = CreateSourceDatasContextFactory.getInstance().createConfig(instruction, ne, "");

        if (log.isInfoEnabled()) {
            log.info("标准值生成器准备根据标准数据生成标准值，标准数据共有：{}", Integer.valueOf(standDatas.size()));
        }
        Map mapReturn = new HashMap();

       for (Map standData : standDatas)
        {
            List<ICheckData> temp = null;
            try {
                temp = createOneSourceCheckData(subTaskContext, standData, busiDict, "");

                for (ICheckData data : temp) {
                    //data.getData().setInstruction(instruction);

                    if (!this.map.containsKey(data.getData().getKey() + resultKey)) {
                        mapReturn.put(data.getData().getKey(), data);
                    }
                    this.map.put(data.getData().getKey() + resultKey, data);
                }
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("createOneSourceCheckData 出现异常", e);
                    throw new RuntimeException("createOneSourceCheckData 出现异常", e);
                }

            }

        }

        if (log.isInfoEnabled()) {
            log.info("生成的标准值有：{}个", Integer.valueOf(this.map.size()));
        }

        return mapReturn;
    }

    private List<ICheckData> createOneSourceCheckData(BusinessSubTaskContext subTaskContext, Map<String, String> standData, BusiDict config, String dbFile)
    {
        List<ICheckData> result = new ArrayList();

        List<BusiDictItem> params = config.getBusiDictItems();

        boolean discard = false;

        boolean rangeFlag = false;
        List<IDataItem> items=new ArrayList();
        for (int index = 0; index < params.size(); index++) {
            BusiDictItem param = params.get(index);
            Map map = new HashMap();
            if (EnumFieldUsage.valueOf(param.getParamProperty()).equals(EnumFieldUsage.CODE_FILED)) {
                map.put("englishValue", standData.get(param.getStdName()));
                map.put("chineseValue", standData.get(param.getStdName()));
                map.put("englishName",param.getStdName());
                map.put("chineseName",param.getStdName());
            }else{
                map.put("englishValue", "[NULL]");
                map.put("chineseValue", "[NULL]");
                map.put("englishName",param.getStdName());
                map.put("chineseName",param.getStdName());
            }
            IDataParamDefine instruParam=new DataParamDefineImpl(0, param.getParamName(),EnumFieldUsage.valueOf(param.getParamProperty()));
            items.add(new DataItemImpl(instruParam, CheckOperateMnum.getCheckOperateMnum(param.getCheckLogic()),map));
        }
        result.add(new CheckDataImpl(new DataImpl(items),EnumCheckDataType.SOURCE));

        return result;
    }


    private IDataItem getGuuidIDataItem(String guuidValue) {
        IDataParamDefine curDataParaDefine = new DataParamDefineImpl(10000, "GUUID", EnumFieldUsage.MAKE_FIELD);

        IDataItem dataItemRe = new DataItemImpl(curDataParaDefine, guuidValue, guuidValue);

        return dataItemRe;
    }
}
