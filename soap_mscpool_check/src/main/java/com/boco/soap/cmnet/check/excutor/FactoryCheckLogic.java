package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.excutor.impl.CheckDataAllResultImpl;
import com.boco.soap.cmnet.check.excutor.impl.CheckDataIncrmentResultImpl;
import com.boco.soap.cmnet.check.result.ICheckDataResult;

public class FactoryCheckLogic {
    private static final FactoryCheckLogic instance = new FactoryCheckLogic();

    public static FactoryCheckLogic getInstance()
    {
        return instance;
    }

    public ICheckDataResult getCheckLogicEngine(EnumFullPartCheck allOrPart) {
        if ((EnumFullPartCheck.PARTCHECK.equals(allOrPart)) || (EnumFullPartCheck.RANGECHECK_FULLCHECK.equals(allOrPart)) || (EnumFullPartCheck.RANGECHECK_PARTCHECK.equals(allOrPart)))
            return new CheckDataIncrmentResultImpl();
        if (EnumFullPartCheck.FULLCHECK.equals(allOrPart))
            return new CheckDataAllResultImpl();
        if (EnumFullPartCheck.PARAMECHECK.equals(allOrPart)) {
            //return new CheckDataParameResultImpl();
        }
        return null;
    }
}
