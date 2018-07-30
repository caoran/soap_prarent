package com.boco.soap.cmnet.cocurrent.sync.workitem;

import com.boco.soap.cmnet.cocurrent.sync.IExecutor;

import java.util.Collection;

public interface IWorkItemExecutor extends IExecutor {
     void addWorkItem(IWorkItem[] paramArrayOfIWorkItem);

     void addWorkItem(IWorkItem paramIWorkItem);

     void addWorkItem(Collection<IWorkItem> paramCollection);
}
