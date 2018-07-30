package com.boco.soap.cmnet.context;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;

import java.util.List;
import java.util.Map;

public interface ICheckContext {
    public abstract String getTaskId();

    public abstract void setTaskId(String paramString);

    public abstract Ne getNe();

    public abstract void setNe(Ne paramINeElement);

    public abstract EnumFullPartCheck getCheckType();

    public abstract void setCheckType(EnumFullPartCheck paramEnumFullPartCheck);

    public abstract String getSubTaskId();

    public abstract void setSubTaskId(String paramString);

    public abstract String getDictIdQuery();

    public abstract void setDictIdQuery(String paramString);

    public abstract List<ICheckData> getStdCheckData();

    public abstract void setStdCheckData(List<ICheckData> paramList);

    public abstract List<ICheckData> getCurCheckData();

    public abstract void setCurCheckData(List<ICheckData> paramList);

    public abstract List<List<IDataCheckReturn>> getCheckDataResult();

    public abstract void setCheckDataResult(List<List<IDataCheckReturn>> paramList);

    public abstract List<IInstructionParameter> getInstructParas();

    public abstract void setInstructParas(List<IInstructionParameter> paramList);

    public abstract int getDictPriority();

    public abstract void setDictPriority(int paramInt);

    public abstract String getDbFile();

    public abstract void setDbFile(String paramString);

    public abstract void setDictPriorityDel(int paramInt);

    public abstract int getDictPriorityDel();

    public abstract void setCheckLogicMap(int paramInt);

    public abstract void setIsCombine(int paramInt);

    public abstract Map<String, String> getDelParaMap();

    public abstract void setDelParaMap(Map<String, String> paramMap);
}
