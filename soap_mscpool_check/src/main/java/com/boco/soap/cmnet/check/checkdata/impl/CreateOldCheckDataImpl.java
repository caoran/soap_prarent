package com.boco.soap.cmnet.check.checkdata.impl;

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
    public Map<String, List<ICheckData>> createOldCheckData(BusinessSubTaskContext subTaskContext, Map<String, MatchResult> map, Ne ne, String dbFile)
    {
        for (String key : map.keySet()) {
            MatchResult matchResult = (MatchResult)map.get(key);
            List matchResultElements = matchResult.getMatchResultElements();
            for (Iterator localIterator2 = matchResultElements.iterator(); localIterator2.hasNext(); ) {
                MatchResultElement element = (MatchResultElement)localIterator2.next();
                IBusinessConstraint constraint = element.getConstraint();
                List<IBusinessInstruction> instructions = constraint.getInstructions();
                for (IBusinessInstruction instruction : instructions)
                    createSourceData(subTaskContext, instruction, element, ne, dbFile);
            }
        }

        return this.checkData;
    }

    private void createSourceData(BusinessSubTaskContext subTaskContext, IBusinessInstruction instruction, MatchResultElement element, Ne ne, String dbFile) {
        int dictId = instruction.getDictId();
        String logTableName = instruction.getLogTableName();
        String resultKey = dictId + "-" + logTableName;

        Map datas = this.dataGenerator.createSourceCheckDatas(subTaskContext, element, instruction, resultKey, null);

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
