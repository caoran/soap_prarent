package com.boco.soap.cmnet.check.result;

public class CheckItemResult {

    /*    */   private boolean checkresult;
    /*    */   private IData stdcheckresult;
    /*    */
/*    */   public boolean isCheckresult()
/*    */   {
/* 29 */     return this.checkresult;
/*    */   }
    /*    */
/*    */   public void setCheckresult(boolean checkresult) {
/* 33 */     this.checkresult = checkresult;
/*    */   }
    /*    */
/*    */   public IData getStdcheckresult() {
/* 37 */     return this.stdcheckresult;
/*    */   }
    /*    */
/*    */   public void setStdcheckresult(IData stdcheckresult) {
/* 41 */     this.stdcheckresult = stdcheckresult;
/*    */   }
}
