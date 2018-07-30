package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.enums.EnumCombineType;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.impl.UnionDataImpl;
import com.boco.soap.cmnet.check.checkdata.impl.UnionStdRangeCheckDataImpl;
import com.boco.soap.cmnet.check.result.ICheckData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SourceDataUnionFactory {
    private static final Logger logger = LoggerFactory.getLogger(SourceDataUnionFactory.class);

    private static SourceDataUnionFactory instance = new SourceDataUnionFactory();

    public static SourceDataUnionFactory getInstance()
    {
        return instance;
    }

    public IUnion createCheckData(EnumFullPartCheck checktype, EnumCombineType combineType, List<ICheckData> curCheckDatas) {
        if (logger.isDebugEnabled()) {
            logger.debug("核查类型：{}", checktype);
        }
        IUnion unionRes = null;
        if ((checktype.equals(EnumFullPartCheck.RANGECHECK_FULLCHECK)) || (checktype.equals(EnumFullPartCheck.RANGECHECK_PARTCHECK)))
            unionRes = new UnionStdRangeCheckDataImpl();
        else if (curCheckDatas != null)
        {
            unionRes = new UnionDataImpl(curCheckDatas);
        }
        else {
            unionRes = CreateUnionTypeFactory.getInstance().getUnionType(combineType);
        }

        return unionRes;
    }
}
