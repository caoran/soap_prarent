package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.enums.EnumCombineType;

import java.util.List;

public interface IBusinessInstruction {
    public abstract String getId();

    public abstract String getName();

    public abstract String getLogTableName();

    public abstract String getDeviceColumnName();

    public abstract String getStandTableName();

    public abstract boolean isConflictCheck();

    public abstract int getDictId();

    public abstract int getAddSort();

    public abstract int getDelSort();

    public abstract String getSysKey();

    public abstract String getSysValue();

    public abstract List<IInstructionParameter> getParams();

    public abstract void setParam(IInstructionParameter paramIInstructionParameter);

    public abstract int getCodeFiledIndex();

    public abstract List<Integer> getCheckFiledIndexs();

    public abstract List<Integer> getQueryFiledIndexs();

    public abstract String getCodeFiledName();

    public abstract List<String> getQueryFiledNames();

    public abstract List<String> getCheckFiledNames();

    public abstract boolean isFuzzyCheck();

    public abstract void setFuzzyCheck(boolean paramBoolean);

    public abstract int getPosition();

    public abstract void setPosition(int paramInt);

    public abstract boolean isMerge();

    public abstract void setMerge(boolean paramBoolean);

    public abstract String getFilterExpression();

    public abstract void setFilterExpression(String paramString);

    public abstract String getGroupid();

    public abstract void setGroupid(String paramString);

    public abstract boolean isMake();

    public abstract void setStdUnionField(String paramString);

    public abstract String getStdUnionField();

    public abstract void setCombineType(EnumCombineType paramString);

    public abstract EnumCombineType getCombineType();


}
