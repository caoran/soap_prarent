package com.boco.soap.cmnet.context;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.Ne;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IMainTaskContext {

     public abstract String getTaskId();

     public abstract void setTaskId(String paramString);

     public abstract Map<String, ISubTaskContext> getSubTaskContext();

     public abstract void setSubTaskContext(Map<String, ISubTaskContext> paramMap);

     public abstract ISubTaskContext getSubTaskContextByTask(String paramString);

     public abstract List<BusiDict> getBusiDicts();

     public abstract void setBusiDicts(List<BusiDict> paramList);

     public abstract String getDbFile();

     public abstract void setDbFile(String paramString);

     public abstract boolean isCheck();

     public abstract void setCheck(boolean paramBoolean);

     public abstract boolean isMake();

     public abstract void setMake(boolean paramBoolean);

     public abstract Date getStartDate();

     public abstract void setStartDate(Date paramDate);

     public abstract Date getEndDate();

     public abstract void setEndDate(Date paramDate);
}
