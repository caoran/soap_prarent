package com.boco.soap.cmnet.check.checkdata;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.boco.soap.cmnet.beans.entity.Ne;

public class CreateSourceDatasContextFactory {

    private static final Log log = LogFactory.getLog(CreateSourceDatasContextFactory.class);

    private static final CreateSourceDatasContextFactory factroy = new CreateSourceDatasContextFactory();

    public static CreateSourceDatasContextFactory getInstance()
    {
        return factroy;
    }

    public ICreateSourceDatasConfig createConfig(IBusinessInstruction instruction, Ne ne, String url)
    {
        if (log.isDebugEnabled()) {
            log.debug("核查源数据（标准数据）生成上下文工厂创建上下文实例： 指令为[" + instruction.getId() + "]  网元为 [ " + ne.getName() + "]");
        }
        return new CreateSourceDatasConfigImpl(instruction, ne, url);
    }
}
