package com.boco.soap.cmnet.check.instruction;

public enum  CheckOperateMnum {
/* 19 */   EQUALS("相等"), MORE_THAN("大于"),
/* 20 */   LESS_THAN("小于"), MORE_OR_EUQALSE("大于等于"),
/* 21 */   LESS_OR_EQUALSE("小于等于"), CONTAIN("包含"),
/* 22 */   ENUM("枚举"), RANG("区间"),
/* 23 */   BE_CONTAINED("被包含"), IGONRE_EQUALSE("忽略大小写"),
/* 24 */   COLECTION("集合"), ADDCOLECTION(""),
/* 25 */   TIME_LESS_THAN("时间小于"), REGEX_OPERATE("正则"),
/* 26 */   RANGE_CONTAINS_LESS_THAN("区间大于等于"), RANGE_BY_CONTAINS_LESS_THAN("区间小于"), BIT_OPERATE("比特");
/*    */
/*    */   private String operatorname;
/*    */
/*    */   private CheckOperateMnum(String operatorname) {
/* 31 */     this.operatorname = operatorname;
/*    */   }
/*    */
/*    */   public static CheckOperateMnum getCheckOperateMnum(int type)
/*    */   {
/* 40 */     switch (type)
/*    */     {
/*    */     case 1:
/* 43 */       return EQUALS;
/*    */     case 2:
/* 45 */       return MORE_THAN;
/*    */     case 3:
/* 47 */       return LESS_THAN;
/*    */     case 4:
/* 49 */       return MORE_OR_EUQALSE;
/*    */     case 5:
/* 51 */       return LESS_OR_EQUALSE;
/*    */     case 6:
/* 53 */       return CONTAIN;
/*    */     case 7:
/* 55 */       return ENUM;
/*    */     case 8:
/* 57 */       return RANG;
/*    */     case 9:
/* 59 */       return BE_CONTAINED;
/*    */     case 10:
/* 61 */       return IGONRE_EQUALSE;
/*    */     case 11:
/* 63 */       return COLECTION;
/*    */     case 12:
/* 65 */       return ADDCOLECTION;
/*    */     case 13:
/* 67 */       return TIME_LESS_THAN;
/*    */     case 14:
/* 69 */       return REGEX_OPERATE;
/*    */     case 15:
/* 71 */       return BIT_OPERATE;
/*    */     case 16:
/* 73 */       return RANGE_CONTAINS_LESS_THAN;
/*    */     case 17:
/* 75 */       return RANGE_BY_CONTAINS_LESS_THAN;
/*    */     }
/*    */
/* 78 */     return EQUALS;
/*    */   }
    /*    */
/*    */   public String getOperatorname()
/*    */   {
/* 83 */     return this.operatorname;
/*    */   }
    /*    */
/*    */   public void setOperatorname(String operatorname) {
/* 87 */     this.operatorname = operatorname;
/*    */   }
    /*    */
/*    */   public static void main(String[] args)
/*    */   {
/* 93 */     CheckOperateMnum type = EQUALS;
/*    */
/* 95 */     System.out.println(type.getOperatorname());
/*    */   }
}
