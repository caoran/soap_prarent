package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;

import java.util.List;
import java.util.Map;

public interface ICheckData {
    public abstract EnumCheckDataType getDataType();

    public abstract void setDataType(EnumCheckDataType paramEnumCheckDataType);

    public abstract void setExitNet(boolean paramBoolean);

    public abstract boolean isExitNet();

    public abstract boolean isUnion();

    public abstract List<ICheckData> getUnionCheckData();

    public abstract void setUnionCheckData(List<ICheckData> paramList);

    public abstract boolean isInitiativeRelevance();

    public abstract boolean isPassivityRelevance();

    public abstract IData getData();

    public abstract void setData(IData paramIData);

    public abstract List<IData> getDatas();

    public abstract void setDatas(List<IData> paramList);

    public abstract ICheckData getKeyEquData();

    public abstract void setKeyEquData(ICheckData paramICheckData);

    public abstract ICheckData getKeyAbbrData();

    public abstract void setKeyAbbrData(ICheckData paramICheckData);

    public abstract List<ICheckData> getKeyExtDatas();

    public abstract void setKeyExtDatas(List<ICheckData> paramList);

    public abstract void setKeyExtDatas(ICheckData paramICheckData);

    public abstract boolean isCurPairChecked();

    public abstract void setCurPairChecked(boolean paramBoolean);

    public abstract boolean isStdChecked();

    public abstract void setStdChecked(boolean paramBoolean);

    public abstract String getRangeCheckedBeg();

    public abstract void setRangeCheckedBeg(String paramLong);

    public abstract String getRangeCheckedEnd();

    public abstract void setRangeCheckedEnd(String paramLong);

    public abstract void setRangePair(ICheckData paramICheckData);

    public abstract List<ICheckData> getRangePair();

    public abstract void setRangeCheckFlag(boolean paramBoolean);

    public abstract boolean isRangeCheckFlag();

    public abstract Map<String, List<ICheckData>> getStdRelation();

    public abstract void setStdRelation(ICheckData paramICheckData, String paramString);

    public abstract Map<String, List<ICheckData>> getCurRelation();

    public abstract void setCurRelation(ICheckData paramICheckData, String paramString);

    public abstract void setRangeCheckCurFlag(boolean paramBoolean);

    public abstract boolean isRangeCheckCurFlag();


}
