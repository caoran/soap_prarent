package com.boco.soap.cmnet.cocurrent.sync.workitem;

import com.boco.soap.cmnet.cocurrent.sync.workitem.impl.BarrierWorkItemExecutor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.impl.LatchWorkItemExecutor;

import java.util.concurrent.ExecutorService;

public class WorkItemExecutorFactory {
    public static final String COUNTDOWNLATCH = "CountDownLatch";
    public static final String CYCLICBARRIER = "CyclicBarrier";
    private static final WorkItemExecutorFactory instance = new WorkItemExecutorFactory();

    public static WorkItemExecutorFactory getInstance()
    {
        return instance;
    }

    public IWorkItemExecutor getWrokItemExecutor(String type, ExecutorService threadPool)
    {
        if ("CountDownLatch".equalsIgnoreCase(type))
            return new LatchWorkItemExecutor(threadPool);
        if ("CyclicBarrier".equalsIgnoreCase(type)) {
            return new BarrierWorkItemExecutor(threadPool);
        }
        return null;
    }
}
