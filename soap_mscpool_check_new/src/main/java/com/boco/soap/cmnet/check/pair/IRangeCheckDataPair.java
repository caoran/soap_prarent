package com.boco.soap.cmnet.check.pair;

import com.boco.soap.cmnet.check.pair.query.DataQueryResult;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

import java.util.List;
import java.util.Map;

public interface IRangeCheckDataPair {
    Map<String, List<DataQueryResult>> dataPairRange(List<ICheckData> paramList1, List<ICheckData> paramList2, DataPairConfig paramDataPairConfig, BusinessCheckContext paramBusinessCheckContext);
}
