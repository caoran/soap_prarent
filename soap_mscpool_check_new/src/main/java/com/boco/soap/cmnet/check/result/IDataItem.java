package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.instruction.CheckOperateMnum;

import java.util.Map;

public interface IDataItem {
    public static final String SEPARATE = "|";

    public abstract String getEnglishValue();

    public abstract String getChineseValue();

    public abstract String getEnglishName();

    public abstract String getChineseName();

    public abstract void setEnglishValue(String paramString);

    public abstract void setChineseValue(String paramString);

    public abstract void setEnglishName(String paramString);

    public abstract void setChineseName(String paramString);

    public abstract IDataParamDefine getParam();

    public abstract void setParam(IDataParamDefine paramIDataParamDefine);

    public abstract String getParamName();

    public abstract StringBuffer formatStdParaString(IDataItem paramIDataItem, String paramString);

    public abstract StringBuffer formatCurParaString(IDataItem paramIDataItem, String paramString);

    public abstract Map<String, String> getStandData();

    public abstract void setStandData(Map<String, String> paramMap);

    public abstract void setKey(String paramString1, String paramString2);

    public abstract String getKey();

    public abstract EnumCheckDataReturnType getItemchecktype();

    public abstract void setItemchecktype(EnumCheckDataReturnType paramEnumCheckDataReturnType);

    public abstract CheckOperateMnum getCheckdataoperator();

    public abstract void setCheckdataoperator(CheckOperateMnum paramCheckOperateMnum);
}
