package com.boco.soap.cmnet.schedule;

import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItemExecutor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.WorkItemExecutorFactory;
import com.boco.soap.cmnet.context.IMainTaskContext;
import com.boco.soap.cmnet.context.ISubTaskContext;
import com.boco.soap.cmnet.schedule.impl.BusinessWorkItemFactory;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class AbstractTaskScheduler {
    private static final Logger logger = LoggerFactory.getLogger(AbstractTaskScheduler.class);

    private ExecutorServiceFactory factory = (ExecutorServiceFactory) SpringContextHolder.getBean("executorServiceFactoryInstance");

    private AbstractWorkItemFactory itemFactory = new BusinessWorkItemFactory();

    public void executeTask(IMainTaskContext mainTaskContext)
    {
        Map subTaskContexts = mainTaskContext.getSubTaskContext();

        ExecutorService threadPool = this.factory.getThreadPool();

        IWorkItemExecutor executor = WorkItemExecutorFactory.getInstance().getWrokItemExecutor("CountDownLatch", threadPool);

        Iterator iterator = subTaskContexts.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry)iterator.next();
            ISubTaskContext subTaskContext = (ISubTaskContext)entry.getValue();
            IWorkItem workItem = this.itemFactory.getWorkItem(subTaskContext);
            executor.addWorkItem(workItem);
        }
        executor.execute();

    }

    public void setFactory(ExecutorServiceFactory factory)
    {
        this.factory = factory;
    }

    public void setItemFactory(AbstractWorkItemFactory itemFactory)
    {
        this.itemFactory = itemFactory;
    }
}
