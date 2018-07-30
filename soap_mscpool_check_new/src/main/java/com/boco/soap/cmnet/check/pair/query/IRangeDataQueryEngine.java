package com.boco.soap.cmnet.check.pair.query;

import com.boco.soap.cmnet.check.pair.query.DataQueryResult;
import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.List;
import java.util.Map;

public interface IRangeDataQueryEngine {
    public abstract Map<String, List<DataQueryResult>> query();

    public abstract void setTargetCheckDatas(List<ICheckData> paramList);

    public abstract void setTargetCheckDatasCommon(List<ICheckData> paramList);
}
