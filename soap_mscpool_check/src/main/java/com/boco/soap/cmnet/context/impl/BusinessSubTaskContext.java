package com.boco.soap.cmnet.context.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.beans.enums.EnumStatus;
import com.boco.soap.cmnet.context.ICheckContext;
import com.boco.soap.cmnet.context.ISubTaskContext;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Map;

public class BusinessSubTaskContext implements ISubTaskContext  {
    private String taskId;
    private String subTaskId;
    private String dbFile;
    private Ne ne;
    private boolean isCheck;
    private boolean isMake;
    private Date startDate;
    private Date endDate;
    private Map<String,ICheckContext> subTaskMap;

    public BusinessSubTaskContext(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public String getSubTaskId() {
        return subTaskId;
    }

    @Override
    public void setSubTaskId(String paramString) {
        this.subTaskId=paramString;
    }

    @Override
    public Ne getNe() {
        return ne;
    }

    @Override
    public void setNe(Ne paramINeElement) {
        this.ne=paramINeElement;
    }

    @Override
    public boolean isCheck() {
        return false;
    }

    @Override
    public void setCheck(boolean paramBoolean) {
        this.isCheck=paramBoolean;
    }

    @Override
    public boolean isMake() {
        return isMake;
    }

    @Override
    public void setMake(boolean paramBoolean) {
        this.isMake=paramBoolean;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Date paramDate) {
        this.startDate=paramDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Date paramDate) {
        this.endDate=paramDate;
    }

    @Override
    public EnumStatus getStatus() {
        return null;
    }

    @Override
    public void setStatus(EnumStatus paramEnumStatus) {

    }

    @Override
    public Throwable getException() {
        return null;
    }

    @Override
    public void setException(Throwable paramThrowable) {

    }

    public String getDbFile() {
        return dbFile;
    }

    public void setDbFile(String dbFile) {
        this.dbFile = dbFile;
    }

    @Override
    public int getIsCombine() {
        return 0;
    }

    @Override
    public void setIsCombine(int paramInt) {

    }

    @Override
    public EnumFullPartCheck getCheckType() {
        return null;
    }

    @Override
    public void setCheckType(EnumFullPartCheck paramEnumFullPartCheck) {

    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Map<String, ICheckContext> getSubTaskMap() {
        return subTaskMap;
    }

    public void setSubTaskMap(Map<String, ICheckContext> subTaskMap) {
        this.subTaskMap = subTaskMap;
    }
}
