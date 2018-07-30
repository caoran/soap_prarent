package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.excutor.impl.CheckEngineImpl;
import com.boco.soap.cmnet.check.excutor.impl.CheckParameEngineImpl;

public class CheckEngineFactory {
    private static CheckEngineFactory factory = new CheckEngineFactory();

    public static CheckEngineFactory getFactory()
    {
        return factory;
    }

    public ICheckEngine getCheckEngine(EnumFullPartCheck checktype, IBusinessInstruction instruction) {
        if (checktype.equals(EnumFullPartCheck.PARAMECHECK))
        {
            return new CheckParameEngineImpl(instruction);
        }
        return new CheckEngineImpl(instruction);
    }

    public ICheckEngine getCheckEngine(IBusinessInstruction instruction)
    {
        return new CheckEngineImpl(instruction);
    }
}
