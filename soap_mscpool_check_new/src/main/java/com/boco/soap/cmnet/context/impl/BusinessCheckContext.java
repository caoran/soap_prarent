package com.boco.soap.cmnet.context.impl;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.context.ICheckContext;
import com.boco.soap.cmnet.db.mongo.OrderMongo;

import java.util.List;
import java.util.Map;

public class BusinessCheckContext implements ICheckContext {
    private String taskId;
    private String subTaskId;
    private List<Ne> neList;
    private String dictIdQuery;
    private List<ICheckData> stdCheckData;
    private List<ICheckData> curCheckData;
    private Map<String,List<ICheckData>> curCheckDataMap;
    private Object extObj;
    private EnumFullPartCheck CheckType;
    private List<List<IDataCheckReturn>> checkDataResult;
    private List<IInstructionParameter> instructParas;
    private int dictPriority;
    private String dbFile;
    private int dictPriorityDel;
    private int checkType;
    private Map<String, String> delParaMap;

    private OrderMongo orderMongo;

    private BusiDict busiDict;

    private int totalCount;

    @Override
    public BusiDict getBusiDict() {
        return busiDict;
    }

    @Override
    public void setBusiDict(BusiDict busiDict) {
        this.busiDict=busiDict;
    }

    public String getTaskId()
    {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSubTaskId() {
        return this.subTaskId;
    }

    public void setSubTaskId(String subTaskId) {
        this.subTaskId = subTaskId;
    }

    public String getDictIdQuery()
    {
        return this.dictIdQuery;
    }

    public void setDictIdQuery(String dictIdQuery)
    {
        this.dictIdQuery = dictIdQuery;
    }

    public List<ICheckData> getStdCheckData() {
        return this.stdCheckData;
    }

    public void setStdCheckData(List<ICheckData> stdCheckData) {
        this.stdCheckData = stdCheckData;
    }

    @Override
    public Map<String,List<ICheckData>> getCurCheckDataMap() {
        return curCheckDataMap;
    }

    @Override
    public void setCurCheckDataMap(Map<String,List<ICheckData>> curCheckDataMap) {
        this.curCheckDataMap = curCheckDataMap;
    }

    public List<ICheckData> getCurCheckData() {
        return curCheckData;
    }

    public void setCurCheckData(List<ICheckData> curCheckData) {
        this.curCheckData = curCheckData;
    }

    public Object getExtObj() {
        return this.extObj;
    }

    public void setExtObj(Object extObj) {
        this.extObj = extObj;
    }

    public EnumFullPartCheck getCheckType() {
        return this.CheckType;
    }

    public void setCheckType(EnumFullPartCheck checkType) {
        this.CheckType = checkType;
    }

    public List<List<IDataCheckReturn>> getCheckDataResult()
    {
        return this.checkDataResult;
    }

    public void setCheckDataResult(List<List<IDataCheckReturn>> checkDataResult)
    {
        this.checkDataResult = checkDataResult;
    }

    public List<IInstructionParameter> getInstructParas()
    {
        return this.instructParas;
    }

    public void setInstructParas(List<IInstructionParameter> instructParas)
    {
        this.instructParas = instructParas;
    }

    public int getDictPriority() {
        return this.dictPriority;
    }

    public void setDictPriority(int dictPriority) {
        this.dictPriority = dictPriority;
    }

    public List<Ne> getNeList() {
        return neList;
    }

    public void setNeList(List<Ne> neList) {
        this.neList = neList;
    }

    public String getDbFile() {
        return this.dbFile;
    }

    public void setDbFile(String dbFile) {
        this.dbFile = dbFile;
    }

    public int getDictPriorityDel()
    {
        return this.dictPriorityDel;
    }

    public void setDictPriorityDel(int dictPriorityDel)
    {
        this.dictPriorityDel = dictPriorityDel;
    }

    public Map<String, String> getDelParaMap()
    {
        return this.delParaMap;
    }

    public void setDelParaMap(Map<String, String> delParaMap) {
        this.delParaMap = delParaMap;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    @Override
    public void setCheckLogicMap(int paramInt) {

    }

    @Override
    public void setIsCombine(int paramInt) {

    }

    @Override
    public OrderMongo getOrderMongo() {
        return orderMongo;
    }

    @Override
    public void setOrderMongo(OrderMongo orderMongo) {
        this.orderMongo = orderMongo;
    }

    @Override
    public int getTotalCount() {
        return this.totalCount;
    }

    @Override
    public void setTotalCount(int totalCount) {
        this.totalCount=totalCount;
    }
}
