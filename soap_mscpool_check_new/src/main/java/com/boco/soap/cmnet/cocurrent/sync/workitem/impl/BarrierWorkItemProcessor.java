package com.boco.soap.cmnet.cocurrent.sync.workitem.impl;

import com.boco.soap.cmnet.cocurrent.sync.IBarrierProcessor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.cocurrent.sync.workitem.AbstractWorkItemProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class BarrierWorkItemProcessor extends AbstractWorkItemProcessor implements IBarrierProcessor {
    private static final Logger logger = LoggerFactory.getLogger(BarrierWorkItemProcessor.class);
    private CyclicBarrier cyclicBarrier;

    public BarrierWorkItemProcessor(String processorName, IWorkItem workItem)
    {
        super(processorName, workItem);
    }

    public void setBarrier(CyclicBarrier cyclicBarrier)
    {
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run()
    {
        if (this.cyclicBarrier != null)
        {
            try
            {
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

                if (logger.isDebugEnabled()) {
                    logger.debug("执行器[{}]执行完毕，countDownLatch开始执行countDown()",
                            this.processorName);
                }

                try
                {
                    this.cyclicBarrier.await();
                }
                catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                catch (BrokenBarrierException e2) {
                    e2.printStackTrace();
                }
            }
            finally
            {
                if (logger.isDebugEnabled()) {
                    logger.debug("执行器[{}]执行完毕，countDownLatch开始执行countDown()",
                            this.processorName);
                }

                try
                {
                    this.cyclicBarrier.await();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
