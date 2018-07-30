package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.check.result.ICheckResultMainTaskEO;
import com.boco.soap.cmnet.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class CheckResultMainTaskEOImpl implements ICheckResultMainTaskEO {
    private static final Logger logger = LoggerFactory.getLogger(CheckResultMainTaskEOImpl.class);
    private String taskId;
    private String dictNames;
    private String stdTableName;
    private Date startDateTime;
    private Date endDateTime;
    private String subTaskNum;
    private String orderId;

    public CheckResultMainTaskEOImpl(List<BusiDict> busiDicts, List<String> tableNames, String orderId)
    {
        int dictSize = busiDicts.size() - 1;

        logger.info(new StringBuilder().append("List<BusiDict> nes集合大小--- ").append(busiDicts.size()).toString());

        StringBuilder dictNames = new StringBuilder("");
        for (int j = 0; j < dictSize; j++) {
            dictNames.append(busiDicts.get(j).getName()).append("|");
        }
        dictNames.append(busiDicts.get(dictSize).getName());

        int stdSize = tableNames.size() - 1;
        StringBuilder stdTableNames = new StringBuilder("");
        for (int i = 0; i <=stdSize; i++) {
            stdTableNames.append(tableNames.get(i)).append("|");
        }
        if (StringUtils.isNoneEmpty(stdTableNames)){
            stdTableNames=stdTableNames.delete(0,stdTableNames.length()-1);
        }
        this.stdTableName = stdTableNames.toString();
        this.dictNames = dictNames.toString();
        this.startDateTime = new Date();
        this.subTaskNum = String.valueOf(busiDicts.size());
        this.orderId=orderId;
    }

    public Date getEndDateTime()
    {
        return this.endDateTime;
    }


    public Date getStartDateTime()
    {
        return this.startDateTime;
    }

    public String getStdTableName()
    {
        return this.stdTableName;
    }

    public String getTaskId()
    {
        return this.taskId;
    }

    public void setEndDateTime(Date endDateTime)
    {
        this.endDateTime = endDateTime;
    }

    public void setStartDateTime(Date startDateTime)
    {
        this.startDateTime = startDateTime;
    }

    public void setStdTableName(String stdTableName)
    {
        this.stdTableName = stdTableName;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getSubTaskNum()
    {
        return this.subTaskNum;
    }

    public void setSubTaskNum(String subTaskNum) {
        this.subTaskNum = subTaskNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDictNames() {
        return dictNames;
    }

    public void setDictNames(String dictNames) {
        this.dictNames = dictNames;
    }
}
