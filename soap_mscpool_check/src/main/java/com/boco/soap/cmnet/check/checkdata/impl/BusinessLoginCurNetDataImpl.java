package com.boco.soap.cmnet.check.checkdata.impl;

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
    public Map<String, List<ICheckData>> createCurNetCheckDatas(BusinessSubTaskContext subTaskContext, String dbFile, Map<String, MatchResult> matchResult, Ne ne)
    {
        for (Iterator localIterator1 = matchResult.keySet().iterator(); localIterator1.hasNext(); ) {
           String solutionId = (String)localIterator1.next();
            MatchResult matchResultObj = (MatchResult)matchResult.get(solutionId);
            for (MatchResultElement matchElement : matchResultObj.getMatchResultElements())
                getCheckDataMap(matchElement, ne, solutionId, dbFile);
        }
        return this.checkCurNetData;
    }

    private Map<String, List<ICheckData>> getCheckDataMap(MatchResultElement matchElement, Ne ne, String solutionId, String dbFile)
    {
        for (IBusinessInstruction instruction : matchElement.getConstraint()
                .getInstructions()) {
            int dictId = instruction.getDictId();
            String logTableName = instruction.getLogTableName();
            String resultKey = dictId + "-" + logTableName;
            if (!this.checkCurNetData.containsKey(resultKey))
            {
                List checkDataReturnList = createCurNetCheckData(dbFile, ne, solutionId, instruction, dbFile);

                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("现网表ICheckData 指令字典---> [{}],现网表名称---> [{}],共生成 --->[{}]", new Object[] { Integer.valueOf(dictId), logTableName, Integer.valueOf(checkDataReturnList.size()) });
                }
                this.checkCurNetData.put(resultKey, checkDataReturnList);
            }
        }
        return this.checkCurNetData;
    }

    private List<ICheckData> createCurNetCheckData(String dbFile, Ne ne, String solutionId, IBusinessInstruction instruction, String url)
    {
        IBusinessCurNetData businessCurNetData = (IBusinessCurNetData) SpringContextHolder.getBean("businessCurNetDataImpl");

        List<Map<String, String>> curNetDataList = businessCurNetData
                .getCurNetDataInfo(dbFile, ne, solutionId, instruction
                        .getLogTableName());

        List instructDictParas = businessCurNetData
                .getInstructDictList(instruction, url);

        CollectResult collectResult = new CollectResult();
        collectResult.setResults(curNetDataList);

        IBusinessCurNetServiceData curNetServiceData = (IBusinessCurNetServiceData)SpringContextHolder.getBean("businessCurNetServiceDataImpl");
        List<IData> dataList = curNetServiceData.getCurDataList(collectResult, instruction.getParams());

        List checkDataReturnList = new ArrayList();
        for (IData iData : dataList) {
            ICheckData checkData = new CheckDataImpl(iData, EnumCheckDataType.TARGET);

            checkDataReturnList.add(checkData);
        }
        return checkDataReturnList;
    }
}
