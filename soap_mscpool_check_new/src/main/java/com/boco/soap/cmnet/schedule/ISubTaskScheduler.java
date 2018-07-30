package com.boco.soap.cmnet.schedule;

import com.boco.soap.cmnet.context.ISubTaskContext;

public interface ISubTaskScheduler<T extends ISubTaskContext> {
    public abstract void execute(T paramT);
}
