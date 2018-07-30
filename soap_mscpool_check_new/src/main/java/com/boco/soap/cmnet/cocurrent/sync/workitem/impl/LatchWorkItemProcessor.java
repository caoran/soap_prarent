package com.boco.soap.cmnet.cocurrent.sync.workitem.impl;

import com.boco.soap.cmnet.cocurrent.sync.ILatchProcessor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.cocurrent.sync.workitem.AbstractWorkItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class LatchWorkItemProcessor  extends AbstractWorkItemProcessor
        implements ILatchProcessor {
    private static final Logger logger = LoggerFactory.getLogger(LatchWorkItemProcessor.class);
    private CountDownLatch downLatch;

    public LatchWorkItemProcessor(String name, IWorkItem workItem)
    {
        super(name, workItem);
    }

    @Override
    public void setCountDownLatch(CountDownLatch downLatch)
    {
        this.downLatch = downLatch;
    }

    @Override
    public void run()
    {
        if (this.downLatch == null)
        {
            throw new RuntimeException("countDownLatch is null!");
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("执行器[{}]开始执行。", this.processorName);
            }
            this.workItem.run();
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("执行器" + this.processorName + "执行异常", e);
            }

            if (this.processorExceptionHandler != null) {
                this.processorExceptionHandler.callBack(this, e);
            }
        }
        finally
        {
            if (logger.isDebugEnabled()) {
                logger.debug("执行器[{}]执行完毕，countDownLatch开始执行countDown()", this.processorName);
            }
            this.downLatch.countDown();
        }
    }
}
