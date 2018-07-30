package com.boco.soap.cmnet.schedule.impl;

import com.boco.soap.cmnet.schedule.ExecutorServiceFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Component
public class ExecutorServiceFactoryInstance implements ExecutorServiceFactory {
    private int corePoolSize = 10;

    private int maximumPoolSize = 20;

    private Long keepAliveTime = Long.valueOf(5000L);  //非核心线程的闲置超时时间，超过这个时间就会被回收
    private static  ExecutorService executorService;

    @Override
    public ExecutorService getThreadPool()
    {
        if (this.executorService == null) {
            this.executorService = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize, this.keepAliveTime
                    .longValue(), TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }

        return this.executorService;
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return this.keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

}
