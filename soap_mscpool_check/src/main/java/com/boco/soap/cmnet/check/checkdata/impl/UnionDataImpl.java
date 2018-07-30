package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.checkdata.IUnion;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.check.result.impl.CheckDataImpl;
import com.boco.soap.cmnet.check.result.impl.DataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UnionDataImpl  implements IUnion
{
    private List<ICheckData> curCheckDataList;
    private static final Logger log = LoggerFactory.getLogger(UnionDataImpl.class);

    private List<String> stdUnionFieldList = new ArrayList();

    public UnionDataImpl()
    {
    }

    public UnionDataImpl(List<ICheckData> curCheckDataList)
    {
        this.curCheckDataList = curCheckDataList;
    }

    public List<ICheckData> unionData(List<ICheckData> checkDatas)
    {
        Map dataMap = createParentMap(checkDatas);
        Iterator localIterator1;
        if ((this.curCheckDataList != null) && (this.curCheckDataList.size() > 0)) {
            Map<String, List<ICheckData>> curDataMap = createCurParentMap(this.curCheckDataList);
            for (localIterator1 = dataMap.entrySet().iterator(); localIterator1.hasNext(); ) {
                Map.Entry entry = (Map.Entry)localIterator1.next();
                String key = ((String)entry.getKey()).split("&")[0];
                List<ICheckData>  value = (List)entry.getValue();
                if (curDataMap.containsKey(key))
                    for (ICheckData checkData : curDataMap.get(key)) {
                        boolean flag = false;
                        for (ICheckData checkData1 : value) {
                            if (checkData1.getData().getCode().equals(checkData.getData().getCode())) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag)
                            ((List)entry.getValue()).add(checkData);
                    }
            }
        }

        Map<String, List<ICheckData>> mergDataMap = mergDataMap(dataMap);
        while (dataMap.size() != mergDataMap.size()) {
            dataMap = mergDataMap;
            mergDataMap = mergDataMap(dataMap);
        }
        List<ICheckData> mergeDatas = new ArrayList();
        for (String key : mergDataMap.keySet()) {
            List<ICheckData> datas = mergDataMap.get(key);
            for (ICheckData checkData : datas) {
                if (checkData.getDataType().equals(EnumCheckDataType.SOURCE)) {
                    ((List)mergeDatas).add(checkData);
                }
            }
        }

        return mergeDatas;
    }

    private Map<String, List<ICheckData>> createCurParentMap(List<ICheckData> curCheckDataList) {
        Map parentMap = new HashMap();
        for (ICheckData checkData : curCheckDataList) {
            String parentCode = checkData.getData().getCode();
            String parentKey = checkData.getData().getKey();
            if ((parentCode != null) && (!parentCode.equals(""))) {
                parentKey = parentKey.substring(0, parentKey.length() - 1);
                if (parentMap.containsKey(parentKey)) {
                    List parentDatas = (List)parentMap.get(parentKey);
                    parentDatas.add(checkData);
                } else {
                    List parentDatas = new ArrayList();
                    parentDatas.add(checkData);
                    parentMap.put(parentKey, parentDatas);
                }
            }

        }

        return parentMap;
    }

    private Map<String, List<ICheckData>> createParentMap(List<ICheckData> checkDatas) {
        Map parentMap = new HashMap();
        for (ICheckData checkData : checkDatas) {
            String parentCode = checkData.getData().getCode();

            String parentKey = checkData.getData().getKey();

            String insid = checkData.getData().getInstruction().getId();

            if ((parentCode != null) && (!parentCode.equals(""))) {
                parentKey = parentKey.substring(0, parentKey.length() - 1);
                if (parentMap.containsKey(parentKey + "&" + insid)) {
                    List parentDatas = (List)parentMap.get(parentKey + "&" + insid);
                    parentDatas.add(checkData);
                } else {
                    List parentDatas = new ArrayList();
                    parentDatas.add(checkData);
                    parentMap.put(parentKey + "&" + insid, parentDatas);
                }
            }

        }

        return parentMap;
    }

    private Map<String, List<ICheckData>> mergDataMap(Map<String, List<ICheckData>> dataMap)
    {
        Map mergDataMap = new HashMap();
        for (String quertykey : dataMap.keySet()) {
            String key = quertykey.split("&")[0];
            List oldData = (List)dataMap.get(quertykey);
            Map map = unionList(oldData);
            boolean isunion = ((Boolean)map.get("isunion")).booleanValue();
            List mergData = (List)map.get("unionList");
            if ((mergData.size() == 1) && (key.length() > 1) && (isunion)) {
                key = key.substring(0, key.length() - 1);
            }
            String newkey = key + "&" + quertykey.split("&")[1];
            if (mergDataMap.containsKey(newkey)) {
                List lastDatas = (List)mergDataMap.get(newkey);
                lastDatas.addAll(mergData);
                mergDataMap.put(newkey, lastDatas);
            } else {
                mergDataMap.put(newkey, mergData);
            }
        }

        return mergDataMap;
    }

    private Map<String, Object> unionList(List<ICheckData> oldData)
    {
        Map map = new HashMap();
        if (oldData.size() != 10) {
            boolean isunion = false;
            map.put("isunion", Boolean.valueOf(isunion));
            map.put("unionList", oldData);
            return map;
        }

        List oldunionlist = new ArrayList();
        ICheckData firstData = null;
        for (Iterator localIterator1 = oldData.iterator(); localIterator1.hasNext(); ) {
            ICheckData  checkData = (ICheckData)localIterator1.next();
            if (checkData.getDataType().equals(EnumCheckDataType.SOURCE)) {
                firstData = checkData;
                break;
            }
        }
        List<IInstructionParameter> instructionParameters = firstData.getData().getInstruction().getParams();
        for (IInstructionParameter param : instructionParameters) {
            if ((param.getStdunion() != null) && (param.getStdunion().equals("是"))) {
                this.stdUnionFieldList.add(param.getEnglishName());
            }
        }
        boolean compare = false;
        for (int i = 0; i < oldData.size(); i++) {
            ICheckData compareData = (ICheckData)oldData.get(i);
            compare = isSameData(firstData, compareData, this.stdUnionFieldList);
            if (compareData.isUnion()) {
                oldunionlist.addAll(compareData.getUnionCheckData());
            }
            if (!compare) {
                boolean isunion = false;
                map.put("isunion", Boolean.valueOf(isunion));
                map.put("unionList", oldData);
                return map;
            }

        }

        String oldCode = firstData.getData().getCode();
        String unionCode = oldCode.substring(0, oldCode.length() - 1);

        EnumCheckDataType dataType = firstData.getDataType();
        for (IDataItem item : firstData.getData().getItems()) {
            if (EnumFieldUsage.CODE_FILED.equals(item.getParam().getFieldUsage())) {
                item.setChineseValue(unionCode);
                item.setEnglishValue(unionCode);
            }

        }

        Object newDate = new DataImpl(firstData.getData().getItems(), firstData.getData().getStandData());
        ((IData)newDate).setInstruction(firstData.getData().getInstruction());

        ICheckData newCheckData = new CheckDataImpl((IData)newDate, dataType);

        if (oldunionlist.size() == 0) {
            oldunionlist = oldData;
        }
        newCheckData.setUnionCheckData(oldunionlist);

        List mergeDatas = new ArrayList();
        mergeDatas.add(newCheckData);

        boolean isunion = true;
        map.put("isunion", Boolean.valueOf(isunion));
        map.put("unionList", mergeDatas);

        return map;
    }

    private boolean isSameData(ICheckData firstData, ICheckData compareData, List<String> stdUnionFieldList) {
        firstData.getData().getItems().sort(new Comparator<IDataItem>()
        {
            public int compare(IDataItem o1, IDataItem o2) {
                int i = o1.getParamName().compareTo(o2.getParamName());
                return i;
            }
        });
        compareData.getData().getItems().sort(new Comparator<IDataItem>()
        {
            public int compare(IDataItem o1, IDataItem o2) {
                int i = o1.getParamName().compareTo(o2.getParamName());
                return i;
            }
        });
        boolean result = true;

        for (int i = 0; i < firstData.getData().getItems().size(); i++) {
            IDataItem firstDataItem = (IDataItem)firstData.getData().getItems().get(i);
            IDataItem compareDataItem = (IDataItem)compareData.getData().getItems().get(i);

            if (firstDataItem.getParam().getFieldUsage() != EnumFieldUsage.CODE_FILED) {
                if ((stdUnionFieldList != null) && (stdUnionFieldList.size() > 0) &&
                        (!stdUnionFieldList
                                .contains(compareDataItem
                                        .getParam().getParamName())))
                {
                    if (!firstDataItem.getChineseValue().equals(compareDataItem.getChineseValue())) {
                        firstDataItem.setChineseValue("");
                        compareDataItem.setChineseValue("");
                    }
                }
                else {
                    if (!firstDataItem.getParam().getParamName().equals(compareDataItem.getParam().getParamName())) {
                        log.info("要合并参数名--->" + compareDataItem.getParam().getParamName());
                        log.info("第一个参数名--->" + firstDataItem.getParam().getParamName());
                        result = false;
                    }
                    if (!firstDataItem.getChineseValue().equals(compareDataItem.getChineseValue())) {
                        log.info("要合并参数值--->" + compareDataItem.getChineseValue());
                        log.info("第一个参数值--->" + firstDataItem.getChineseValue());
                        result = false;
                    }
                }
            }
        }
        return result;
    }
}
