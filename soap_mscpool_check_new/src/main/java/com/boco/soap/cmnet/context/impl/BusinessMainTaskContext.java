package com.boco.soap.cmnet.context.impl;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.context.IMainTaskContext;
import com.boco.soap.cmnet.context.ISubTaskContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BusinessMainTaskContext implements IMainTaskContext {

    private String taskId;
    private List<BusiDict> busiDicts;
    private String dbFile;
    private boolean isCheck;
    private boolean isMake;
    private Map<String, ISubTaskContext> subTaskContexts;
    private Date startDate;
    private Date endDate;

    private String orderId;

    public BusinessMainTaskContext(List<BusiDict> busiDicts, Map<String, ISubTaskContext> subTaskContexts,String orderId) {
        this.busiDicts = busiDicts;
        this.subTaskContexts = subTaskContexts;
        this.orderId=orderId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public List<BusiDict> getBusiDicts() {
        return this.busiDicts;
    }

    @Override
    public void setBusiDicts(List<BusiDict> paramList) {
        this.busiDicts=paramList;
    }

    public String getDbFile() {
        return this.dbFile;
    }

    public void setDbFile(String dbFile) {
        this.dbFile = dbFile;
    }

    public boolean isCheck() {
        return this.isCheck;
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    public boolean isMake() {
        return this.isMake;
    }

    public void setMake(boolean isMake) {
        this.isMake = isMake;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Map<String, ISubTaskContext> getSubTaskContext()
    {
        return this.subTaskContexts;
    }

    public ISubTaskContext getSubTaskContextByTask(String subTaskId)
    {
        return (ISubTaskContext)this.subTaskContexts.get(subTaskId);
    }

    public void setSubTaskContext(Map<String, ISubTaskContext> subTaskContext)
    {
        this.subTaskContexts = subTaskContext;
    }

    public Map<String, ISubTaskContext> getSubTaskContexts() {
        return subTaskContexts;
    }

    public void setSubTaskContexts(Map<String, ISubTaskContext> subTaskContexts) {
        this.subTaskContexts = subTaskContexts;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
