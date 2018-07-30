package com.boco.soap.cmnet.cocurrent.sync.workitem.impl;

import com.boco.soap.cmnet.cocurrent.sync.ILatchProcessor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItemExecutor;
import com.boco.soap.cmnet.cocurrent.sync.ProcessorFactory;
import com.boco.soap.cmnet.cocurrent.sync.workitem.AbstractWorkItemExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class LatchWorkItemExecutor extends AbstractWorkItemExecutor implements IWorkItemExecutor {
    private Logger logger = LoggerFactory.getLogger(LatchWorkItemExecutor.class);

    private CountDownLatch downLatch = null;

    private List<ILatchProcessor> processors = new ArrayList();

    public LatchWorkItemExecutor(ExecutorService threadPool)
    {
        super(threadPool);
    }

    public void execute()
    {
        this.downLatch = new CountDownLatch(this.processors.size());

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("线程组[{}]运行开始,线程数为[{}]", this.name, Integer.valueOf(this.processors.size()));
        }

        long start = System.currentTimeMillis();
        if (this.threadPool != null)
        {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("线程池不为空，在线程池中执行任务。");
            }

            threadPoolExecut();
        }
        else {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("线程池为空，创建线程进行执行。");
            }
            newThreadExecut();
        }
        try
        {
            this.downLatch.await();
        }
        catch (InterruptedException e) {
            if (this.logger.isErrorEnabled()) {
                this.logger.error("缓程组[" + this.name + "]运行出现问题：" + e.getMessage(), e);
            }

        }

        long end = System.currentTimeMillis();
        this.executTime = (end - start);

        if (this.logger.isDebugEnabled())
            this.logger.debug("线程组[{}]运行结束，用时：", this.name, Long.valueOf(this.executTime));
    }

    private void newThreadExecut()
    {
        for (ILatchProcessor processor : this.processors) {
            processor.setCountDownLatch(this.downLatch);
            Thread thread = new Thread(processor);
            thread.start();
        }
    }

    private void threadPoolExecut()
    {
        for (ILatchProcessor processor : this.processors) {
            processor.setCountDownLatch(this.downLatch);
            this.threadPool.submit(processor);
        }
    }

    public void addWorkItem(IWorkItem workItem)
    {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("向线程组[{}] 添加任务[{}]。", this.name, workItem
                    .getWorkItemName());
        }
        ILatchProcessor processor =
                ProcessorFactory.getInstance()
                        .getLatchProcessor(workItem, this.name + ":" + workItem.getWorkItemName());
        this.processors.add(processor);
    }
}
