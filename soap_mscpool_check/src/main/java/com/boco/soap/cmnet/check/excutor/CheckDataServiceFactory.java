package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.excutor.impl.CheckDataServiceResultImpl;
import com.boco.soap.cmnet.check.excutor.impl.ParameDataServiceResultImpl;
import com.boco.soap.cmnet.check.excutor.impl.RangeCheckDataServiceResultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckDataServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(CheckDataServiceFactory.class);

    private static final CheckDataServiceFactory instance = new CheckDataServiceFactory();

    public static CheckDataServiceFactory getInstance()
    {
        return instance;
    }

    public ICheckDataServiceResult createCheckDataService(EnumFullPartCheck checkType)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("当前核查类型为{}", checkType);
        }

        if (checkType.equals(EnumFullPartCheck.PARAMECHECK))
        {
            return new ParameDataServiceResultImpl();
        }
        return new CheckDataServiceResultImpl();
    }

    public IRangeCheckDataServiceResult createRangeCheckDataService(EnumFullPartCheck checkType)
    {
        return new RangeCheckDataServiceResultImpl();
    }
}
