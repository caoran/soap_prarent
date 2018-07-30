package com.boco.soap.cmnet.beans.entity;


import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderMongoVO implements Serializable{

    private static final String COLLECTION_PREFIX="PersistElement";

    private String id;
    private String orderName;

    private String busiId;

    private String busiName;
    //核查网元
    private Map<String,Ne> neList;
    //核查地市
    private List<String> busiCity;
    //核查指令字典
    private Map<String,BusiDict> busiDictList;

    //核查标准数据
    private Map<String,List> stdTable;
    //核查现网数据
    private Map<String,List> curTable;
    //核查结果
    private Map<String, List> checkResult;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;

    private String createUserId;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public List<String> getBusiCity() {
        return busiCity;
    }

    public void setBusiCity(List<String> busiCity) {
        this.busiCity = busiCity;
    }

    public Map<String, List> getStdTable() {
        return stdTable;
    }

    public void setStdTable(Map<String, List> stdTable) {
        this.stdTable = stdTable;
    }

    public Map<String, List> getCurTable() {
        return curTable;
    }

    public void setCurTable(Map<String, List> curTable) {
        this.curTable = curTable;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map<String, Ne> getNeList() {
        return neList;
    }

    public void setNeList(Map<String, Ne> neList) {
        this.neList = neList;
    }

    public Map<String, BusiDict> getBusiDictList() {
        return busiDictList;
    }

    public void setBusiDictList(Map<String, BusiDict> busiDictList) {
        this.busiDictList = busiDictList;
    }

    public String getBusiName() {
        return busiName;
    }

    public void setBusiName(String busiName) {
        this.busiName = busiName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public  String getCollectionName(){
        return COLLECTION_PREFIX+id;
    }

    public static String getCollectionName(String id){
        return COLLECTION_PREFIX+id;
    }

    public Map<String, List> getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Map<String, List> checkResult) {
        this.checkResult = checkResult;
    }
}
