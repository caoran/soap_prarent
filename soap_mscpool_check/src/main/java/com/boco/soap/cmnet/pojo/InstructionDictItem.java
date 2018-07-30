package com.boco.soap.cmnet.pojo;

public class InstructionDictItem {
    private String Id;
    private int dictid;
    private int itemid;
    private int priority;
    private String englishname;
    private String chinesename;
    private int paramformat;
    private int paramtype;
    private String createtime;
    private String createuser;
    private String moditime;
    private String modiuser;
    private int status;
    private String remark;
    private int isCore;
    private String paramRemark;

    public String getId()
    {
        return this.Id;
    }
    public void setId(String id) {
        this.Id = id;
    }
    public int getDictid() {
        return this.dictid;
    }
    public void setDictid(int dictid) {
        this.dictid = dictid;
    }
    public int getItemid() {
        return this.itemid;
    }
    public void setItemid(int itemid) {
        this.itemid = itemid;
    }
    public int getPriority() {
        return this.priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public String getEnglishname() {
        return this.englishname;
    }
    public void setEnglishname(String englishname) {
        this.englishname = englishname;
    }
    public String getChinesename() {
        return this.chinesename;
    }
    public void setChinesename(String chinesename) {
        this.chinesename = chinesename;
    }
    public int getParamformat() {
        return this.paramformat;
    }
    public void setParamformat(int paramformat) {
        this.paramformat = paramformat;
    }

    public int getParamtype()
    {
        return this.paramtype;
    }
    public void setParamtype(int paramtype) {
        this.paramtype = paramtype;
    }

    public String getCreatetime() {
        return this.createtime;
    }
    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    public String getCreateuser() {
        return this.createuser;
    }
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }
    public String getModitime() {
        return this.moditime;
    }
    public void setModitime(String moditime) {
        this.moditime = moditime;
    }
    public String getModiuser() {
        return this.modiuser;
    }
    public void setModiuser(String modiuser) {
        this.modiuser = modiuser;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getRemark() {
        return this.remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public int getIsCore() {
        return this.isCore;
    }
    public void setIsCore(int isCore) {
        this.isCore = isCore;
    }
    public String getParamRemark() {
        return this.paramRemark;
    }
    public void setParamRemark(String paramRemark) {
        this.paramRemark = paramRemark;
    }
}
