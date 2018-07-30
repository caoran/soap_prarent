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

public class DataCheckReturnImpl implements IDataCheckReturn {

    private EnumCheckDataReturnType checkDataType;
    /*     */   public ICheckData stdCheckData;
    /*     */   public ICheckData curCheckData;
    /*     */   public EnumCheckQuertPairType pairDataType;
    /*     */
/*     */   public EnumCheckDataReturnType getCheckDataType()
/*     */   {
/*  29 */     return this.checkDataType;
/*     */   }
    /*     */
/*     */   public ICheckData getCurCheckData()
/*     */   {
/*  35 */     return this.curCheckData;
/*     */   }
    /*     */
/*     */   public EnumCheckQuertPairType getPairDataType()
/*     */   {
/*  41 */     return this.pairDataType;
/*     */   }
    /*     */
/*     */   public ICheckData getStdCheckData()
/*     */   {
/*  47 */     return this.stdCheckData;
/*     */   }
    /*     */
/*     */   public void setCurCheckData(ICheckData curCheckData)
/*     */   {
/*  53 */     this.curCheckData = curCheckData;
/*     */   }
    /*     */
/*     */   public void setDataType(EnumCheckDataReturnType checkDataType)
/*     */   {
/*  59 */     this.checkDataType = checkDataType;
/*     */   }
    /*     */
/*     */   public void setPairDataType(EnumCheckQuertPairType pairDataType)
/*     */   {
/*  65 */     this.pairDataType = pairDataType;
/*     */   }
    /*     */
/*     */   public void setStdCheckData(ICheckData stdCheckData)
/*     */   {
/*  71 */     this.stdCheckData = stdCheckData;
/*     */   }
    /*     */
/*     */   public String formatString(List<IInstructionParameter> instructParas, ICheckData stdCheckData, ICheckData curCheckData, EnumCheckDataReturnType checkReturnType)
/*     */   {
/*  78 */     StringBuffer sb = new StringBuffer("");
/*  79 */     sb.append("{\"errortype\":\"");
/*  80 */     String result = EnumCheckDataReturnType.resultToString(checkReturnType);
/*  81 */     sb.append(result).append("\",");
/*  82 */     for (IInstructionParameter dictItem : instructParas) {
/*  83 */       String columnType = "";
/*  84 */       if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(1))
/*  85 */         columnType = "-码号";
/*  86 */       else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(2))
/*  87 */         columnType = "-查询项";
/*  88 */       else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(4))
/*  89 */         columnType = "-核查项";
/*  90 */       else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(8))
/*  91 */         columnType = "MakeField";
/*  92 */       else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(9))
/*  93 */         columnType = "-现网字段";
/*  94 */       else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(16))
/*  95 */         columnType = "-动态字段";
/*  96 */       else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(32)) {
/*  97 */         columnType = "-自增长字段";
/*     */       }
/*  99 */       else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(256)) {
/* 100 */         columnType = "-核查显示";
/*     */       }
/* 102 */       else if (dictItem.getFieldUsage() == EnumFieldUsage.valueOf(300)) {
/* 103 */         columnType = "-IP查询项";
/*     */       }
/*     */       else {
/* 106 */         columnType = "-其他";
/*     */       }
/*     */
/* 109 */       String englishName = dictItem.getEnglishName();
/* 110 */       if (stdCheckData != null) {
/* 111 */         boolean stdQueryFlag = false;
/* 112 */         for (IDataItem stDataItem : stdCheckData.getData().getItems()) {
/* 113 */           if ((stDataItem != null) && (stDataItem.getParamName().equals(englishName)))
/*     */           {
/* 115 */             if (EnumFieldUsage.CODE_FILED.equals(stDataItem.getParam().getFieldUsage())) {
/* 116 */               stDataItem.setChineseValue(stdCheckData.getData().getCode());
/* 117 */               stDataItem.setEnglishValue(stdCheckData.getData().getCode());
/* 118 */               sb.append(stDataItem.formatStdParaString(stDataItem, columnType));
/* 119 */               stdQueryFlag = true;
/*     */             } else {
/* 121 */               sb.append(stDataItem.formatStdParaString(stDataItem, columnType));
/* 122 */               stdQueryFlag = true;
/*     */             }
/*     */           }
/*     */         }
/* 126 */         if (!stdQueryFlag) {
/* 127 */           sb.append("\"").append(englishName).append(columnType).append("_std");
/* 128 */           sb.append("\":\"").append("\",");
/*     */         }
/*     */       } else {
/* 131 */         sb.append("\"").append(englishName).append(columnType).append("_std");
/* 132 */         sb.append("\":\"").append("\",");
/*     */       }
/* 134 */       if (curCheckData != null) {
/* 135 */         boolean curQueryFlag = false;
/* 136 */         for (IDataItem curDataItem : curCheckData.getData().getItems()) {
/* 137 */           if ((curDataItem != null) && (curDataItem.getParamName().equals(englishName)))
/*     */           {
/* 139 */             sb.append(curDataItem.formatCurParaString(curDataItem, columnType));
/* 140 */             curQueryFlag = true;
/*     */           }
/*     */         }
/* 143 */         if (!curQueryFlag) {
/* 144 */           sb.append("\"").append(englishName).append(columnType).append("_cur");
/* 145 */           sb.append("\":\"").append("\",");
/*     */         }
/*     */       } else {
/* 148 */         sb.append("\"").append(englishName).append(columnType).append("_cur");
/* 149 */         sb.append("\":\"").append("\",");
/*     */       }
/*     */     }
/* 152 */     sb.delete(sb.length() - 1, sb.length());
/* 153 */     sb.append("}");
/* 154 */     return sb.toString();
/*     */   }
    /*     */
/*     */   public String parameformatString(List<IInstructionParameter> instructParas, IData stddata, IData curdata)
/*     */   {
/* 159 */     return null;
/*     */   }
}
