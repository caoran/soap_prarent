package com.boco.soap.cmnet.check.pair;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.pair.impl.CodeDataPairImpl;
import com.boco.soap.cmnet.check.pair.impl.RangeCodeDataPairImpl;

public class FactoryCheckDataPair {
    private static final FactoryCheckDataPair instance = new FactoryCheckDataPair();

    public static FactoryCheckDataPair getInstance()
    {
        return instance;
    }

    public ICheckDataPair createCheckPairResult(EnumFullPartCheck checkType) {
        return new CodeDataPairImpl();
    }

    public IRangeCheckDataPair createRangeCheckPairResult(EnumFullPartCheck checkType)
    {
        return new RangeCodeDataPairImpl();
    }
}
