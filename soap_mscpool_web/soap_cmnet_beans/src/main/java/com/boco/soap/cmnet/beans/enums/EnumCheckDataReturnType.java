package com.boco.soap.cmnet.beans.enums;

public enum  EnumCheckDataReturnType {
    NONE, CORRECT, WRONG, LOST, EXECESS, CONFLICT, MIXED_WRONG, MIXED_COLLECT, MIXED_LOST, MIXED_EXECESS, ORIGINAL_EXECESS;
    /*    */
/*    */   public static EnumCheckDataReturnType getEnumCheckDataType(int i) {
/* 10 */     switch (i)
/*    */     {
/*    */     case 0:
/* 13 */       return NONE;
/*    */     case 1:
/* 16 */       return CORRECT;
/*    */     case 2:
/* 19 */       return WRONG;
/*    */     case 3:
/* 22 */       return LOST;
/*    */     case 4:
/* 25 */       return EXECESS;
/*    */     case 5:
/* 28 */       return CONFLICT;
/*    */     }
/*    */
/* 31 */     return NONE;
/*    */   }
    /*    */
/*    */   public static String resultToString(EnumCheckDataReturnType checkResult)
/*    */   {
/* 36 */     String result = "";
/* 37 */     if (CORRECT.equals(checkResult))
/* 38 */       result = "正确";
/* 39 */     else if (WRONG.equals(checkResult))
/* 40 */       result = "错做";
/* 41 */     else if (LOST.equals(checkResult))
/* 42 */       result = "漏做";
/* 43 */     else if (EXECESS.equals(checkResult)) {
/* 44 */       result = "多做";
/*    */     }
/* 46 */     return result;
/*    */   }
}
