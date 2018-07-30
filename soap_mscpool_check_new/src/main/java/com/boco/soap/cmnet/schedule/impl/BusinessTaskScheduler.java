package com.boco.soap.cmnet.schedule.impl;

import com.boco.soap.cmnet.context.impl.BusinessMainTaskContext;
import com.boco.soap.cmnet.db.service.DBCheckResultService;
import com.boco.soap.cmnet.db.service.DBServiceManager;
import com.boco.soap.cmnet.schedule.AbstractTaskScheduler;
import com.boco.soap.cmnet.schedule.ITaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusinessTaskScheduler extends AbstractTaskScheduler
implements ITaskScheduler<BusinessMainTaskContext> {

    private static final Logger LOGGER= LoggerFactory.getLogger(BusinessTaskScheduler.class);

    @Override
    public void execute(BusinessMainTaskContext mainTaskContext)
    {
        executeTask(mainTaskContext);

        LOGGER.info("核查结束，修改工单orderId:{},状态:{}。。。。。。",mainTaskContext.getOrderId(),4);

        /*DBCheckResultService service = (DBCheckResultService) DBServiceManager.getInstance()
                .getDBService("dBCheckResultService");*/

        DBCheckResultService service=DBServiceManager.getInstance().getDBServiceCur();
        service.updateMainTaskForComplete(mainTaskContext.getOrderId());

        LOGGER.info("核查结束，修改工单orderId:{},状态:{}成功",mainTaskContext.getOrderId(),4);

    }


}
