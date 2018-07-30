package com.boco.soap.cmnet.check.pair.query;

public class QueryResultFactory {
    private static final QueryResultFactory instance = new QueryResultFactory();

    public static QueryResultFactory getInstance()
    {
        return instance;
    }

    public DataQueryResult createQueryResult() {
        return new DataQueryResult();
    }
}
