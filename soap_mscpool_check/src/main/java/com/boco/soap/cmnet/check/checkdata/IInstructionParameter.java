package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.enums.EnumFieldType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.beans.enums.EnumParamType;

public interface IInstructionParameter {
    public abstract String getId();

    public abstract void setId(String paramString);

    public abstract int getDictItemId();

    public abstract void setDictItemId(int paramInt);

    public abstract EnumParamType getParamtype();

    public abstract void setParamtype(EnumParamType paramEnumParamType);

    public abstract EnumFieldUsage getFieldUsage();

    public abstract void setFieldUsage(EnumFieldUsage paramEnumFieldUsage);

    public abstract String getCheckLogic();

    public abstract void setCheckLogic(String paramString);

    public abstract String getChangeName();

    public abstract void setChangeName(String paramString);

    public abstract String getValue();

    public abstract void setValue(String paramString);

    public abstract String getArgs();

    public abstract void setArgs(String paramString);

    public abstract String getVarKey();

    public abstract void setVarKey(String paramString);

    public abstract int getFormat();

    public abstract void setFormat(int paramInt);

    public abstract String getEnglishName();

    public abstract void setEnglishName(String paramString);

    public abstract String getChineseName();

    public abstract void setChineseName(String paramString);

    public abstract EnumFieldType getFieldType();

    public abstract void setFieldType(EnumFieldType paramEnumFieldType);

    public abstract String getChineseValue();

    public abstract void setChineseValue(String paramString);

    public abstract String getEnglishValue();

    public abstract void setEnglishValue(String paramString);

    public abstract String getParamRemark();

    public abstract void setParamRemark(String paramString);

    public abstract int getParamproperty();

    public abstract void setParamproperty(int paramInt);

    public abstract int getPriority();

    public abstract void setPriority(int paramInt);

    public abstract int getIsCore();

    public abstract void setIsCore(int paramInt);

    public abstract IValueSequence getValueSequence();

    public abstract void setValueSequence(IValueSequence paramIValueSequence);

    public abstract boolean isIdentity();

    public abstract void setIdentity(boolean paramBoolean);

    public abstract int getIsSort();

    public abstract void setIsSort(int paramInt);

    public abstract String getStdunion();

    public abstract void setStdunion(String paramString);
}
