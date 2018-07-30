package com.boco.soap.cmnet.schedule;

import com.boco.soap.cmnet.schedule.impl.BusinessTaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskSchedulerFactory {
    private static final Logger logger = LoggerFactory.getLogger(TaskSchedulerFactory.class);

    private static TaskSchedulerFactory instance = new TaskSchedulerFactory();

    public static TaskSchedulerFactory getFactory() {
        return instance;
    }

    public ITaskScheduler getTaskScheduler() {
        return new BusinessTaskScheduler();
    }
}