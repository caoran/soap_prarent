package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.excutor.ICheckDataAllMain;
import com.boco.soap.cmnet.check.result.ICheckResultMainTaskEO;
import com.boco.soap.cmnet.check.result.ICheckResultSubTaskEO;
import com.boco.soap.cmnet.check.result.impl.CheckResultMainTaskEOImpl;
import com.boco.soap.cmnet.check.result.impl.CheckResultSubTaskEOImpl;
import com.boco.soap.cmnet.context.IMainTaskContext;
import com.boco.soap.cmnet.context.ISubTaskContext;
import com.boco.soap.cmnet.context.impl.BusinessMainTaskContext;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.mongo.bean.OrderMongo;
import com.boco.soap.cmnet.mongo.dao.IOrderMongoDao;
import com.boco.soap.cmnet.schedule.ExecutorServiceFactory;
import com.boco.soap.cmnet.schedule.ITaskScheduler;
import com.boco.soap.cmnet.schedule.TaskSchedulerFactory;
import com.boco.soap.cmnet.util.CommonUtil;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
@Component
public class CheckDataAllMainImpl implements ICheckDataAllMain {
    @Autowired
    //private IOrderMongoDao orderMongoDao;
    private static final Logger logger = LoggerFactory.getLogger(CheckDataAllMainImpl.class);
    private ExecutorServiceFactory factory = (ExecutorServiceFactory) SpringContextHolder.getBean("ExecutorServiceFactoryInstance");

    public  CheckDataAllMainImpl(){

    }

    @Override
    public String soapCheckDataInvokeResult(String paramString) throws Exception {
        return "";
    }

    @Override
    public  String soapCheckDataInvokeResultByOrderId(String orderId) throws Exception {
        String subTaskId = "";
        logger.info("开始核查多线程，orderId：{}",orderId);
        OrderMongo orderMongo=orderMongoDao.findById(orderId);
        Map<String,Ne> neMap=orderMongo.getNeList();
        ICheckResultMainTaskEO checkResultMainEo = new CheckResultMainTaskEOImpl(new ArrayList<>(neMap.values()), new ArrayList<>(orderMongo.getStdTable().keySet()),orderId);
        checkResultMainEo=insertMainTask(checkResultMainEo);
        Map<String,ISubTaskContext> subTaskContextMap=initSubContextMap(neMap,checkResultMainEo.getTaskId());
        final IMainTaskContext mainTask = new BusinessMainTaskContext(new ArrayList<>(neMap.values()),subTaskContextMap);
        Runnable task = new Runnable()
        {
            public void run() {
                CheckDataAllMainImpl.logger.info("开始执行多线程启动工单任务！");
                ITaskScheduler taskScheduler = TaskSchedulerFactory.getFactory().getTaskScheduler();
                taskScheduler.execute(mainTask);
            }
        };
        ExecutorService threadPool = this.factory.getThreadPool();
        threadPool.submit(task);
        logger.info("核查消息已正常启动！");
        return "";
    }

    private ICheckResultMainTaskEO insertMainTask(ICheckResultMainTaskEO checkResultMainEo) {
        checkResultMainEo.setTaskId(CommonUtil.nextStrId());
        logger.info("save checkResultMainEo=====");
        return checkResultMainEo;
    }

    private Map<String,ISubTaskContext> initSubContextMap(Map<String,Ne> nelist,String taskid) {
        Map subTaskMap = new HashMap();
        for (Map.Entry<String,Ne> entry:nelist.entrySet()){
            ICheckResultSubTaskEO checkSubTask = new CheckResultSubTaskEOImpl(taskid, entry.getValue().getName(), EnumFullPartCheck.FULLCHECK, 0, 0);
            //this.service.insertSubTask(checkSubTask);
            checkSubTask.setSubTaskId(CommonUtil.nextStrId());
            ISubTaskContext subTaskContexts = new BusinessSubTaskContext(taskid);
            subTaskContexts.setNe(entry.getValue());
            subTaskContexts.setStartDate(new Date());
            subTaskContexts.setSubTaskId(checkSubTask.getSubTaskId());


            subTaskMap.put(subTaskContexts.getSubTaskId(),subTaskContexts);
        }
        return subTaskMap;
    }
}
