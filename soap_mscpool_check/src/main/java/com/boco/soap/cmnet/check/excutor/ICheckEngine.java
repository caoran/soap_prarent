package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.check.pair.operate.ICheckOperate;
import com.boco.soap.cmnet.check.result.CheckItemResult;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;

public interface ICheckEngine {
    public abstract String[] getCheckNames();

    public abstract int[] getCheckIndexs();

    public abstract ICheckOperate[] getCheckOperates();

    public abstract boolean check(ICheckData paramICheckData1, ICheckData paramICheckData2);

    public abstract CheckItemResult checkparames(IData paramIData1, IData paramIData2);
}
