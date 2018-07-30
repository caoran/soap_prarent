package com.boco.soap.cmnet.beans.entity;

import java.io.Serializable;
import java.util.Date;

public class BusiDictItem implements Serializable {
    private String id;

    private Date createTime;

    private String createUserId;

    private String dictId;

    private String paramName;

    private Short seqNo;

    private String stdName;

    private String curName;

    private int checkLogic;

    private int paramProperty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public Short getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Short seqNo) {
        this.seqNo = seqNo;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName == null ? null : stdName.trim();
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName == null ? null : curName.trim();
    }

    public int getCheckLogic() {
        return checkLogic;
    }

    public void setCheckLogic(int checkLogic) {
        this.checkLogic = checkLogic;
    }

    public int getParamProperty() {

        return paramProperty;
    }

    public void setParamProperty(int paramProperty) {
        this.paramProperty = paramProperty;
    }
}