package com.boco.soap.cmnet.cocurrent.sync.workitem.impl;

import com.boco.soap.cmnet.cocurrent.sync.workitem.AbstractCheckWorkItemFactory;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.context.ICheckContext;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

public class BusinessCheckWorkItemFactory implements AbstractCheckWorkItemFactory {

    @Override
    public IWorkItem getWorkItem(ICheckContext checksubTaskContext) {
        return new BusinessCheckcheduler((BusinessCheckContext)checksubTaskContext);
    }
}
