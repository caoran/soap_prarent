package com.boco.soap.cmnet.context;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.db.mongo.OrderMongo;

import java.util.List;
import java.util.Map;

public interface ICheckContext {
    public abstract String getTaskId();

    public abstract void setTaskId(String paramString);

    public abstract int getTotalCount();

    public abstract void setTotalCount(int  totalCount);

    public abstract List<Ne> getNeList();

    public abstract void setNeList(List<Ne> paramINeElement);

    public abstract EnumFullPartCheck getCheckType();

    public abstract void setCheckType(EnumFullPartCheck paramEnumFullPartCheck);

    public abstract String getSubTaskId();

    public abstract void setSubTaskId(String paramString);

    public abstract String getDictIdQuery();

    public abstract void setDictIdQuery(String paramString);

    public abstract List<ICheckData> getStdCheckData();

    public abstract void setStdCheckData(List<ICheckData> paramList);

    public List<ICheckData> getCurCheckData();

    public void setCurCheckData(List<ICheckData> paramList);

    public abstract Map<String,List<ICheckData>> getCurCheckDataMap();

    public abstract void setCurCheckDataMap(Map<String,List<ICheckData>> paramList);

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

    public abstract void setOrderMongo(OrderMongo paramInt);

    public abstract OrderMongo getOrderMongo();

    public abstract Map<String, String> getDelParaMap();

    public abstract void setDelParaMap(Map<String, String> paramMap);

    public abstract BusiDict getBusiDict();

    public abstract void setBusiDict(BusiDict paramMap);

}
