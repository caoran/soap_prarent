package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckQuertPairType;
import com.boco.soap.cmnet.check.excutor.ICheckCommonComponent;
import com.boco.soap.cmnet.check.excutor.ICheckEngine;
import com.boco.soap.cmnet.check.excutor.ICheckTypeExecutor;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckTypeExecessExecutorImpl implements ICheckTypeExecutor {
    @Override
    public List<IDataCheckReturn> checkLogic(List<ICheckData> stdCheckDatas, List<ICheckData> curCheckDatas, ICheckEngine checkEngine) {
        ICheckCommonComponent checkComponent = new CheckCommonComponentImpl();
        List checkDataGroupExtList = new ArrayList();

        for (ICheckData curCheckData : curCheckDatas)
            if (!curCheckData.isCurPairChecked())
            {
                IDataCheckReturn dataCheckReturn = checkComponent
                        .setCheckDataResult(null, curCheckData, EnumCheckDataReturnType.EXECESS, EnumCheckQuertPairType.NOTPAIR);

                checkDataGroupExtList.add(dataCheckReturn);
            }
        return checkDataGroupExtList;
    }

    private Map<String, ICheckData> clearCurExeCheckData(List<ICheckData> stdCheckDatas)
    {
        Map stdCheckDataMap = new HashMap();
        for (ICheckData stdCheckData : stdCheckDatas) {
            if ((stdCheckData != null) &&
                    (!stdCheckDataMap.containsKey(stdCheckData.getData().getKey()))) {
                stdCheckDataMap.put(stdCheckData.getData().getKey(), stdCheckData);
            }
        }

        return stdCheckDataMap;
    }
}
