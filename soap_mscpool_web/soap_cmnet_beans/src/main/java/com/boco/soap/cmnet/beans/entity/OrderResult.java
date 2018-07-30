package com.boco.soap.cmnet.beans.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderResult implements Serializable {
    private String id;

    private Date createTime;

    private String createUserId;

    private Short rightNo;

    private Short wrongNo;

    private Short lostNo;

    private Short moreNo;

    private String orderId;

    private String busiId;

    private String dictId;

    private String curTable;

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
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Short getRightNo() {
        return rightNo;
    }

    public void setRightNo(Short rightNo) {
        this.rightNo = rightNo;
    }

    public Short getWrongNo() {
        return wrongNo;
    }

    public void setWrongNo(Short wrongNo) {
        this.wrongNo = wrongNo;
    }

    public Short getLostNo() {
        return lostNo;
    }

    public void setLostNo(Short lostNo) {
        this.lostNo = lostNo;
    }

    public Short getMoreNo() {
        return moreNo;
    }

    public void setMoreNo(Short moreNo) {
        this.moreNo = moreNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    public String getCurTable() {
        return curTable;
    }

    public void setCurTable(String curTable) {
        this.curTable = curTable == null ? null : curTable.trim();
    }
}