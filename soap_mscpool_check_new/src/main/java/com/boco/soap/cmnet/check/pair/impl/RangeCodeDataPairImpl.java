package com.boco.soap.cmnet.check.pair.impl;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.pair.DataPairConfig;
import com.boco.soap.cmnet.check.pair.IRangeCheckDataPair;
import com.boco.soap.cmnet.check.pair.code.CheckDataComparator;
import com.boco.soap.cmnet.check.pair.query.DataQueryFactory;
import com.boco.soap.cmnet.check.pair.query.DataQueryResult;
import com.boco.soap.cmnet.check.pair.query.IRangeDataQueryEngine;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RangeCodeDataPairImpl implements IRangeCheckDataPair {

    @Override
    public Map<String, List<DataQueryResult>> dataPairRange(List<ICheckData> sourceCheckDatas, List<ICheckData> targetCheckDatas, DataPairConfig dataPairConfig, BusinessCheckContext paramBusinessCheckContext) {
        Collections.sort(sourceCheckDatas, new CheckDataComparator());
        Collections.sort(targetCheckDatas, new CheckDataComparator());
        IRangeDataQueryEngine queryEngine = DataQueryFactory.getInstance().getRangeDataQueryEngine(dataPairConfig, EnumFullPartCheck.RANGECHECK_FULLCHECK);
        queryEngine.setTargetCheckDatas(targetCheckDatas);
        queryEngine.setTargetCheckDatasCommon(sourceCheckDatas);
        return queryEngine.query();
    }
}
