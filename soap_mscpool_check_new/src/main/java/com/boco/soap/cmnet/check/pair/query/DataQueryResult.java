package com.boco.soap.cmnet.check.pair.query;

import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.ArrayList;
import java.util.List;

public class DataQueryResult {
    private boolean hasResult;
    private EnumQueryType queryType;
    private List<ICheckData> queryResult;
    private List<ICheckData> ResultStdData;
    private ICheckData originalCurData;

    public boolean isHasResult()
    {
        return this.hasResult;
    }

    public void setHasResult(boolean hasResult) {
        this.hasResult = hasResult;
    }

    public EnumQueryType getQueryType() {
        return this.queryType;
    }

    public void setQueryType(EnumQueryType queryType) {
        this.queryType = queryType;
    }

    public List<ICheckData> getQueryResult() {
        return this.queryResult;
    }

    public void setQueryResult(List<ICheckData> queryResult) {
        this.queryResult = queryResult;
    }

    public List<ICheckData> getResultStdData() {
        return this.ResultStdData;
    }

    public void setResult(List<ICheckData> resultStdData) {
        this.ResultStdData = resultStdData;
    }

    public void setStdDataResult(ICheckData checkdata) {
        if (this.ResultStdData == null)
        {
            this.ResultStdData = new ArrayList();
            this.ResultStdData.add(checkdata);
        }
        else
        {
            this.ResultStdData.add(checkdata);
        }
    }

    public void setQueryResult(ICheckData checkdata) {
        if (this.queryResult == null)
        {
            this.queryResult = new ArrayList();
            this.queryResult.add(checkdata);
        }
        else
        {
            this.queryResult.add(checkdata);
        }
    }

    public ICheckData getOriginalCurData() {
        return this.originalCurData;
    }

    public void setOriginalCurData(ICheckData originalCurData) {
        this.originalCurData = originalCurData;
    }
}
