package com.boco.soap.cmnet.beans.enums;

public enum  EnumFieldUsage {
    NO_USED(0),
/*     */
/*  13 */   CODE_FILED(1),
    /*     */
/*  15 */   QUERY_FIELD(2),
/*     */
/*  17 */   CHECK_FIELD(4),
    /*     */
/*  19 */   MAKE_FIELD(8),
/*     */
/*  21 */   LOG_FIELD(9),
    /*     */
/*  23 */   COLLECT(15),
/*     */
/*  25 */   DYNAMIC_FIELD(16),
    /*     */
/*  27 */   IDENTITY(32),
/*     */
/*  29 */   RANGE_DOWN(64),
    /*     */
/*  31 */   RANGE_UP(128),
/*     */
/*  33 */   CHECK_SHOW(256),
    /*     */
/*  35 */   IPQUERY_FIELD(300);
    /*     */
/*     */   private final int value;
    /*     */
/*     */   private EnumFieldUsage(int value) {
/*  40 */     this.value = value;
/*     */   }
    /*     */
/*     */   public static EnumFieldUsage valueOf(int value)
/*     */   {
/*  45 */     switch (value) {
/*     */     case 0:
/*  47 */       return NO_USED;
/*     */     case 1:
/*  49 */       return CODE_FILED;
/*     */     case 2:
/*  51 */       return QUERY_FIELD;
/*     */     case 4:
/*  53 */       return CHECK_FIELD;
/*     */     case 8:
/*  55 */       return MAKE_FIELD;
/*     */     case 9:
/*  57 */       return LOG_FIELD;
/*     */     case 15:
/*  59 */       return COLLECT;
/*     */     case 16:
/*  61 */       return DYNAMIC_FIELD;
/*     */     case 32:
/*  63 */       return IDENTITY;
/*     */     case 512:
/*  66 */       return RANGE_UP;
/*     */     case 1024:
/*  69 */       return RANGE_DOWN;
/*     */     case 256:
/*  72 */       return CHECK_SHOW;
/*     */     case 300:
/*  74 */       return IPQUERY_FIELD;
/*     */     }
/*     */
/*  77 */     return NO_USED;
/*     */   }
    /*     */
/*     */   public static EnumFieldUsage curvalueOf(int value)
/*     */   {
/*  87 */     switch (value) {
/*     */     case 1:
/*  89 */       return QUERY_FIELD;
/*     */     case 2:
/*  91 */       return CHECK_FIELD;
/*     */     case 3:
/*  93 */       return CODE_FILED;
/*     */     case 4:
/*  95 */       return RANGE_UP;
/*     */     case 5:
/*  97 */       return RANGE_DOWN;
/*     */     }
/*     */
/* 100 */     return CHECK_FIELD;
/*     */   }
    /*     */
/*     */   public int getValue()
/*     */   {
/* 109 */     return this.value;
/*     */   }
}
