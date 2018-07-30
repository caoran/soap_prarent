package com.boco.soap.cmnet.schedule;

import java.util.concurrent.ExecutorService;

public interface ExecutorServiceFactory {
    ExecutorService getThreadPool();
}
