package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.beans.enums.EnumFieldType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;

public interface IDataParamDefine {
    public abstract int getItemId();

    public abstract void setItemId(int paramInt);

    public abstract String getParamName();

    public abstract void setParamName(String paramString);

    public abstract EnumFieldUsage getFieldUsage();

    public abstract EnumFieldType getFieldType();

    public abstract void setFieldType(EnumFieldType paramEnumFieldType);

    public abstract void setFieldUsage(EnumFieldUsage paramEnumFieldUsage);
}
