package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.MatchResult;
import com.boco.soap.cmnet.context.impl.BusinessSubTaskContext;

import java.util.List;
import java.util.Map;

public interface IBusinessLoginCurNetData {
     Map<String, List<ICheckData>> createCurNetCheckDatas(BusinessSubTaskContext paramBusinessSubTaskContext, Map<String, MatchResult> paramMap, BusiDict paramINeElement);
}
