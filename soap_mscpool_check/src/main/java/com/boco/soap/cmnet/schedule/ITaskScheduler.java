package com.boco.soap.cmnet.schedule;

import com.boco.soap.cmnet.context.IMainTaskContext;

public interface ITaskScheduler<T extends IMainTaskContext> {
    void execute(T paramT);
}
