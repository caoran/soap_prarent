package com.boco.soap.cmnet.beans.entity;

import java.io.Serializable;
import java.util.Date;

public class Menu implements Serializable {
    private String id;

    private String menuName;

    private Date createTime;

    private String createUserId;

    private Date modifyTime;

    private Short modifyUserId;

    private String parentId;

    private Short isDelete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Short getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(Short modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }
}