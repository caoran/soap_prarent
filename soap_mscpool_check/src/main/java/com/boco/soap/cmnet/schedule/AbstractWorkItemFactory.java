package com.boco.soap.cmnet.schedule;

import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.context.ISubTaskContext;

public interface AbstractWorkItemFactory {
    IWorkItem getWorkItem(ISubTaskContext paramISubTaskContext);
}
