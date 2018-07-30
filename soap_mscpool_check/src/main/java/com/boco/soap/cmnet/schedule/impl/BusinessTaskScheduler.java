package com.boco.soap.cmnet.schedule.impl;

import com.boco.soap.cmnet.context.impl.BusinessMainTaskContext;
import com.boco.soap.cmnet.schedule.AbstractTaskScheduler;
import com.boco.soap.cmnet.schedule.ITaskScheduler;

public class BusinessTaskScheduler extends AbstractTaskScheduler
implements ITaskScheduler<BusinessMainTaskContext> {
    @Override
    public void execute(BusinessMainTaskContext mainTaskContext)
    {
        executeTask(mainTaskContext);

        /*DBCheckResultService service = (DBCheckResultService)DBServiceManager.getInstance()
                .getDBService("db_service_check_result");

        service.updateMainTaskForComplete(mainTaskContext.getTaskId());*/
    }


}
