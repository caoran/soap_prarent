package com.boco.soap.cmnet.cocurrent.sync.workitem;

import com.boco.soap.cmnet.context.ICheckContext;

public interface AbstractCheckWorkItemFactory {
    IWorkItem getWorkItem(ICheckContext paramICheckContext);
}
