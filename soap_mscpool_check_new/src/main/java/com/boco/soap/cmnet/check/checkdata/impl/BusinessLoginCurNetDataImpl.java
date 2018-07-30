package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.BusiDictItem;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.check.checkdata.IBusinessCurNetData;
import com.boco.soap.cmnet.check.checkdata.IBusinessCurNetServiceData;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.checkdata.IBusinessLoginCurNetData;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.impl.CheckDataImpl;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.db.mongo.OrderMongo;
import com.boco.soap.cmnet.pojo.CollectResult;
import com.boco.soap.cmnet.pojo.MatchResultElement;
import com.boco.soap.cmnet.util.SpringContextHolder;
import com.boco.soap.cmnet.check.result.MatchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BusinessLoginCurNetDataImpl implements IBusinessLoginCurNetData {
    private Map<String, List<ICheckData>> checkCurNetData;
    private final Logger logger = LoggerFactory.getLogger(BusinessLoginCurNetDataImpl.class);

    public BusinessLoginCurNetDataImpl()
    {
        this.checkCurNetData = new HashMap();
    }

    @Override
    public Map<String, List<ICheckData>> createCurNetCheckDatas(BusinessSubTaskContext subTaskContext, Map<String, MatchResult> matchResult, BusiDict busiDict)
    {
        getCheckDataMap(busiDict, subTaskContext.getOrderMongo());
        return this.checkCurNetData;
    }



    private Map<String, List<ICheckData>> getCheckDataMap( BusiDict busiDict, OrderMongo orderMongo)
    {
        List<Ne> neList=new ArrayList<>(orderMongo.getNeList().values());
        for (Ne  ne: neList) {
            String neName = ne.getName();
            String logTableName = busiDict.getCurTable();
            String resultKey = neName + "||" + logTableName;
            if (!this.checkCurNetData.containsKey(resultKey))
            {
                List checkDataReturnList = createCurNetCheckData(ne, busiDict,orderMongo);

                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("现网表ICheckData 网元名称---> [{}],现网表名称---> [{}],共生成 --->[{}]", new Object[] {neName, logTableName, Integer.valueOf(checkDataReturnList.size()) });
                }
                this.checkCurNetData.put(resultKey, checkDataReturnList);
            }
        }
        return this.checkCurNetData;
    }

    private List<ICheckData> createCurNetCheckData(Ne ne, BusiDict busiDict, OrderMongo orderMongo)
    {
        IBusinessCurNetData businessCurNetData = (IBusinessCurNetData) SpringContextHolder.getBean("businessCurNetDataImpl");

        List<Map<String, String>> curNetDataList = businessCurNetData.getCurNetDataInfo(ne, busiDict.getCurTable());

        List instructDictParas = busiDict.getBusiDictItems();

        CollectResult collectResult = new CollectResult();
        collectResult.setResults(curNetDataList);

        IBusinessCurNetServiceData curNetServiceData = (IBusinessCurNetServiceData)SpringContextHolder.getBean("businessCurNetServiceDataImpl");
        List<IData> dataList = curNetServiceData.getCurDataList(collectResult, instructDictParas);

        List checkDataReturnList = new ArrayList();
        for (IData iData : dataList) {
            ICheckData checkData = new CheckDataImpl(iData, EnumCheckDataType.TARGET);

            checkDataReturnList.add(checkData);
        }
        return checkDataReturnList;
    }
}
