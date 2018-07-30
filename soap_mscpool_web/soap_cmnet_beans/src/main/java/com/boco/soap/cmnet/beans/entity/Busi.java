package com.boco.soap.cmnet.beans.entity;

import java.io.Serializable;
import java.util.Date;

public class Busi implements Serializable{
    private String id;

    private Date createTime;

    private String createUserId;

    private String busiName;

    private String menuUrl;

    private Short seqNo;

    private Short isDelete;

    private String imgUrl;

    private String processId;

    private String stdTable;

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

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName == null ? null : busiName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Short getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Short seqNo) {
        this.seqNo = seqNo;
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getStdTable() {
        return stdTable;
    }

    public void setStdTable(String stdTable) {
        this.stdTable = stdTable;
    }

    @Override
    public String toString() {
        return "Busi{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", createUserId=" + createUserId +
                ", stdTable=" + stdTable +
                ", busiName='" + busiName + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", seqNo=" + seqNo +
                ", processId=" + processId +
                ", isDelete=" + isDelete +
                ", imgUrl=" + imgUrl +
                '}';
    }
}