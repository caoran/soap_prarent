package com.boco.soap.cmnet.cocurrent.sync;

import java.util.concurrent.CountDownLatch;

public abstract interface ILatchProcessor extends IProcessor
{
    public abstract void setCountDownLatch(CountDownLatch paramCountDownLatch);
}
