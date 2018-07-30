package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.ICheckDataMainResult;
import com.boco.soap.cmnet.cocurrent.sync.workitem.AbstractCheckWorkItemFactory;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItemExecutor;
import com.boco.soap.cmnet.cocurrent.sync.workitem.WorkItemExecutorFactory;
import com.boco.soap.cmnet.cocurrent.sync.workitem.impl.BusinessCheckWorkItemFactory;
import com.boco.soap.cmnet.common.util.FormatMap;
import com.boco.soap.cmnet.context.ICheckContext;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.db.service.DBDtInfoService;
import com.boco.soap.cmnet.db.service.DBServiceManager;
import com.boco.soap.cmnet.schedule.ExecutorServiceFactory;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

public class CheckDataResultImpl  implements ICheckDataMainResult
{
    private ExecutorServiceFactory check_factory;
    private AbstractCheckWorkItemFactory itemFactory;
    private final Logger logger = LoggerFactory.getLogger(CheckDataResultImpl.class);

    public CheckDataResultImpl()
    {
        this.check_factory = ((ExecutorServiceFactory)SpringContextHolder.getBean("executorServiceFactoryInstance"));

        this.itemFactory = new BusinessCheckWorkItemFactory();
    }

    public BusinessSubTaskContext getCheckDataResult(Map<String, List<ICheckData>> stdCheckDatas, Map<String, List<ICheckData>> curCheckDatas, BusinessSubTaskContext subTaskContext, Object extObj)
    {
        this.logger.info("dictID+现网表准备线程准备开始时间-->>>>" + new Date());
        Map<String,ICheckContext> subTaskMap = new HashMap();

        ExecutorService threadPool = this.check_factory.getThreadPool();

        IWorkItemExecutor executor = WorkItemExecutorFactory.getInstance().getWrokItemExecutor("CountDownLatch", threadPool);

        for (String key : stdCheckDatas.keySet()) {
            int count=stdCheckDatas.get(key).size();
            int pages = count%2000== 0?(count/2000):(count/2000 + 1);
            for(int i = 0; i<pages; i++){
                int start = i*2000;
                int end = (i+1)*2000 + 1;
                if(end >= count) end = count;
                ICheckContext checksubtaskcontext = new BusinessCheckContext();
                checksubtaskcontext.setTotalCount(count);
                checksubtaskcontext.setCurCheckDataMap(curCheckDatas);
                checksubtaskcontext.setStdCheckData((List)stdCheckDatas.get(key).subList(start,end));
                checksubtaskcontext.setTaskId(subTaskContext.getTaskId());
                checksubtaskcontext.setSubTaskId(subTaskContext.getSubTaskId());
                checksubtaskcontext.setNeList(new ArrayList<>(subTaskContext.getOrderMongo().getNeList().values()));
                checksubtaskcontext.setDictIdQuery(key);
                //checksubaskcontext.setDbFile(subTaskContext.getDbFile());
                //checksubtaskcontext.setCheckLogicMap(subTaskContext.getCheckLogicMap());
                checksubtaskcontext.setOrderMongo(subTaskContext.getOrderMongo());
                checksubtaskcontext.setCheckType(subTaskContext.getCheckType());
                checksubtaskcontext.setIsCombine(subTaskContext.getIsCombine());
                checksubtaskcontext.setBusiDict(subTaskContext.getBusiDict());
                subTaskMap.put(key, checksubtaskcontext);

                IWorkItem workItem = this.itemFactory.getWorkItem(checksubtaskcontext);

                executor.addWorkItem(workItem);
            }
        }

        executor.execute();
        subTaskContext.setSubTaskMap(subTaskMap);
        return subTaskContext;
    }
    public ExecutorServiceFactory getCheck_factory() {
        return this.check_factory;
    }

    public void setCheck_factory(ExecutorServiceFactory checkFactory) {
        this.check_factory = checkFactory;
    }

    private Map<String, Object> getPRIORITY(DBDtInfoService service, String key, String dbFile)
    {
        Map map = new HashMap();

        if (key == null)
        {
            return initPRIORITY();
        }

        List list = service.getInstructionDictInfo(key.split("-")[0], dbFile);

        if ((list != null) && (list.size() > 0) && (list.get(0) != null))
        {
            String PRIORITY = ((Map)list.get(0)).get("PRIORITY") != null ? ((Map)list.get(0)).get("PRIORITY").toString() : null;
            String ISTEMPLATE = ((Map)list.get(0)).get("ISTEMPLATE") != null ? ((Map)list.get(0)).get("ISTEMPLATE").toString() : null;
            if ((PRIORITY != null) && (ISTEMPLATE != null))
            {
                map = FormatMap.FormatMap((Map)list.get(0));
            }
            else
            {
                map = initPRIORITY();
            }

        }
        else
        {
            map = initPRIORITY();
        }

        return map;
    }

    private Map<String, Object> initPRIORITY() {
        Map map = new HashMap();
        map.put("PRIORITY", "0");
        map.put("ISTEMPLATE", "0");
        return map;
    }
}
