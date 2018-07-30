package com.boco.soap.cmnet.beans.entity;

import java.io.Serializable;

public class OrderResultDetail implements Serializable {
    private Short id;

    private Short orderId;

    private Short createTime;

    private Short createUserId;

    private Short busiId;

    private Short dictId;

    private Short dictParamId;

    private String stdVal;

    private String curVal;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Short getOrderId() {
        return orderId;
    }

    public void setOrderId(Short orderId) {
        this.orderId = orderId;
    }

    public Short getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Short createTime) {
        this.createTime = createTime;
    }

    public Short getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Short createUserId) {
        this.createUserId = createUserId;
    }

    public Short getBusiId() {
        return busiId;
    }

    public void setBusiId(Short busiId) {
        this.busiId = busiId;
    }

    public Short getDictId() {
        return dictId;
    }

    public void setDictId(Short dictId) {
        this.dictId = dictId;
    }

    public Short getDictParamId() {
        return dictParamId;
    }

    public void setDictParamId(Short dictParamId) {
        this.dictParamId = dictParamId;
    }

    public String getStdVal() {
        return stdVal;
    }

    public void setStdVal(String stdVal) {
        this.stdVal = stdVal == null ? null : stdVal.trim();
    }

    public String getCurVal() {
        return curVal;
    }

    public void setCurVal(String curVal) {
        this.curVal = curVal == null ? null : curVal.trim();
    }
}