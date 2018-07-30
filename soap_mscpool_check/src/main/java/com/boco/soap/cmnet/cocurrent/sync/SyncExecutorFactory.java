package com.boco.soap.cmnet.cocurrent.sync;

import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItemExecutor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.impl.BarrierWorkItemExecutor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.impl.LatchWorkItemExecutor;

public class SyncExecutorFactory {
    public static IWorkItemExecutor createBarrierWorkItemExecutor()
    {
        return new BarrierWorkItemExecutor(null);
    }

    public static IWorkItemExecutor createLatchWorkItemExecutor()
    {
        return new LatchWorkItemExecutor(null);
    }
}
