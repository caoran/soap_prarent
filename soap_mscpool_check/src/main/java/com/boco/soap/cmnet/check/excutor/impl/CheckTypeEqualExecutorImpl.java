package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckQuertPairType;
import com.boco.soap.cmnet.check.excutor.ICheckEngine;
import com.boco.soap.cmnet.check.excutor.ICheckTypeExecutor;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.check.result.impl.DataCheckReturnImpl;

import java.util.ArrayList;
import java.util.List;

public class CheckTypeEqualExecutorImpl implements ICheckTypeExecutor {

    @Override
    public List<IDataCheckReturn> checkLogic(List<ICheckData> stdCheckDatas, List<ICheckData> curCheckDatas, ICheckEngine checkEngine) {
             ICheckData stdCheckData = (ICheckData)stdCheckDatas.get(0);
/* 31 */     ICheckData curCheckData = (ICheckData)curCheckDatas.get(0);
/* 32 */     boolean pairFlag = checkEngine.check(stdCheckData, curCheckData);
/*    */
/* 34 */     List checkDataGroupAbbrList = new ArrayList();
/*    */
/* 36 */     IDataCheckReturn dataCheckReturn = new DataCheckReturnImpl();
/* 37 */     if (pairFlag)
/* 38 */       dataCheckReturn.setDataType(EnumCheckDataReturnType.CORRECT);
/*    */     else {
/* 40 */       dataCheckReturn.setDataType(EnumCheckDataReturnType.WRONG);
/*    */     }
/*    */
/* 43 */     curCheckData.setCurPairChecked(true);
/* 44 */     stdCheckData.setStdChecked(true);
/* 45 */     dataCheckReturn.setStdCheckData(stdCheckData);
/* 46 */     dataCheckReturn.setCurCheckData(curCheckData);
/* 47 */     dataCheckReturn.setPairDataType(EnumCheckQuertPairType.EQUPAIR);
/* 48 */     checkDataGroupAbbrList.add(dataCheckReturn);
/*    */
/* 50 */     return checkDataGroupAbbrList;
    }
}
