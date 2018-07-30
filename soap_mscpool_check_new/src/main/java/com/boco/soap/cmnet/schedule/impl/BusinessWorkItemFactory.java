package com.boco.soap.cmnet.schedule.impl;

import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.context.ISubTaskContext;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.schedule.AbstractWorkItemFactory;

public class BusinessWorkItemFactory implements AbstractWorkItemFactory {
    public IWorkItem getWorkItem(ISubTaskContext subTaskContext)
    {
        return new BusinessSubTaskScheduler((BusinessSubTaskContext)subTaskContext);
    }
}
