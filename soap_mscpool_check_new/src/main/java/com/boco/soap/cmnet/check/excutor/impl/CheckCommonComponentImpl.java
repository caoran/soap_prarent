package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckQuertPairType;
import com.boco.soap.cmnet.check.excutor.ICheckCommonComponent;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.check.result.impl.DataCheckReturnImpl;

public class CheckCommonComponentImpl implements ICheckCommonComponent
{
    public IDataCheckReturn checkPairEqualCheckData(ICheckData stdCheckData, ICheckData curCheckData, EnumCheckQuertPairType pairType, boolean checkResultFlag)
    {
        IDataCheckReturn dataCheckReturn = getcheckdatareturn(pairType);
        if (checkResultFlag)
            dataCheckReturn.setDataType(EnumCheckDataReturnType.CORRECT);
        else {
            dataCheckReturn.setDataType(EnumCheckDataReturnType.WRONG);
        }
        curCheckData.setCurPairChecked(true);
        stdCheckData.setStdChecked(true);
        dataCheckReturn.setStdCheckData(stdCheckData);
        dataCheckReturn.setCurCheckData(curCheckData);
        dataCheckReturn.setPairDataType(pairType);
        return dataCheckReturn;
    }

    private IDataCheckReturn getcheckdatareturn(EnumCheckQuertPairType pairType)
    {
        IDataCheckReturn dataCheckReturn = null;
        if (pairType.equals(EnumCheckQuertPairType.EXTPARAME))
        {
            //dataCheckReturn = new ParameCheckReturnImpl();
        }
        else {
            dataCheckReturn = new DataCheckReturnImpl();
        }

        return dataCheckReturn;
    }

    public IDataCheckReturn setCheckDataResult(ICheckData stdCheckData, ICheckData curCheckData, EnumCheckDataReturnType checkType, EnumCheckQuertPairType pairType)
    {
        IDataCheckReturn dataCheckReturn = new DataCheckReturnImpl();
        dataCheckReturn.setDataType(checkType);
        dataCheckReturn.setStdCheckData(stdCheckData);
        dataCheckReturn.setCurCheckData(curCheckData);
        dataCheckReturn.setPairDataType(pairType);
        return dataCheckReturn;
    }
}
