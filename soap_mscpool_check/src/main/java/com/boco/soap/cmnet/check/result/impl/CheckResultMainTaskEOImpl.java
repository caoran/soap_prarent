package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.check.result.ICheckResultMainTaskEO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class CheckResultMainTaskEOImpl implements ICheckResultMainTaskEO {
    private static final Logger logger = LoggerFactory.getLogger(CheckResultMainTaskEOImpl.class);
    private String taskId;
    private String neNames;
    private String stdTableName;
    private Date startDateTime;
    private Date endDateTime;
    private String subTaskNum;
    private String orderId;

    public CheckResultMainTaskEOImpl(List<Ne> nes, List<String> tableNames, String orderId)
    {
        int nesSize = nes.size() - 1;

        logger.info(new StringBuilder().append("List<INeElement> nes集合大小--- ").append(nes.size()).toString());

        StringBuilder nesNames = new StringBuilder("");
        for (int j = 0; j < nesSize; j++) {
            nesNames.append(((Ne)nes.get(j)).getName()).append("|");
        }
        nesNames.append(((Ne)nes.get(nesSize)).getName());

        int stdSize = tableNames.size() - 1;
        StringBuilder stdTableNames = new StringBuilder("");
        for (int i = 0; i < stdSize; i++) {
            stdTableNames.append(tableNames.get(i)).append("|");
        }
        stdTableNames.append(tableNames.get(stdSize));

        this.stdTableName = stdTableNames.toString();
        this.neNames = nesNames.toString();
        this.startDateTime = new Date();
        this.subTaskNum = String.valueOf(nes.size());
        this.orderId=orderId;
    }

    public Date getEndDateTime()
    {
        return this.endDateTime;
    }

    public String getNeNames()
    {
        return this.neNames;
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


    public void setNeNames(String neNames)
    {
        this.neNames = neNames;
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
}
