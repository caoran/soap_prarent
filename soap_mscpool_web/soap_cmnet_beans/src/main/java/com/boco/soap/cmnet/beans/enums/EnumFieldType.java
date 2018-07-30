package com.boco.soap.cmnet.beans.enums;

public enum EnumFieldType {
    NONE, KEY, CHECK, BUILD, LOCAL;
    /*    */
/*    */   public static EnumFieldType getEnumParamUsage(int i) {
/* 12 */     switch (i) {
/*    */     case 0:
/* 14 */       return NONE;
/*    */     case 1:
/* 17 */       return KEY;
/*    */     case 2:
/* 20 */       return CHECK;
/*    */     case 3:
/* 23 */       return BUILD;
/*    */     case 4:
/* 26 */       return LOCAL;
/*    */     }
/*    */
/* 29 */     return NONE;
/*    */   }
    /*    */
/*    */   public static EnumFieldType transformEnumParamUsage(EnumFieldUsage fieldUsage)
/*    */   {
/* 40 */     EnumFieldType result = NONE;
/* 41 */     switch (fieldUsage.ordinal()) {
/*    */     case 1:
/* 43 */       result = CHECK;
/* 44 */       break;
/*    */     case 2:
/* 46 */       result = KEY;
/* 47 */       break;
/*    */     case 3:
/* 49 */       result = KEY;
/*    */     case 4:
/* 51 */       result = KEY;
/* 52 */       break;
/*    */     case 5:
/* 54 */       result = KEY;
/* 55 */       break;
/*    */     case 6:
/* 57 */       result = KEY;
/* 58 */       break;
/*    */     case 7:
/* 60 */       result = KEY;
/* 61 */       break;
/*    */     case 8:
/* 63 */       result = LOCAL;
/* 64 */       break;
/*    */     case 9:
/* 66 */       result = LOCAL;
/* 67 */       break;
/*    */     case 10:
/* 69 */       result = BUILD;
/* 70 */       break;
/*    */     case 11:
/* 72 */       result = NONE;
/* 73 */       break;
/*    */     default:
/* 75 */       result = NONE;
/*    */     }
/*    */
/* 78 */     return result;
/*    */   }
}
