package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.check.checkdata.*;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.MatchResult;
import com.boco.soap.cmnet.check.result.impl.ParameCheckDataImpl;
import com.boco.soap.cmnet.common.util.cache.ICacheManager;
import com.boco.soap.cmnet.common.util.cache.impl.CacheImpl;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.pojo.CollectResult;
import com.boco.soap.cmnet.pojo.INeElement;
import com.boco.soap.cmnet.pojo.MatchResultElement;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParaCurDataGenerateImp implements IBusinessLoginCurNetData
{
    private Map<String, List<ICheckData>> checkCurNetData = new HashMap();
    private BusinessSubTaskContext subTaskContext;
    private final Logger logger = LoggerFactory.getLogger(ParaCurDataGenerateImp.class);

    public Map<String, List<ICheckData>> createCurNetCheckDatas(BusinessSubTaskContext subTaskContext, String dbFile, Map<String, MatchResult> matchResult, Ne ne)
    {
        this.subTaskContext = subTaskContext;

        createCache(subTaskContext.getNe().getName());
        this.logger.info("参数核查，网元{}现网采集开始！", ne.getName());
        for (String solutionId : matchResult.keySet()) {
            MatchResult matchResultObj = (MatchResult)matchResult.get(solutionId);
            List matchs = matchResultObj.getMatchResultElements();
            if ((matchs != null) && (matchs.size() > 0))
            {
                for (MatchResultElement matchElement : matchResultObj
                        .getMatchResultElements())
                    getCheckDataMap(matchElement, ne, solutionId, dbFile);
            }
            else {
                this.logger.error("网元：{}，方案ID：{}，配置方案信息有误！", ne.getName(), solutionId);
            }
        }

        return this.checkCurNetData;
    }

    private Map<String, List<ICheckData>> getCheckDataMap(MatchResultElement matchElement, Ne ne, String solutionId, String dbFile)
    {
        try
        {
            for (IBusinessInstruction instruction : matchElement.getConstraint()
                    .getInstructions()) {
                int dictId = instruction.getDictId();
                String collectDictId = instruction.getLogTableName();
                String resultKey = dictId + "-" + collectDictId;
                this.logger.info("指令字典信息{}开始采集", resultKey);
                if (!this.checkCurNetData.containsKey(resultKey))
                {
                    List checkDataReturnList = createCurNetCheckData(ne, resultKey, "1", dbFile, instruction);

                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("现网表ICheckData 指令字典---> [{}],现网表名称---> [{}],共生成 --->[{}]", new Object[] { Integer.valueOf(dictId), collectDictId, Integer.valueOf(checkDataReturnList.size()) });
                    }
                    this.checkCurNetData.put(resultKey, checkDataReturnList);
                }
            }
        } catch (Exception e) {
            this.logger.error("", e);
        }
        return this.checkCurNetData;
    }

    private List<ICheckData> createCurNetCheckData(Ne ne, String resultKey, String collecType, String dbFile, IBusinessInstruction instruction) throws Exception {
        String checkTaskId = this.subTaskContext.getTaskId();

        IParaCollectService iCollectDataService = (IParaCollectService)SpringContextHolder.getBean("parameCollectServiceImpl");
        List<CollectResult> collectResult = iCollectDataService.collect(ne, resultKey, checkTaskId, collecType, dbFile);
        List checkDataReturnList = new ArrayList();
        IBusinessCurNetData businessCurNetData = (IBusinessCurNetData)SpringContextHolder.getBean("businessCurNetDataImpl");
        List instructDictParas = businessCurNetData.getInstructDictList(instruction, dbFile);

        IBusinessCurNetServiceData curNetServiceData = (IBusinessCurNetServiceData)SpringContextHolder.getBean("parameCurNetServiceDataImpl");
        boolean sencondCollectFlag = false;
        if ((collectResult != null) && (collectResult.size() > 1))
            sencondCollectFlag = ((CollectResult)collectResult.get(0)).isSecond();
        if (sencondCollectFlag) {
            List dataList = curNetServiceData.getParCurDataList(collectResult, instructDictParas);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("网元{}，采集信息{}，采集了{}组信息", new Object[] { ne.getName(), resultKey, Integer.valueOf(dataList.size()) });
            }

            ICheckData checkData = new ParameCheckDataImpl(dataList, EnumCheckDataType.TARGET);
            checkDataReturnList.add(checkData);
        } else {
            for (CollectResult colectObj : collectResult) {
                List collectResultNew = new ArrayList();
                collectResultNew.add(colectObj);
                List dataList = curNetServiceData.getParCurDataList(collectResultNew, instructDictParas);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("网元{}，采集信息{}，采集了{}组信息", new Object[] { ne.getName(), resultKey, Integer.valueOf(dataList.size()) });
                }

                ICheckData checkData = new ParameCheckDataImpl(dataList, EnumCheckDataType.TARGET);
                checkDataReturnList.add(checkData);
            }

        }

        return checkDataReturnList;
    }

    private void createCache(String nename)
    {
        if (this.logger.isInfoEnabled()) {
            this.logger.info("创建网元采集指令组缓存 :" + nename + " 的共享缓存");
        }
        ICacheManager cacheManager = (ICacheManager) SpringContextHolder.getBean("data_cache");
        cacheManager.setCache(nename, new CacheImpl());
    }
}
