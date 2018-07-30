package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.impl.BusinessLoginCurNetDataImpl;
import com.boco.soap.cmnet.check.checkdata.impl.ParaCurDataGenerateImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCurCheckDataFactory {
    private static final Logger logger = LoggerFactory.getLogger(CreateCurCheckDataFactory.class);

    private static CreateCurCheckDataFactory instance = new CreateCurCheckDataFactory();

    public static CreateCurCheckDataFactory getInstance()
    {
        return instance;
    }

    public IBusinessLoginCurNetData createCheckData(EnumFullPartCheck checktype) {
        if (logger.isDebugEnabled()) {
            logger.debug("核查现网数据生成:{}", checktype);
        }
        IBusinessLoginCurNetData curcheckdata = null;
        if (checktype.equals(EnumFullPartCheck.PARAMECHECK)) {
            curcheckdata = new ParaCurDataGenerateImp();
        }else {
            curcheckdata = new BusinessLoginCurNetDataImpl();
        }

        return curcheckdata;
    }
}
