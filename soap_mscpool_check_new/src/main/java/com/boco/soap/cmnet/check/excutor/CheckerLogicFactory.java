package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.beans.enums.EnumCheckType;
import com.boco.soap.cmnet.check.excutor.impl.CheckTypeAbbrExecutorImpl;
import com.boco.soap.cmnet.check.excutor.impl.CheckTypeEqualExecutorImpl;
import com.boco.soap.cmnet.check.excutor.impl.CheckTypeExecessExecutorImpl;
import com.boco.soap.cmnet.check.excutor.impl.CheckTypeExtExecutorImpl;

public class CheckerLogicFactory {
    private static final CheckerLogicFactory instance = new CheckerLogicFactory();

    public static CheckerLogicFactory getInstance()
    {
        return instance;
    }

    public ICheckTypeExecutor getCheckObjEngine(EnumCheckType checkType) {
        if (checkType.equals(EnumCheckType.EQUCHECK))
        {
            return new CheckTypeEqualExecutorImpl();
        }
        if (checkType.equals(EnumCheckType.ABBRCHECK))
        {
            return new CheckTypeAbbrExecutorImpl();
        }
        if (checkType.equals(EnumCheckType.EXTCHECK))
        {
            return new CheckTypeExtExecutorImpl();
        }
        if (checkType.equals(EnumCheckType.CHECKEXECESS))
        {
            return new CheckTypeExecessExecutorImpl();
        }
        /*if (checkType.equals(EnumCheckType.RANGECHECK))
        {
            return new RangeCheckTypeEqualExecutorImpl();
        }
        if (checkType.equals(EnumCheckType.PARAMEEXTCHECK))
        {
            return new ParameTypeExtExecutorImpl();
        }
        if (checkType.equals(EnumCheckType.PARAMEEQUALCHECK)) {
            return new ParameTypeEqualExecutorImpl();
        }*/

        return null;
    }
}
