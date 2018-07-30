package com.boco.soap.cmnet.check.pair.query.impl;

import com.boco.soap.cmnet.check.pair.query.DataQueryResult;
import com.boco.soap.cmnet.check.pair.query.EnumQueryType;
import com.boco.soap.cmnet.check.pair.query.IDataQueryEngine;
import com.boco.soap.cmnet.check.pair.query.QueryResultFactory;
import com.boco.soap.cmnet.check.result.ICheckData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccuracyCodeDataQueryEngine implements IDataQueryEngine {
    private static final Logger logger = LoggerFactory.getLogger(AccuracyCodeDataQueryEngine.class);

    private Map<String, ICheckData> engineCheckDatas = new ConcurrentHashMap();
    private Map<String, ICheckData> engineCheckCommonDatas = new ConcurrentHashMap();

    public DataQueryResult query(ICheckData sourceCheckData)
    {
        DataQueryResult result = QueryResultFactory.getInstance().createQueryResult();
        String key = sourceCheckData.getData().getKey() != null ? sourceCheckData.getData().getKey().toUpperCase() : "";
        if (this.engineCheckDatas.containsKey(key)) {
            if (logger.isDebugEnabled()) {
                logger.debug("精确查询找到数据");
            }
            ICheckData targetCheckData = (ICheckData)this.engineCheckDatas.get(key);
            List queryResultData = new ArrayList();
            queryResultData.add(targetCheckData);

            result.setQueryResult(queryResultData);
            result.setHasResult(true);
            result.setQueryType(EnumQueryType.ACCURACY);
        }
        else if (this.engineCheckCommonDatas.containsKey(key))
        {
            if (logger.isDebugEnabled()) {
                logger.debug("精确查询使用code找到数据");
            }
            ICheckData targetCheckData = (ICheckData)this.engineCheckCommonDatas.get(key);
            List queryResultData = new ArrayList();
            queryResultData.add(targetCheckData);

            result.setQueryResult(queryResultData);
            result.setHasResult(true);
            result.setQueryType(EnumQueryType.ACCURACY);
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("精确查询未找到数据");
            }
            result.setHasResult(false);
        }
        return result;
    }

    private void getEngineCheckDatas(List<ICheckData> targetDatas)
    {
        String tmpkey = "";
        for (ICheckData checkData : targetDatas) {
            tmpkey = checkData.getData().getKey() != null ? checkData.getData().getKey().toUpperCase() : "";
            this.engineCheckDatas.put(tmpkey, checkData);
        }
    }

    public void setTargetCheckDatas(List<ICheckData> checkDatas)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("开始构造精确配对查询目标数据结构");
        }
        getEngineCheckDatas(checkDatas);
    }

    private void getEngineCheckCommonDatas(List<ICheckData> targetDatas)
    {
        for (ICheckData checkData : targetDatas)
            this.engineCheckDatas.put(checkData.getData().getCode(), checkData);
    }

    public void setTargetCheckDatasCommon(List<ICheckData> checkDatas)
    {
        getEngineCheckCommonDatas(checkDatas);
    }
}
