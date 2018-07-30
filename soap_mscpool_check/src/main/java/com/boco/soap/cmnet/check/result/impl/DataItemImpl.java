package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.check.instruction.CheckOperateMnum;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.check.result.IDataParamDefine;

import java.util.Map;

public class DataItemImpl implements IDataItem{
    private IDataParamDefine param;
    private String chineseValue;
    private String englishValue;
    private String englishName;
    private String chineseName;
    private String key;
    private Map<String, String> StandData;
    private EnumCheckDataReturnType itemchecktype;
    private CheckOperateMnum checkdataoperator;

    public DataItemImpl(IDataParamDefine param, CheckOperateMnum checkdataoperator, Map<String, String> map)
    {
        this.param = param;

        String englishValue = (String)map.get("englishValue");
        String chineseValue = (String)map.get("chineseValue");
        String englishName = (String)map.get("englishName");
        String chineseName = (String)map.get("chineseName");

        this.chineseValue = chineseValue;
        this.englishName = englishName;
        this.englishValue = englishValue;
        this.chineseName = chineseName;
        this.checkdataoperator = checkdataoperator;
    }

    public DataItemImpl(IDataParamDefine param, String englishValue, String chineseValue)
    {
        this.param = param;
        this.chineseValue = chineseValue;
        this.englishValue = englishValue;
    }

    public String getChineseValue()
    {
        return this.chineseValue;
    }

    public String getEnglishValue()
    {
        return this.englishValue;
    }

    public IDataParamDefine getParam()
    {
        return this.param;
    }

    public String getParamName()
    {
        return this.param.getParamName();
    }

    public void setChineseValue(String chineseValue)
    {
        this.chineseValue = chineseValue;
    }

    public void setEnglishValue(String englishValue)
    {
        this.englishValue = englishValue;
    }

    public void setParam(IDataParamDefine param)
    {
        this.param = param;
    }

    public Map<String, String> getStandData() {
        return this.StandData;
    }

    public void setStandData(Map<String, String> standData) {
        this.StandData = standData;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String dictid, String englishName) {
        this.key = (dictid + "|" + englishName);
    }

    public StringBuffer formatStdParaString(IDataItem dataItem, String columnType)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("\"").append(dataItem.getParamName()).append(columnType).append("_std");
        String tmpString = dataItem.getChineseValue();

        if (dataItem.getChineseValue() != null) {
            tmpString = tmpString.replace("\"", "\\\"");
        }

        sb.append("\":\"").append(tmpString).append("\",");
        return sb;
    }

    public StringBuffer formatCurParaString(IDataItem dataItem, String columnType)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("\"").append(dataItem.getParamName()).append(columnType).append("_cur");
        String tmpString = dataItem.getChineseValue();

        if (dataItem.getChineseValue() != null) {
            tmpString = tmpString.replace("\"", "\\\"");
        }
        sb.append("\":\"").append(tmpString).append("\",");

        return sb;
    }

    private CheckOperateMnum getcheckOperateMnum(String checkLogic) {
        int type = Integer.parseInt(checkLogic);
        return CheckOperateMnum.getCheckOperateMnum(type);
    }

    public String getEnglishName() {
        return this.englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return this.chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public EnumCheckDataReturnType getItemchecktype() {
        return this.itemchecktype;
    }

    public void setItemchecktype(EnumCheckDataReturnType itemchecktype) {
        this.itemchecktype = itemchecktype;
    }

    public CheckOperateMnum getCheckdataoperator() {
        return this.checkdataoperator;
    }

    public void setCheckdataoperator(CheckOperateMnum checkdataoperator) {
        this.checkdataoperator = checkdataoperator;
    }
}
