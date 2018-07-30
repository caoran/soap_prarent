package com.boco.soap.cmnet.check.pair.query;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.pair.DataPairConfig;
import com.boco.soap.cmnet.check.pair.EnumPairType;
import com.boco.soap.cmnet.check.pair.query.impl.AccuracyCodeDataQueryEngine;
import com.boco.soap.cmnet.check.pair.query.impl.FuzzyCodeQueryEngine;
import com.boco.soap.cmnet.check.pair.query.impl.RangeCheckDataQueryEngine;

public class DataQueryFactory {
    private static final DataQueryFactory instance = new DataQueryFactory();

    public static DataQueryFactory getInstance()
    {
        return instance;
    }

    public IDataQueryEngine getDataQueryEngine(DataPairConfig config, EnumFullPartCheck checkType) {
        if (EnumPairType.CODE_PAIR.equals(config.getPairType()))
        {
            if ((config.isAllowAbbreviated()) && (config.isdefaultcombine()))
            {
                return new FuzzyCodeQueryEngine();
            }
            if ((config.isAllowAbbreviated()) && (!config.isdefaultcombine()))
            {
                //return new E214CodeDataQueryEngine();
            }

            return new AccuracyCodeDataQueryEngine();
        }

        return null;
    }

    public IRangeDataQueryEngine getRangeDataQueryEngine(DataPairConfig config, EnumFullPartCheck checkType)
    {
        if (EnumPairType.CODE_PAIR.equals(config.getPairType())) {
            return new RangeCheckDataQueryEngine();
        }
        return null;
    }
}
