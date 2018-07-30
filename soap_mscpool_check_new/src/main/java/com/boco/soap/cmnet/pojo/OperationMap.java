package com.boco.soap.cmnet.pojo;

public class OperationMap {
    private int ID;
    private int dictid;
    private String standardoperation;
    private int productionmode;
    private String instemplate;
    private int inscategory;
    private int operationtype;
    private int instype;
    private int inssequence;
    private String remark;

    public int getID()
    {
        return this.ID;
    }
    public void setID(int iD) {
        this.ID = iD;
    }
    public int getDictid() {
        return this.dictid;
    }
    public void setDictid(int dictid) {
        this.dictid = dictid;
    }
    public String getStandardoperation() {
        return this.standardoperation;
    }
    public void setStandardoperation(String standardoperation) {
        this.standardoperation = standardoperation;
    }
    public int getProductionmode() {
        return this.productionmode;
    }
    public void setProductionmode(int productionmode) {
        this.productionmode = productionmode;
    }
    public String getInstemplate() {
        return this.instemplate;
    }
    public void setInstemplate(String instemplate) {
        this.instemplate = instemplate;
    }
    public int getInscategory() {
        return this.inscategory;
    }
    public void setInscategory(int inscategory) {
        this.inscategory = inscategory;
    }
    public int getOperationtype() {
        return this.operationtype;
    }
    public void setOperationtype(int operationtype) {
        this.operationtype = operationtype;
    }
    public int getInstype() {
        return this.instype;
    }
    public void setInstype(int instype) {
        this.instype = instype;
    }
    public int getInssequence() {
        return this.inssequence;
    }
    public void setInssequence(int inssequence) {
        this.inssequence = inssequence;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
