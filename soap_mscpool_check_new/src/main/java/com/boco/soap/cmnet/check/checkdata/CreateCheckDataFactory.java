package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.impl.CreateOldCheckDataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCheckDataFactory {
    private static final Logger logger = LoggerFactory.getLogger(CreateCheckDataFactory.class);

    private static CreateCheckDataFactory instance = new CreateCheckDataFactory();

    public static CreateCheckDataFactory getInstance()
    {
        return instance;
    }

    public ICreateOldCheckData createCheckData(EnumFullPartCheck checktype) {
        if (logger.isDebugEnabled()) {
            logger.debug("核查源数据（标准数据）生成上下文工厂创建上下文实例：{}", checktype);
        }
        ICreateOldCheckData checkdata = null;
        if (EnumFullPartCheck.PARAMECHECK.equals(checktype)) {
            //checkdata = new CreateParameCheckDataImpl();
        }else {
            checkdata = new CreateOldCheckDataImpl();
        }

        return checkdata;
    }
}
