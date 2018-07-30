package com.boco.soap.cmnet.beans.enums;

public enum EnumCheckType {
    NONE, EQUCHECK, ABBRCHECK, EXTCHECK, CHECKEXECESS, RANGECHECK, PARAMEEXTCHECK, PARAMEEQUALCHECK;
    /*    */
/*    */   public static EnumCheckType getEnumCheckTypePara(int i) {
/*  8 */     switch (i)
/*    */     {
/*    */     case 0:
/* 11 */       return NONE;
/*    */     case 1:
/* 14 */       return EQUCHECK;
/*    */     case 2:
/* 17 */       return ABBRCHECK;
/*    */     case 3:
/* 20 */       return EXTCHECK;
/*    */     case 4:
/* 23 */       return CHECKEXECESS;
/*    */     case 6:
/* 26 */       return PARAMEEXTCHECK;
/*    */     case 7:
/* 29 */       return PARAMEEQUALCHECK;
/*    */     case 5:
/*    */     }
/* 32 */     return NONE;
/*    */   }
}
