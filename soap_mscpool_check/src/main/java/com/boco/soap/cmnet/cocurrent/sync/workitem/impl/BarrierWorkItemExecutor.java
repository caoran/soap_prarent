package com.boco.soap.cmnet.cocurrent.sync.workitem.impl;

import com.boco.soap.cmnet.cocurrent.sync.IBarrierProcessor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.cocurrent.sync.ProcessorFactory;
import com.boco.soap.cmnet.cocurrent.sync.workitem.AbstractWorkItemExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

public class BarrierWorkItemExecutor extends AbstractWorkItemExecutor {
    private Logger logger = LoggerFactory.getLogger(BarrierWorkItemExecutor.class);

    private List<IBarrierProcessor> processors = new ArrayList();
    private Runnable runnable;
    private CyclicBarrier barrier;

    public BarrierWorkItemExecutor(ExecutorService threadPool)
    {
        super(threadPool);
    }

    public void execute()
    {
        this.barrier = new CyclicBarrier(this.processors.size(), this.runnable);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("线程组[{}]运行开始,线程数为[{}]", this.name, Integer.valueOf(this.processors.size()));
        }
        System.out.println("线程组[" + this.name + "]运行开始,线程数为[" + this.processors.size() + "]");
        long start = System.currentTimeMillis();

        if (this.threadPool != null)
        {
            threadPoolExecut();
        }
        else {
            newThreadExecut();
        }

        long end = System.currentTimeMillis();
        this.executTime = (end - start);

        if (this.logger.isDebugEnabled())
            this.logger.debug("线程组[{}]运行结束，用时：", this.name, Long.valueOf(this.executTime));
    }

    private void newThreadExecut()
    {
        for (IBarrierProcessor processor : this.processors) {
            processor.setBarrier(this.barrier);
            Thread thread = new Thread(processor);
            thread.start();
        }
    }

    private void threadPoolExecut()
    {
        int i = 0;
        for (IBarrierProcessor processor : this.processors) {
            System.out.println(i++);
            processor.setBarrier(this.barrier);
            this.threadPool.execute(processor);
        }
    }

    public void addWorkItem(IWorkItem workItem)
    {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("向线程组[{}] 添加任务[{}]。", this.name, workItem
                    .getWorkItemName());
        }

        IBarrierProcessor processor =
                ProcessorFactory.getInstance()
                        .getBarrierProcessor(workItem, this.name + ":" + workItem.getWorkItemName());
        this.processors.add(processor);
    }
}
