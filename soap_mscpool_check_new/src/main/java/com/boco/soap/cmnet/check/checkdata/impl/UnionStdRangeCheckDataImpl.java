package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.checkdata.IUnion;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UnionStdRangeCheckDataImpl
        implements IUnion
{
    private static final Logger log = LoggerFactory.getLogger(UnionStdRangeCheckDataImpl.class);

    private List<String> stdUnionFieldList = new ArrayList();

    public List<ICheckData> unionData(List<ICheckData> checkDatas)
    {
        Map temp = new HashMap();
        Collections.sort(checkDatas, new Comparator<ICheckData>()
        {
            public int compare(ICheckData o1, ICheckData o2) {
                return o1.getRangeCheckedBeg().compareTo(o2.getRangeCheckedBeg());
            }
        });
        for (ICheckData checkData : checkDatas) {
            String begin = checkData.getRangeCheckedBeg();
            String end = checkData.getRangeCheckedEnd();
            if (temp.containsKey(Long.valueOf(Long.parseLong(begin) - 1L))) {
                ICheckData old = (ICheckData)temp.get(Long.valueOf(Long.parseLong(begin) - 1L));
                old.setRangeCheckedEnd(end);

                IData data = old.getData();
                //data.setRangeCheckedEnd(end);
                for (IDataItem dataItem : data.getItems()) {
                    if (dataItem.getParam().getFieldUsage().equals(EnumFieldUsage.RANGE_DOWN)) {
                        dataItem.setChineseValue(end);
                        dataItem.setEnglishValue(end);
                    }
                }
                temp.remove(Long.valueOf(Long.parseLong(begin) - 1L));
                temp.put(Long.valueOf(Long.parseLong(end)), old);
            } else {
                temp.put(Long.valueOf(Long.parseLong(end)), checkData);
            }
        }

        List checkDataList = temp.size() > 0 ? new ArrayList(temp.values()) : null;
        return checkDataList;
    }

    private Map<String, List<ICheckData>> createParentMap(List<ICheckData> checkDatas)
    {
        Map parentMap = new HashMap();
        for (ICheckData checkData : checkDatas) {
            String rangeCheckedBeg = String.valueOf(checkData.getData().getRangeCheckedBeg());
            if ((rangeCheckedBeg != null) && (!rangeCheckedBeg.equals(""))) {
                if (parentMap.containsKey(rangeCheckedBeg)) {
                    List parentDatas = (List)parentMap.get(rangeCheckedBeg);
                    parentDatas.add(checkData);
                } else {
                    List parentDatas = new ArrayList();
                    parentDatas.add(checkData);
                    parentMap.put(rangeCheckedBeg, parentDatas);
                }
            }
        }
        return parentMap;
    }

    private Map<String, List<ICheckData>> mergDataMap(Map<String, List<ICheckData>> dataMap)
    {
        Map mergDataMap = new HashMap();

        for (String beginCode : dataMap.keySet()) {
            for (ICheckData stdData : dataMap.get(beginCode)) {
                Long beginCodeLong = Long.valueOf(Long.valueOf(stdData.getData().getRangeCheckedBeg()).longValue() + 1L);
                String newBeginCodeStr = String.valueOf(beginCodeLong);
                if (dataMap.containsKey(newBeginCodeStr)) {
                    List newCheckList = (List)dataMap.get(newBeginCodeStr);
                    newCheckList.add(stdData);
                    mergDataMap.put(newBeginCodeStr, newCheckList);
                    if (mergDataMap.get(stdData.getData().getRangeCheckedBeg()) != null) {
                        mergDataMap.remove(stdData.getData().getRangeCheckedBeg());
                    }
                }
                else if (mergDataMap.containsKey(stdData.getData().getRangeCheckedBeg())) {
                    List newList = (List)mergDataMap.get(stdData.getData().getRangeCheckedBeg());
                    newList.add(stdData);
                    mergDataMap.put(String.valueOf(stdData.getData().getRangeCheckedBeg()), newList);
                } else {
                    List newList = new ArrayList();
                    newList.add(stdData);
                    mergDataMap.put(String.valueOf(stdData.getData().getRangeCheckedBeg()), newList);
                }
            }
        }

        return mergDataMap;
    }

    private List<ICheckData> unionList(Map<String, List<ICheckData>> oldData) {
        List icheckRes = new ArrayList();

        for (String beginCode : oldData.keySet()) {
            List oneCheckList = (List)oldData.get(beginCode);

            List<IInstructionParameter> instructionParameters = ((ICheckData)oneCheckList.get(0)).getData().getInstruction().getParams();
            for (IInstructionParameter param : instructionParameters) {
                if ((param.getStdunion() != null) && (param.getStdunion().equals("是"))) {
                    this.stdUnionFieldList.add(param.getEnglishName());
                }
            }
            if (oneCheckList.size() > 1) {
                ICheckData firstData = (ICheckData)oneCheckList.get(0);
                for (int i = 0; i < oneCheckList.size(); i++) {
                    ICheckData compareData = (ICheckData)oneCheckList.get(i);
                    boolean compare = isSameData(firstData, compareData, this.stdUnionFieldList);
                    if (compare) {
                        if (Long.valueOf(firstData.getRangeCheckedBeg()).longValue() > Long.valueOf(compareData.getRangeCheckedBeg()).longValue()) {
                            firstData.setRangeCheckedBeg(compareData.getRangeCheckedBeg());
                        }
                        if (Long.valueOf(firstData.getRangeCheckedEnd()).longValue() < Long.valueOf(compareData.getRangeCheckedEnd()).longValue())
                            firstData.setRangeCheckedEnd(compareData.getRangeCheckedEnd());
                    }
                    else {
                        icheckRes.add(compareData);
                    }
                }
                icheckRes.add(firstData);
            } else {
                icheckRes.addAll(oneCheckList);
            }
        }
        return icheckRes;
    }

    private boolean isSameData(ICheckData firstData, ICheckData compareData, List<String> stdUnionFieldList) {
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

    public static void main(String[] args) {
        String end = "15999";
        String begin = "14000";
        long result = Long.parseLong(end) - Long.parseLong(begin);
        Long value = Long.valueOf(Long.parseLong(end.substring(0, end.length() - String.valueOf(result).length())));
        System.out.println(value);
    }
}
