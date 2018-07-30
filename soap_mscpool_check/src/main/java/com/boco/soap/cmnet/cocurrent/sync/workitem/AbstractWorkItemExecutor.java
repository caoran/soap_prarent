package com.boco.soap.cmnet.cocurrent.sync.workitem;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

public abstract  class AbstractWorkItemExecutor implements IWorkItemExecutor {

    protected String name;
    protected ExecutorService threadPool;
    protected long executTime;

    public AbstractWorkItemExecutor(ExecutorService threadPool)
    {
        this.threadPool = threadPool;
    }

    public void addWorkItem(IWorkItem[] workItems)
    {
        for (IWorkItem workItem : workItems)
            addWorkItem(workItem);
    }

    public abstract void addWorkItem(IWorkItem paramIWorkItem);

    public void addWorkItem(Collection<IWorkItem> workItems)
    {
        for (IWorkItem workItem : workItems)
            addWorkItem(workItem);
    }

    public long getExecutTime()
    {
        return this.executTime;
    }

    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }
}
