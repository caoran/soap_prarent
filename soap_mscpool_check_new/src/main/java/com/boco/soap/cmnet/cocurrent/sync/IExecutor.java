package com.boco.soap.cmnet.cocurrent.sync;

import java.util.concurrent.ExecutorService;

public interface IExecutor {

     void execute();

     void setThreadPool(ExecutorService paramExecutorService);

    long getExecutTime();
}
