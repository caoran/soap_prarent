package com.boco.soap.cmnet.cocurrent.sync.workitem;

import com.boco.soap.cmnet.cocurrent.sync.IProcessorExceptionHandler;

public class AbstractWorkItemProcessor {
    protected String processorName;
    protected IWorkItem workItem;
    protected IProcessorExceptionHandler processorExceptionHandler;

    public AbstractWorkItemProcessor(String processorName, IWorkItem workItem)
    {
        this.processorName = processorName;
        this.workItem = workItem;
    }

    public void setProcessorExceptionHandler(IProcessorExceptionHandler processorExceptionHandler)
    {
        this.processorExceptionHandler = processorExceptionHandler;
    }

    public String getName()
    {
        return this.processorName;
    }
}
