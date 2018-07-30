package com.boco.soap.cmnet.cocurrent.sync;

import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.cocurrent.sync.workitem.impl.BarrierWorkItemProcessor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.impl.LatchWorkItemProcessor;

public class ProcessorFactory {
    private static final ProcessorFactory instance = new ProcessorFactory();

    public static ProcessorFactory getInstance()
    {
        return instance;
    }

    public ILatchProcessor getLatchProcessor(IWorkItem workItem, String name)
    {
        return new LatchWorkItemProcessor(name, workItem);
    }

    public IBarrierProcessor getBarrierProcessor(IWorkItem workItem, String name)
    {
        return new BarrierWorkItemProcessor(name, workItem);
    }
}
