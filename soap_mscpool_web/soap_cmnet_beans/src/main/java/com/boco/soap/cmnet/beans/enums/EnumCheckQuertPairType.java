package com.boco.soap.cmnet.beans.enums;

public enum  EnumCheckQuertPairType {
    NONE, EQUPAIR, ABBRPAIR, EXTPAIR, NOTPAIR, EXTPARAME;
    /*    */
/*    */   public static EnumCheckQuertPairType getEnumQueryPairPara(int i) {
/*  8 */     switch (i)
/*    */     {
/*    */     case 0:
/* 11 */       return NONE;
/*    */     case 1:
/* 14 */       return EQUPAIR;
/*    */     case 2:
/* 17 */       return ABBRPAIR;
/*    */     case 3:
/* 20 */       return EXTPAIR;
/*    */     case 4:
/* 23 */       return NOTPAIR;
/*    */     case 5:
/* 26 */       return EXTPARAME;
/*    */     }
/*    */
/* 29 */     return NONE;
/*    */   }
}
