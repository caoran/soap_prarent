package com.boco.soap.cmnet.context;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.beans.enums.EnumStatus;

import java.util.Date;
import java.util.Map;

public interface ISubTaskContext {
    public abstract String getTaskId();

    public abstract String getSubTaskId();

    public abstract void setSubTaskId(String paramString);

    public abstract Ne getNe();

    public abstract void setNe(Ne paramINeElement);


    public abstract boolean isCheck();

    public abstract void setCheck(boolean paramBoolean);

    public abstract boolean isMake();

    public abstract void setMake(boolean paramBoolean);

    public abstract EnumFullPartCheck getCheckType();

    public abstract void setCheckType(EnumFullPartCheck paramEnumFullPartCheck);

    public abstract Date getStartDate();

    public abstract void setStartDate(Date paramDate);

    public abstract Date getEndDate();

    public abstract void setEndDate(Date paramDate);

    public abstract EnumStatus getStatus();

    public abstract void setStatus(EnumStatus paramEnumStatus);

    public abstract Throwable getException();

    public abstract void setException(Throwable paramThrowable);

    public abstract int getIsCombine();

    public abstract void setIsCombine(int paramInt);


    public void setSubTaskMap(Map<String, ICheckContext> subTaskMap);


    public abstract String getDbFile();

    public abstract void setDbFile(String paramString);


}
