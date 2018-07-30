package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.beans.enums.EnumParamType;
import com.boco.soap.cmnet.check.checkdata.*;
import com.boco.soap.cmnet.check.checkdata.paramchange.IParamChange;
import com.boco.soap.cmnet.check.exception.CreateStdDataException;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.check.result.IDataParamDefine;
import com.boco.soap.cmnet.check.result.impl.DataItemImpl;
import com.boco.soap.cmnet.check.result.impl.DataParamDefineImpl;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.pojo.MatchResultElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SourceDatasGeneratorImpl implements ISourceDatasGenerator {

    private static final Logger log = LoggerFactory.getLogger(SourceDatasGeneratorImpl.class)
            ;
    private Map<String, ICheckData> map = new ConcurrentHashMap<>();

    public Map<String, ICheckData> createSourceCheckDatas(BusinessSubTaskContext subTaskContext, MatchResultElement element, IBusinessInstruction instruction, String resultKey, Object obj)
    {
        Ne ne = subTaskContext.getNe();
        List<Map<String, String>> standDatas = element.getStandDatas();
        ICreateSourceDatasConfig createSourceDatasConfig = CreateSourceDatasContextFactory.getInstance().createConfig(instruction, ne, "");

        if (log.isInfoEnabled()) {
            log.info("标准值生成器准备根据标准数据生成标准值，标准数据共有：{}", Integer.valueOf(standDatas.size()));
        }
        Map mapReturn = new HashMap();

        for (Map standData : standDatas)
        {
            List<ICheckData> temp = null;
            try {
                temp = createOneSourceCheckData(subTaskContext, standData, createSourceDatasConfig, "");

                for (ICheckData data : temp) {
                    data.getData().setInstruction(instruction);

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

    private List<ICheckData> createOneSourceCheckData(BusinessSubTaskContext subTaskContext, Map<String, String> standData, ICreateSourceDatasConfig config, String dbFile)
    {
        List result = new ArrayList();

        List params = config.getInstruction()
                .getParams();

        List dynamics = new ArrayList();
        Map non_dynamics = new HashMap();

        boolean discard = false;

        boolean rangeFlag = false;

        for (int index = 0; index < params.size(); index++) {
            IInstructionParameter param = (IInstructionParameter)params.get(index);
            if ((param.getFieldUsage().equals(EnumFieldUsage.RANGE_UP)) || (param.getFieldUsage() == EnumFieldUsage.RANGE_DOWN)) {
                rangeFlag = true;
            }
            IParamChange paramChange = config.getParamChange(index);
            IValueInvoke chineseInvoke = config.getChineseValueInvoke(index);
            IValueInvoke englishInvoke = config.getEnglishValueInvoke(index);
            String[] englishValue = null;
            String[] chineseValue = null;

            if (param.getParamtype().equals(EnumParamType.CONSTANT)) {
                chineseValue = new String[] { param.getChineseValue() };
                englishValue = new String[] { param.getEnglishValue() };
            }
            else if (chineseInvoke == englishInvoke) {
                englishValue = englishInvoke.getValues(config
                        .getNeElement(), param, standData, dbFile);
                chineseValue = englishValue;
            } else {
                chineseValue = chineseInvoke.getValues(config
                        .getNeElement(), param, standData, dbFile);
                englishValue = englishInvoke.getValues(config
                        .getNeElement(), param, standData, dbFile);
            }

            if ((chineseValue == null) || (englishValue == null)) {
                if (log.isInfoEnabled()) {
                    log.info("第[" + index + "]变量返回值为：null,此标准数据丢弃,标准数据为：" + standData);
                }

                discard = true;
                break;
            }
            if (chineseValue.length == englishValue.length)
            {
                if (EnumFieldUsage.DYNAMIC_FIELD.equals(param.getFieldUsage()))
                {
                    IDataItem[] items = new IDataItem[chineseValue.length];
                    for (int i = 0; i < chineseValue.length; i++) {
                        Map map = new HashMap();
                        map.put("englishValue", paramChange.format(englishValue[i]));
                        map.put("chineseValue", paramChange.format(chineseValue[i]));
                        items[i] = CheckDataFactory.getInstance().getDataItem(param, map, null);
                    }
                    dynamics.add(items);
                }
                else if (EnumFieldUsage.CODE_FILED.equals(param.getFieldUsage()))
                {
                    IDataItem[] items = new IDataItem[chineseValue.length];
                    for (int i = 0; i < chineseValue.length; i++) {
                        Map map = new HashMap();
                        map.put("englishValue", paramChange.format(englishValue[i]));
                        map.put("chineseValue", paramChange.format(chineseValue[i]));
                        items[i] = CheckDataFactory.getInstance().getDataItem(param, map, null);
                    }
                    dynamics.add(items);
                }
                else {
                    Map map = new HashMap();
                    map.put("englishValue", paramChange.format(englishValue[0]));
                    map.put("chineseValue", paramChange.format(chineseValue[0]));

                    IDataItem item = CheckDataFactory.getInstance()
                            .getDataItem(param, map, null);

                    non_dynamics.put(param.getEnglishName(), item);
                }
            }
            else {
                throw new CreateStdDataException("生成CheckDataItem时变量生成的标准值个数不一致");
            }

        }

        if (rangeFlag) {
            if (subTaskContext.getCheckType() == EnumFullPartCheck.FULLCHECK)
                subTaskContext.setCheckType(EnumFullPartCheck.RANGECHECK_FULLCHECK);
            else if (subTaskContext.getCheckType() == EnumFullPartCheck.PARTCHECK) {
                subTaskContext.setCheckType(EnumFullPartCheck.RANGECHECK_PARTCHECK);
            }
        }
        if (!discard) {
            boolean isExitNet = getExitNet(standData);
            if (dynamics.size() != 0)
                result = getdynamicsData(non_dynamics, dynamics, subTaskContext, config, standData);
            else {
                result = getResultByMap(non_dynamics, config
                        .getInstruction().getParams(), isExitNet, standData);
            }
        }
        return result;
    }

    private List<ICheckData> getdynamicsData(Map<String, IDataItem> non_dynamics, List<IDataItem[]> dynamics, BusinessSubTaskContext subTaskContext, ICreateSourceDatasConfig config, Map<String, String> standData)
    {
        String dbFile = subTaskContext.getDbFile();

        List<Map<String, IDataItem>> tempResult = descartes(dynamics);

        Iterator entryIt = non_dynamics.entrySet().iterator();
        while (entryIt.hasNext()) {
            Map.Entry  entry = (Map.Entry)entryIt.next();
            for (Map map : tempResult) {
                map.put(entry.getKey(), entry.getValue());
            }
        }

       /* Map mapRelation = subTaskContext.getDynamicInterfaceClass();
        INeElement neObj = subTaskContext.getNe();
        String solutionStr = (String)standData.get("SOLUTION_ID");
        if ((mapRelation != null) || (mapRelation.size() > 0)) {
            for (String keyArray : mapRelation.keySet()) {
                if ((solutionStr != null) && (solutionStr.equals(keyArray))) {
                    try {
                        log.info("获取solutionStr--->>>" + solutionStr);

                        IValueDynamicInvoke valueDynamicInvoke = null;
                        Class clazz = Class.forName((String)mapRelation.get(keyArray));
                        valueDynamicInvoke = (IValueDynamicInvoke)clazz.newInstance();
                        if (valueDynamicInvoke != null)
                        {
                            log.info("加载动态类--->>>" + solutionStr);
                            tempResult = valueDynamicInvoke.getDynamicValues(neObj, standData, dbFile, tempResult);
                        }
                    } catch (Exception e) {
                        log.error("动态类加载异常--->>>" + (String)mapRelation.get(keyArray));
                        e.printStackTrace();
                    }
                }
            }
        }*/

        boolean isExitNet = getExitNet(standData);

        List result = getResultByList(tempResult, config
                .getInstruction().getParams(), isExitNet, standData);

        return result;
    }

    private boolean getExitNet(Map<String, String> standData)
    {
        String exitNet = (String)standData.get("BUSI_SCENARIO");

        boolean isExitNet = false;

        if ((exitNet != null) && (exitNet.equals("是")))
        {
            isExitNet = true;
        }

        return isExitNet;
    }

    private List<ICheckData> getResultByMap(Map<String, IDataItem> map, List<IInstructionParameter> params, boolean isExitNet, Map<String, String> standData)
    {
        List items = new ArrayList();

        String rangeCheckUp = "";
        String rangeCheckLow = "";

        for (IInstructionParameter param : params)
        {
            if (param.getFieldUsage() == EnumFieldUsage.RANGE_UP) {
                IDataItem idataItem = (IDataItem)map.get(param.getEnglishName());
                rangeCheckUp = idataItem.getEnglishValue();
            }

            if (param.getFieldUsage() == EnumFieldUsage.RANGE_DOWN) {
                IDataItem idataItem = (IDataItem)map.get(param.getEnglishName());
                rangeCheckLow = idataItem.getEnglishValue();
            }

            items.add(map.get(param.getEnglishName()));
        }

        items.sort(new Comparator<IDataItem>()
        {
            public int compare(IDataItem o1, IDataItem o2) {
                int i = o1.getParamName().compareTo(o2.getParamName());
                return i;
            }
        });
        ICheckData checkData = CheckDataFactory.getInstance()
                .getCheckRangeData(EnumCheckDataType.SOURCE, items, isExitNet, standData, rangeCheckUp, rangeCheckLow);

        List result = new ArrayList();

        result.add(checkData);

        return result;
    }

    private List<ICheckData> getResultByList(List<Map<String, IDataItem>> itemsList, List<IInstructionParameter> params, boolean isExitNet, Map<String, String> standData)
    {
        List result = new ArrayList();
        for (Map items : itemsList) {
            result.addAll(getResultByMap(items, params, isExitNet, standData));
        }

        return result;
    }

    private List<Map<String, IDataItem>> descartes(List<IDataItem[]> dynamics)
    {
        List result = new ArrayList();

        if (dynamics.size() == 1)
        {
            for (IDataItem singleItem : (IDataItem[])dynamics.get(0)) {
                Map map = new HashMap();
                map.put(singleItem.getParamName(), singleItem);
                result.add(map);
            }
        }else{
            result = getdynamicsDataitems(dynamics);
        }

        return result;
    }

    private List<Map<String, IDataItem>> getdynamicsDataitems(List<IDataItem[]> dynamics) {
        List<Map<String, IDataItem>> result = new ArrayList();

        for (IDataItem[] items : dynamics)
        {
            if (result.size() == 0) {
                for (IDataItem singleItem : items) {
                    Map map = new HashMap();
                    map.put(singleItem.getParamName(), singleItem);
                    result.add(map);
                }
            } else {
                List<Map<String, IDataItem>> result_Temp = new ArrayList();
                for (Map<String, IDataItem> map : result) {
                    IDataItem[]  singleItem = items;
                   int  mapLength = singleItem.length;
                    for (int localMap1 = 0; localMap1 < mapLength; localMap1++) {
                        IDataItem item = singleItem[localMap1];
                        Map<String, IDataItem> tempMap = new HashMap();

                        Iterator entryIt = map.entrySet().iterator();
                        while (entryIt.hasNext()) {
                            Map.Entry<String, IDataItem> entry = (Map.Entry)entryIt.next();
                            tempMap.put(entry.getKey(), entry.getValue());
                        }
                        tempMap.put(item.getParamName(), item);
                        result_Temp.add(tempMap);
                    }
                }
                result = result_Temp;
            }
        }
        return result;
    }

    private IDataItem getGuuidIDataItem(String guuidValue) {
        IDataParamDefine curDataParaDefine = new DataParamDefineImpl(10000, "GUUID", EnumFieldUsage.MAKE_FIELD);

        IDataItem dataItemRe = new DataItemImpl(curDataParaDefine, guuidValue, guuidValue);

        return dataItemRe;
    }
}
