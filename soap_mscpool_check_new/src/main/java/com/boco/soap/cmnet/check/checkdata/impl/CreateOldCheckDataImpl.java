package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.BusiDictItem;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.checkdata.ICreateOldCheckData;
import com.boco.soap.cmnet.check.checkdata.ISourceDatasGenerator;
import com.boco.soap.cmnet.check.result.IBusinessConstraint;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.MatchResult;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.pojo.MatchResultElement;

import java.util.*;

public class CreateOldCheckDataImpl implements ICreateOldCheckData
{
    private Map<String, List<ICheckData>> checkData = new HashMap();
    ISourceDatasGenerator dataGenerator = new SourceDatasGeneratorImpl();

    @Override
    public Map<String, List<ICheckData>> createOldCheckData(BusinessSubTaskContext subTaskContext, Map<String, MatchResult> map, BusiDict busiDict, String dbFile)
    {
        /*for (String key : map.keySet()) {
            MatchResult matchResult = (MatchResult)map.get(key);
            List matchResultElements = matchResult.getMatchResultElements();
            for (Iterator localIterator2 = matchResultElements.iterator(); localIterator2.hasNext(); ) {
                MatchResultElement element = (MatchResultElement)localIterator2.next();
                IBusinessConstraint constraint = element.getConstraint();
                List<IBusinessInstruction> instructions = constraint.getInstructions();
                for (IBusinessInstruction instruction : instructions)
                    createSourceData(subTaskContext, instruction, element, ne, dbFile);
            }
        }*/
        /*for (Ne ne:subTaskContext.getOrderMongo().getNeList().values()) {
            createSourceData(subTaskContext, ne, busiDict, dbFile);
        }*/
        Set<String> neSet=subTaskContext.getOrderMongo().getNeList().keySet();
        String[] neNameArr=new String[neSet.size()];
        int i=0;
        for (String neName:neSet){
            neNameArr[i]=neName;
            i++;
        }
        createSourceData(subTaskContext, neNameArr, busiDict, dbFile);
        return this.checkData;
    }

    private void createSourceData(BusinessSubTaskContext subTaskContext,String[] neNameArr, BusiDict busiDict, String dbFile) {
        String logTableName = busiDict.getCurTable();
        String resultKey =  logTableName;

        Map datas = this.dataGenerator.createSourceCheckDatas(subTaskContext, neNameArr, busiDict, resultKey, null);

        List checkDatas = new ArrayList(datas.values());

        if (this.checkData.containsKey(resultKey))
        {
            List checkDataList = (List)this.checkData.get(resultKey);
            checkDataList.addAll(checkDatas);
        }
        else
        {
            this.checkData.put(resultKey, checkDatas);
        }
    }
}
