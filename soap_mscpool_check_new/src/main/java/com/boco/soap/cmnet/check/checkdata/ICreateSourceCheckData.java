package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.MatchResult;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;

import java.util.List;
import java.util.Map;

public interface ICreateSourceCheckData {
    public abstract Map<String, List<ICheckData>> createSourceCheckData(BusinessSubTaskContext paramBusinessSubTaskContext, Map<String, MatchResult> paramMap, Map<String, List<ICheckData>> paramMap1);
}
