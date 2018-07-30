package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.enums.EnumFieldType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.result.IDataParamDefine;

public class DataParamDefineImpl implements IDataParamDefine {
    private EnumFieldUsage fieldUsage;
    private EnumFieldType fieldType;
    private int itemId;
    private String paramName;

    public DataParamDefineImpl() {
    }

    public DataParamDefineImpl(IInstructionParameter param) {
        this.fieldUsage = param.getFieldUsage();
        this.fieldType = param.getFieldType();
        this.itemId = param.getDictItemId();
        this.paramName = param.getEnglishName();
    }

    public DataParamDefineImpl(int itemId, String paramName, EnumFieldUsage fieldUsage) {
        this.itemId = itemId;
        this.fieldUsage = fieldUsage;
        this.paramName = paramName;
        this.fieldType = EnumFieldType.transformEnumParamUsage(fieldUsage);
    }

    @Override
    public EnumFieldType getFieldType() {
        return this.fieldType;
    }

    @Override
    public EnumFieldUsage getFieldUsage() {
        return this.fieldUsage;
    }

    @Override
    public int getItemId() {
        return this.itemId;
    }

    @Override
    public String getParamName() {
        return this.paramName;
    }

    @Override
    public void setFieldType(EnumFieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public void setFieldUsage(EnumFieldUsage filedUsage) {
        this.fieldUsage = filedUsage;
    }

    @Override
    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
