package com.boco.soap.cmnet.check.pair.query;

import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.List;

public interface IDataQueryEngine{
        public abstract DataQueryResult query(ICheckData paramICheckData);

        public abstract void setTargetCheckDatas(List<ICheckData> paramList);

        public abstract void setTargetCheckDatasCommon(List<ICheckData> paramList);
}
