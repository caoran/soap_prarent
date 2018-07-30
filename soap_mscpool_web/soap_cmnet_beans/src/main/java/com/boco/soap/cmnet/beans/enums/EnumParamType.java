package com.boco.soap.cmnet.beans.enums;

public enum  EnumParamType {
    CONSTANT, VARIANT, GETVALUE;
    /*    */
/*    */   public static EnumParamType getEnumParamType(int i) {
/* 14 */     if (i == 1)
/* 15 */       return CONSTANT;
/* 16 */     if (i == 2)
/* 17 */       return VARIANT;
/* 18 */     if (i == 3) {
/* 19 */       return GETVALUE;
/*    */     }
/* 21 */     return null;
/*    */   }
}
