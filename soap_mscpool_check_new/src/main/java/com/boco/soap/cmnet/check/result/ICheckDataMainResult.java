package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;

import java.util.List;
import java.util.Map;

public interface ICheckDataMainResult {
    BusinessSubTaskContext getCheckDataResult(Map<String, List<ICheckData>> paramMap1, Map<String, List<ICheckData>> paramMap2, BusinessSubTaskContext paramBusinessSubTaskContext, Object paramObject);
}
