package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.result.ICheckResultSubTaskEO;

import java.util.Date;

public class CheckResultSubTaskEOImpl implements ICheckResultSubTaskEO{
    private String taskId;
    private String subTaskId;
    private String neName;
    private String dbFile;
    private EnumFullPartCheck checkType;
    private int success;
    private int executionState;
    private Date startDateTime;
    private String solutionIds;

    public CheckResultSubTaskEOImpl(String taskId, String neName, EnumFullPartCheck checkType, int success, int executionState)
    {
        this.taskId = taskId;
        this.neName = neName;
        this.checkType = checkType;
        this.success = success;
        this.executionState = executionState;
        this.startDateTime = new Date();
    }

    public EnumFullPartCheck getCheckType()
    {
        return this.checkType;
    }

    public String getDbFile()
    {
        return this.dbFile;
    }

    public int getExecutionState()
    {
        return this.executionState;
    }

    public String getNeName()
    {
        return this.neName;
    }

    public Date getStartDateTime()
    {
        return this.startDateTime;
    }

    public String getSubTaskId()
    {
        return this.subTaskId;
    }

    public int getSuccess()
    {
        return this.success;
    }

    public String getTaskId()
    {
        return this.taskId;
    }

    public void setCheckType(EnumFullPartCheck checkType)
    {
        this.checkType = checkType;
    }

    public void setDbFile(String dbFile)
    {
        this.dbFile = dbFile;
    }

    public void setExecutionState(int executionState)
    {
        this.executionState = executionState;
    }

    public void setNeName(String neName)
    {
        this.neName = neName;
    }

    public void setStartDateTime(Date startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    public void setSubTaskId(String subTaskId)
    {
        this.subTaskId = subTaskId;
    }

    public void setSuccess(int success)
    {
        this.success = success;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getSolutionIds()
    {
        return this.solutionIds;
    }

    public void setSolutionIds(String solutionIds)
    {
        this.solutionIds = solutionIds;
    }
}
