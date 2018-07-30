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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class CheckDataResultImpl  implements ICheckDataMainResult
{
    private ExecutorServiceFactory check_factory;
    private AbstractCheckWorkItemFactory itemFactory;
    private final Logger logger = LoggerFactory.getLogger(CheckDataResultImpl.class);

    public CheckDataResultImpl()
    {
        this.check_factory = ((ExecutorServiceFactory)SpringContextHolder.getBean("ExecutorServiceFactoryInstanceCheck"));

        this.itemFactory = new BusinessCheckWorkItemFactory();
    }

    public BusinessSubTaskContext getCheckDataResult(Map<String, List<ICheckData>> stdCheckDatas, Map<String, List<ICheckData>> curCheckDatas, BusinessSubTaskContext subTaskContext, Object extObj)
    {
        this.logger.info("dictID+现网表准备线程准备开始时间-->>>>" + new Date());
        Map<String,ICheckContext> subTaskMap = new HashMap();

        ExecutorService threadPool = this.check_factory.getThreadPool();

        IWorkItemExecutor executor = WorkItemExecutorFactory.getInstance().getWrokItemExecutor("CountDownLatch", threadPool);

        DBDtInfoService service = (DBDtInfoService) DBServiceManager.getInstance().getDBService("db_service_dt");

        for (String key : stdCheckDatas.keySet()) {
            ICheckContext checksubtaskcontext = new BusinessCheckContext();
            checksubtaskcontext.setCurCheckData((List)curCheckDatas.get(key));
            checksubtaskcontext.setStdCheckData((List)stdCheckDatas.get(key));
            checksubtaskcontext.setTaskId(subTaskContext.getTaskId());
            checksubtaskcontext.setSubTaskId(subTaskContext.getSubTaskId());
            checksubtaskcontext.setNe(subTaskContext.getNe());
            checksubtaskcontext.setDictIdQuery(key);
            checksubtaskcontext.setDbFile(subTaskContext.getDbFile());
            //checksubtaskcontext.setCheckLogicMap(subTaskContext.getCheckLogicMap());

            checksubtaskcontext.setCheckType(subTaskContext.getCheckType());
            checksubtaskcontext.setIsCombine(subTaskContext.getIsCombine());

            if (!subTaskContext.getCheckType().equals(EnumFullPartCheck.PARAMECHECK))
            {
                Map result = getPRIORITY(service, key, subTaskContext.getDbFile());

                if ((result.get("PRIORITY") != null) &&
                        (!result
                                .get("PRIORITY")
                                .toString().equals("")))
                    checksubtaskcontext.setDictPriority(Integer.parseInt(result
                            .get("PRIORITY")
                            .toString()));
                else {
                    checksubtaskcontext.setDictPriority(0);
                }

                if ((result.get("ISTEMPLATE") != null) &&
                        (!result
                                .get("ISTEMPLATE")
                                .toString().equals("")))
                    checksubtaskcontext.setDictPriorityDel(Integer.parseInt(result.get("ISTEMPLATE").toString()));
                else {
                    checksubtaskcontext.setDictPriorityDel(0);
                }
                //checksubtaskcontext.setDelParaMap(subTaskContext.getDelParaMap());
            }

            subTaskMap.put(key, checksubtaskcontext);

            IWorkItem workItem = this.itemFactory
                    .getWorkItem(checksubtaskcontext);

            executor.addWorkItem(workItem);
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
