package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.enums.EnumCombineType;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.checkdata.IMergeCheckData;
import com.boco.soap.cmnet.check.checkdata.IUnion;
import com.boco.soap.cmnet.check.checkdata.SourceDataUnionFactory;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeCheckDataImpl
        implements IMergeCheckData
{
    public Map<String, List<ICheckData>> mergeCheckData(Map<String, List<ICheckData>> checkDataMap, int isCombine, EnumFullPartCheck checkType, Map<String, List<ICheckData>> curCheckDataMap)
    {
        return groupDatas(checkDataMap, isCombine, checkType, curCheckDataMap);
    }

    private Map<String, List<ICheckData>> groupDatas(Map<String, List<ICheckData>> checkData, int isCombine, EnumFullPartCheck checkType, Map<String, List<ICheckData>> curCheckDataMap)
    {
        Map groupCheckData = new HashMap();

        for (String key : checkData.keySet()) {
            List sourceData = (List)checkData.get(key);
            if ((sourceData != null) && (sourceData.size() > 0)) {
                IData data = ((ICheckData)sourceData.get(0)).getData();
                String code = data.getCode();
                IBusinessInstruction instruction = data.getInstruction();
                boolean isFuzzy = instruction.isFuzzyCheck();
                boolean isMerge = instruction.isMerge();
                List curCheckDataList = null;
                if (isMerge) {
                    curCheckDataList = (List)curCheckDataMap.get(key);
                }

                if ((checkType == EnumFullPartCheck.RANGECHECK_FULLCHECK) || (checkType == EnumFullPartCheck.RANGECHECK_PARTCHECK)) {
                    groupCheckData.put(key, groupData(sourceData, checkType, instruction.getCombineType(), curCheckDataList));
                }
                else if ((code != null) && (!code.equals("")) && (isCombine == 1))
                    groupCheckData.put(key, groupData(sourceData, checkType, instruction.getCombineType(), curCheckDataList));
                else {
                    groupCheckData.put(key, sourceData);
                }
            }
        }

        return groupCheckData;
    }

    private List<ICheckData> groupData(List<ICheckData> sourceData, EnumFullPartCheck checkType, EnumCombineType combineType, List<ICheckData> curCheckDatas)
    {
        IUnion unionImpl = SourceDataUnionFactory.getInstance().createCheckData(checkType, combineType, curCheckDatas);
        List targetData = unionImpl.unionData(sourceData);
        return targetData;
    }
}
