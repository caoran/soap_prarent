package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckQuertPairType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.check.result.IDataItem;

import java.util.List;
import java.util.Map;

public class ParameCheckReturnImpl implements IDataCheckReturn {

    private EnumCheckDataReturnType checkDataType;
    public ICheckData stdCheckData;
    public ICheckData curCheckData;
    public EnumCheckQuertPairType pairDataType;

    @Override
    public EnumCheckDataReturnType getCheckDataType() {
        return this.checkDataType;
    }

    @Override
    public ICheckData getCurCheckData() {
        return this.curCheckData;
    }

    @Override
    public EnumCheckQuertPairType getPairDataType() {
        return this.pairDataType;
    }

    @Override
    public ICheckData getStdCheckData() {
        return this.stdCheckData;
    }

    @Override
    public void setCurCheckData(ICheckData curCheckData) {
        this.curCheckData = curCheckData;
    }

    @Override
    public void setDataType(EnumCheckDataReturnType checkDataType) {
        this.checkDataType = checkDataType;
    }

    @Override
    public void setPairDataType(EnumCheckQuertPairType pairDataType) {
        this.pairDataType = pairDataType;
    }

    @Override
    public void setStdCheckData(ICheckData stdCheckData) {
        this.stdCheckData = stdCheckData;
    }

    @Override
    public String formatString(List<IInstructionParameter> instructParas, ICheckData stdCheckData, ICheckData curCheckData, EnumCheckDataReturnType checkReturnType) {
        StringBuffer sb = new StringBuffer("");
        for (IInstructionParameter dictItem : instructParas) {
            String columnType = "";
            if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(1)) {
                columnType = "-码号";
            } else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(4)) {
                columnType = "-核查项";
            } else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(8)) {
                columnType = "MakeField";
            } else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(256)) {
                columnType = "-核查显示";
            } else {
                columnType = "-其他";
            }

            String englishName = dictItem.getEnglishName();

            if (!englishName.equals("GUUID")) {
                String collectkey = "";
                String filepath = "";

                boolean isappend = false;
                if (stdCheckData != null) {
                    IData idata = stdCheckData.getDatas().get(0);
                    collectkey = idata.getCollectkey();
                    filepath = idata.getLogfile();

                    for (IDataItem stDataItem : stdCheckData.getDatas().get(0).getItems()) {
                        if ((stDataItem != null) && (stDataItem.getParamName() != null)) {
                            if (stDataItem.getParamName().equals(englishName)) {
                                sb.append("{\"errortype\":\"");
                                String result = EnumCheckDataReturnType.resultToString(stDataItem.getItemchecktype());
                                sb.append(result).append("\",");

                                String checkitemvalue = "";

                                if ((collectkey != null) && (!collectkey.equals(""))) {
                                    checkitemvalue = stdCheckData.getDatas().get(0).getCheckitem() + "(" + collectkey + ")";
                                } else {
                                    checkitemvalue = stdCheckData.getDatas().get(0).getCheckitem();
                                }

                                sb.append("\"核查项\":\"").append(checkitemvalue).append("\",");
                                sb.append("\"参数名称\":\"").append(stDataItem.getChineseName()).append("\",");
                                sb.append("\"operatortype\":\"").append(stDataItem.getCheckdataoperator().getOperatorname()).append("\",");
                                sb.append(this.formatStdParaString(stDataItem, columnType));
                                isappend = true;
                                break;
                            }
                        } else {
                            sb.append("{\"errortype\":\"");
                            String result = "异常:参数英文名称匹配异常";
                            sb.append(result).append("\",");
                            sb.append("\"核查项\":\"").append(stdCheckData.getDatas().get(0).getCheckitem()).append("\",");
                            sb.append("\"参数名称\":\"").append(stDataItem.getChineseName()).append("\",");
                            sb.append(this.formatStdParaString(stDataItem, columnType));
                            break;
                        }
                    }

                }

                if (isappend) {
                    if (curCheckData != null) {
                        IData curdata = curCheckData.getDatas().get(0);
                        for (IDataItem curDataItem : curdata.getItems()) {
                            if ((curDataItem != null) && (curDataItem.getParamName() != null)) {
                                if (curDataItem.getParamName().equals(englishName)) {
                                    sb.append(this.formatCurParaString(curDataItem, columnType, filepath));
                                    sb.append("},").append("\n");
                                    break;
                                }
                            } else {
                                sb.append("\"现网值_cur\":\"现网采集信息异常！\"");
                                sb.append(",\"curlogfile\":\"\"");
                                sb.append("},").append("\n");
                                break;
                            }
                        }
                    } else {
                        sb.append("\"").append(englishName).append(columnType).append("_cur");
                        sb.append(",\"collectkey\":\"\"");
                        sb.append(",\"curlogfile\":\"\"");
                        sb.append("\":\"").append("\",");
                    }
                }
            }
        }
        return sb.toString();
    }

    private StringBuffer formatStdParaString(IDataItem dataItem, String columnType) {
        StringBuffer sb = new StringBuffer();
        sb.append("\"标准值_std");
        String tmpString = dataItem.getEnglishValue();

        if (dataItem.getChineseValue() != null) {
            tmpString = tmpString.replace("\"", "\\\"");
        }

        sb.append("\":\"").append(tmpString).append("\",");
        return sb;
    }

    private StringBuffer formatCurParaString(IDataItem dataItem, String columnType, String logfile) {
        StringBuffer sb = new StringBuffer();
        sb.append("\"现网值_cur");
        String tmpString = dataItem.getEnglishValue();

        if (dataItem.getChineseValue() != null) {
            tmpString = tmpString.replace("\"", "\\\"");
        }
        sb.append("\":\"").append(tmpString).append("\"");

        sb.append(",\"curlogfile\":\"").append(logfile).append("\"");

        return sb;
    }

    @Override
    public String parameformatString(List<IInstructionParameter> instructParas, IData stddata, IData curdata) {
        StringBuffer sb = new StringBuffer("");
        for (IInstructionParameter dictItem : instructParas) {
            String columnType = "";
            if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(1)) {
                columnType = "-码号";
            } else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(4)) {
                columnType = "-核查项";
            } else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(8)) {
                columnType = "MakeField";
            } else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(256)) {
                columnType = "-核查显示";
            } else {
                columnType = "-其他";
            }

            String englishName = dictItem.getEnglishName();

            if (!englishName.equals("GUUID")) {
                String collectkey = "";
                String filepath = "";

                if (this.stdCheckData != null) {
                    collectkey = stddata.getCollectkey();
                    filepath = stddata.getLogfile();

                    for (IDataItem stDataItem : stddata.getItems()) {
                        if ((stDataItem != null) && (stDataItem.getParamName() != null)) {
                            if (stDataItem.getParamName().equals(englishName)) {
                                sb.append("{\"errortype\":\"");
                                String result = EnumCheckDataReturnType.resultToString(stDataItem.getItemchecktype());
                                sb.append(result).append("\",");

                                String checkitemvalue = "";

                                if ((collectkey != null) && (!collectkey.equals(""))) {
                                    checkitemvalue = stddata.getCheckitem() + "(" + collectkey + ")";
                                } else {
                                    checkitemvalue = stddata.getCheckitem();
                                }

                                sb.append("\"核查项\":\"").append(checkitemvalue).append("\",");
                                sb.append("\"参数名称\":\"").append(stDataItem.getChineseName()).append("\",");
                                sb.append("\"operatortype\":\"").append(stDataItem.getCheckdataoperator().getOperatorname()).append("\",");
                                sb.append(this.formatStdParaString(stDataItem, columnType));
                                break;
                            }

                        } else {
                            sb.append("{\"errortype\":\"");
                            String result = "异常:参数英文名称匹配异常";
                            sb.append(result).append("\",");
                            sb.append("\"核查项\":\"").append(stddata.getCheckitem()).append("\",");
                            sb.append("\"参数名称\":\"").append(stDataItem.getChineseName()).append("\",");
                            sb.append(this.formatStdParaString(stDataItem, columnType));
                            break;
                        }
                    }
                }

                if (this.curCheckData != null) {
                    Map stditems = stddata.getCheck();
                    IDataItem std = (IDataItem) stditems.get(englishName);

                    if (std != null) {
                        for (IDataItem curDataItem : curdata.getItems()) {
                            if ((curDataItem != null) && (curDataItem.getParamName() != null)) {
                                if (curDataItem.getParamName().equals(englishName)) {
                                    sb.append(this.formatCurParaString(curDataItem, columnType, filepath));
                                    sb.append("},").append("\n");
                                    break;
                                }
                            } else {
                                sb.append("\"现网值_cur\":\"现网采集信息异常！\"");
                                sb.append(",\"curlogfile\":\"\"");
                                sb.append("},").append("\n");
                                break;
                            }
                        }
                    }
                } else {
                    sb.append("\"").append(englishName).append(columnType).append("_cur");
                    sb.append(",\"collectkey\":\"\"");
                    sb.append(",\"curlogfile\":\"\"");
                    sb.append("\":\"").append("\",");
                }
            }
        }

        return sb.toString();
    }
}
