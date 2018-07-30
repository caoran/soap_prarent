package com.boco.soap.cmnet.check.excutor;

import ch.qos.logback.classic.Logger;
import com.boco.soap.cmnet.check.excutor.impl.CheckDataAllMainImpl;
import com.boco.soap.cmnet.schedule.TaskSchedulerFactory;
import org.slf4j.LoggerFactory;

public class CheckDataMainFactory {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TaskSchedulerFactory.class);

    private static CheckDataMainFactory instance = new CheckDataMainFactory();

    public static CheckDataMainFactory getFactory()
    {
        return instance;
    }

    public ICheckDataAllMain getCheckDataMain() {
        return new CheckDataAllMainImpl();
    }
}
