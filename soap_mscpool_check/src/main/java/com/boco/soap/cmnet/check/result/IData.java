package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;

import java.util.List;
import java.util.Map;

public interface IData {


    public abstract Map<String, String> getStandData();

    public abstract void setStandData(Map<String, String> paramMap);

    public abstract String getKey();

    public abstract String getCode();

    public abstract String getQuery();

    public abstract String getIpQuery();

    public abstract Map<String, IDataItem> getCheck();

    public abstract IDataItem getItemByName(String paramString);

    public abstract IDataItem getItemByIndex(int paramInt);

    public abstract List<IDataItem> getItems();

    public abstract void setItems(List<IDataItem> paramList);

    public abstract String getValueByName(String paramString, int paramInt);

    public abstract String getValueByIndex(int paramInt1, int paramInt2);

    public abstract String getRangeCheckedBeg();

    public abstract void setRangeCheckedBeg(String paramLong);

    public abstract String getRangeCheckedEnd();

    public abstract void setRangeCheckedEnd(String paramLong);

    public abstract String getCheckitem();

    public abstract String getCollectkey();

    public abstract void setCollectkey(String paramString);

    public abstract String getLogfile();

    public abstract void setLogfile(String paramString);

    public abstract void setInstruction(IBusinessInstruction instruction);

    public abstract IBusinessInstruction getInstruction();

}
