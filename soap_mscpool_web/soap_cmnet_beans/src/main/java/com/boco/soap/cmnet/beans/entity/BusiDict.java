package com.boco.soap.cmnet.beans.entity;

import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusiDict implements Serializable{
    private String id;

    private Date createTime;

    private String createUserId;

    private String busiId;

    private Short isDelete;

    private String name;

    private String curTable;

    private boolean checked;

    private List<String> itemNames;

    private List<BusiDictItem> busiDictItems;

    public List<String> getItemNames() {
        List busiNamesList = new ArrayList();
        if (busiDictItems!=null) {
            for (BusiDictItem item : this.busiDictItems) {
                busiNamesList.add(item.getParamName());
            }
        }
        return busiNamesList;
    }

    public List<String> getKeyStr(){
        List busiNamesList = new ArrayList();
        if (busiDictItems!=null) {
            for (BusiDictItem item : this.busiDictItems) {
                if (EnumFieldUsage.valueOf(item.getParamProperty())== EnumFieldUsage.CODE_FILED)
                busiNamesList.add(item.getParamName());
            }
        }
        return busiNamesList;
    }

    public void setItemNames(List<String> itemNames) {
        this.itemNames = itemNames;
    }

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

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public Short getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BusiDictItem> getBusiDictItems() {
        return busiDictItems;
    }

    public void setBusiDictItems(List<BusiDictItem> busiDictItems) {
        this.busiDictItems = busiDictItems;
    }

    public String getCurTable() {
        return curTable;
    }

    public void setCurTable(String curTable) {
        this.curTable = curTable;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}