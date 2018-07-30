package com.boco.soap.cmnet.schedule.impl;

import com.boco.soap.cmnet.check.checkdata.CreateCurCheckDataFactory;
import com.boco.soap.cmnet.check.checkdata.IBusinessLoginCurNetData;
import com.boco.soap.cmnet.check.checkdata.ICreateSourceCheckData;
import com.boco.soap.cmnet.check.checkdata.impl.CreateSourceCheckDataImpl;
import com.boco.soap.cmnet.check.result.ICheckDataMainResult;
import com.boco.soap.cmnet.check.result.impl.CheckDataResultImpl;
import com.boco.soap.cmnet.cocurrent.exception.ThreadWorkException;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.db.mongo.OrderMongo;
import com.boco.soap.cmnet.schedule.ISubTaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BusinessSubTaskScheduler implements ISubTaskScheduler<BusinessSubTaskContext>,IWorkItem {
    private final Logger logger = LoggerFactory.getLogger(BusinessSubTaskScheduler.class);
    private String workItemName;
    private BusinessSubTaskContext subTaskContext;

    public BusinessSubTaskScheduler(BusinessSubTaskContext subTaskContext)
    {
        this.subTaskContext = subTaskContext;
        this.workItemName = createWorkItemName();
    }

    @Override
    public void execute(BusinessSubTaskContext subTaskContext) {

        this.logger.info("网元级别线程入口--->>>>" + new Date());

        Map paraSubTask = new ConcurrentHashMap();
        boolean sucessFlag = false;
        String errorMsg = new String("");
        try {
            Map matchResult = new ConcurrentHashMap();

            this.logger.info("现网数据准备开始-->>>>" + new Date());

            IBusinessLoginCurNetData curCheckData = CreateCurCheckDataFactory.getInstance().createCheckData(subTaskContext.getCheckType());

            Map curCheckDataMap = curCheckData.createCurNetCheckDatas(subTaskContext, matchResult, subTaskContext.getBusiDict());

            this.logger.info("现网数据准备结束-->>>>" + new Date());

            this.logger.info("标准数据准备开始-->>>>" + new Date());

            ICreateSourceCheckData stdCheckData = new CreateSourceCheckDataImpl();

            Map stdCheckDataMap = stdCheckData.createSourceCheckData(subTaskContext, matchResult, curCheckDataMap);

            this.logger.info("标准数据准备结束-->>>>" + new Date());

            this.logger.info("网元级别cutDowm开始-->>>>" + new Date());

            ICheckDataMainResult checkDataMain = new CheckDataResultImpl();

            BusinessSubTaskContext mapExecutorResult = checkDataMain.getCheckDataResult(stdCheckDataMap, curCheckDataMap, subTaskContext, new Object());

           /*
            IInstructionGenerate instructionGenerate = InstructionGenerateFactory.getFactory().getInstance(subTaskContext.getCheckType());

            List allInstruction = instructionGenerate.instructionCollections(mapExecutorResult);

            this.logger.info("网元指令排序及入数据库开始-->>>>" + new Date());

            instructionGenerate.instructSortAndSaveDb(allInstruction, mapExecutorResult);*/

            this.logger.info("网元指令排序及入数据库结束-->>>>" + new Date());

            sucessFlag = true;
        } catch (Throwable ex) {
            errorMsg = ex.toString();
            ex.printStackTrace();
            throw new RuntimeException(ex);
        } finally {
            paraSubTask.put("subTaskId", subTaskContext.getSubTaskId());
            paraSubTask.put("errorMsg", errorMsg);
            //service.updateSubTask(paraSubTask, sucessFlag);
        }
    }

    @Override
    public String getWorkItemName()
    {
        return this.workItemName;
    }
    @Override
    public void run()
            throws ThreadWorkException
    {
        execute(this.subTaskContext);
    }
    private String createWorkItemName()
    {
        return this.subTaskContext.getTaskId() + ";" + this.subTaskContext.getBusiDict().getName();
    }
}
