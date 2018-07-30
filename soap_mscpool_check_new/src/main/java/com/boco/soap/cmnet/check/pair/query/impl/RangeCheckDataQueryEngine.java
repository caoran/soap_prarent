package com.boco.soap.cmnet.check.pair.query.impl;

import com.boco.soap.cmnet.check.pair.query.DataQueryResult;
import com.boco.soap.cmnet.check.pair.query.IRangeDataQueryEngine;
import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.List;
import java.util.Map;

public class RangeCheckDataQueryEngine implements IRangeDataQueryEngine {

    @Override
    public Map<String, List<DataQueryResult>> query() {
        return null;
    }

    @Override
    public void setTargetCheckDatas(List<ICheckData> paramList) {

    }

    @Override
    public void setTargetCheckDatasCommon(List<ICheckData> paramList) {

    }
}
