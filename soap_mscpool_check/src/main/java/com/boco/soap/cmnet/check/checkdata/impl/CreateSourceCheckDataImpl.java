package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.CreateCheckDataFactory;
import com.boco.soap.cmnet.check.checkdata.ICreateOldCheckData;
import com.boco.soap.cmnet.check.checkdata.ICreateSourceCheckData;
import com.boco.soap.cmnet.check.checkdata.IMergeCheckData;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.MatchResult;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;

import java.util.List;
import java.util.Map;

public class CreateSourceCheckDataImpl implements ICreateSourceCheckData {

    @Override
    public Map<String, List<ICheckData>> createSourceCheckData(BusinessSubTaskContext subTaskContext, Map<String, MatchResult> map, Map<String, List<ICheckData>> curCheckDataMap) {

        Ne ne = subTaskContext.getNe();
        int isCombine = subTaskContext.getIsCombine();
        EnumFullPartCheck checkType = subTaskContext.getCheckType();

        ICreateOldCheckData createOldCheckDataImpl = CreateCheckDataFactory.getInstance().createCheckData(subTaskContext.getCheckType());
        Map checkData = createOldCheckDataImpl.createOldCheckData(subTaskContext, map, ne, "");

        if (subTaskContext.getCheckType().equals(EnumFullPartCheck.PARAMECHECK))
        {
            return checkData;
        }
        IMergeCheckData mergeCheckDataImpl = new MergeCheckDataImpl();
        checkType = subTaskContext.getCheckType();
        Map mergeCheckDatas = mergeCheckDataImpl.mergeCheckData(checkData, isCombine, checkType, curCheckDataMap);
        return mergeCheckDatas;
    }
}
