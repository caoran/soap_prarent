package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.enums.EnumCombineType;
import com.boco.soap.cmnet.check.checkdata.impl.UnionDataImpl;

public class CreateUnionTypeFactory {
    private static CreateUnionTypeFactory ourInstance = new CreateUnionTypeFactory();

    public static CreateUnionTypeFactory getInstance() {
        return ourInstance;
    }

    public IUnion getUnionType(EnumCombineType combineType) {
        IUnion unionImpl = null;

        if (combineType.equals(EnumCombineType.CombineDefault)) {
            unionImpl = new UnionDataImpl();
        } /*else if (combineType.equals(EnumCombineType.CombineE))
        {
            unionImpl = new E214_E_UnionImpl();
        } else if (combineType.equals(EnumCombineType.CombineStar))
        {
            unionImpl = new E214_star_UnionImpl();
        }*/ else unionImpl = new UnionDataImpl();

        return unionImpl;
    }
}
