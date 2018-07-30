package com.boco.soap.cmnet.cocurrent.sync.workitem.impl;

import com.boco.soap.cmnet.check.excutor.FactoryCheckLogic;
import com.boco.soap.cmnet.check.result.ICheckDataResult;
import com.boco.soap.cmnet.cocurrent.exception.ThreadWorkException;
import com.boco.soap.cmnet.cocurrent.sync.workitem.IWorkItem;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

import java.util.List;

public class BusinessCheckcheduler implements IWorkItem {
    private String workItemName;
    private BusinessCheckContext checksubTaskContext;

    public BusinessCheckcheduler(BusinessCheckContext checksubTaskContext2)
    {
        this.checksubTaskContext = checksubTaskContext2;
        this.workItemName = createWorkItemName();
    }

    private void execute(BusinessCheckContext checksubTaskContext)
    {
        ICheckDataResult checkDataMain = FactoryCheckLogic.getInstance().getCheckLogicEngine(checksubTaskContext.getCheckType());

        List checkDataResult = checkDataMain.checkDataExcuteResult(checksubTaskContext);

        checksubTaskContext.setCheckDataResult(checkDataResult);
    }

    public String getWorkItemName()
    {
        return this.workItemName;
    }

    public void run()
            throws ThreadWorkException
    {
        execute(this.checksubTaskContext);
    }

    private String createWorkItemName()
    {
        return this.checksubTaskContext.getTaskId() + ";" + this.checksubTaskContext.getCheckType();
    }
}
