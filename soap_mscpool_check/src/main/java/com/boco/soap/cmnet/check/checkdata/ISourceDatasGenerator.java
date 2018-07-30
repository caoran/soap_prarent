package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;
import com.boco.soap.cmnet.pojo.MatchResultElement;

import java.util.Map;

public interface ISourceDatasGenerator {
    Map<String, ICheckData> createSourceCheckDatas(BusinessSubTaskContext subTaskContext, MatchResultElement element, IBusinessInstruction instruction, String resultKey, Object obj);
}
