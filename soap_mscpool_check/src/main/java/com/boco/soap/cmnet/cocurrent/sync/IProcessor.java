package com.boco.soap.cmnet.cocurrent.sync;

public interface IProcessor extends Runnable{

    String getName();

    void setProcessorExceptionHandler(IProcessorExceptionHandler paramIProcessorExceptionHandler);
}
