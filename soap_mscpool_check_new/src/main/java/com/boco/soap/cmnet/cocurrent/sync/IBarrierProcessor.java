package com.boco.soap.cmnet.cocurrent.sync;

import java.util.concurrent.CyclicBarrier;

public interface IBarrierProcessor extends IProcessor{
    void setBarrier(CyclicBarrier paramCyclicBarrier);
}
