package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.entity.BusiDictItem;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.pojo.CollectResult;
import com.boco.soap.cmnet.pojo.InstructionItem;

import java.util.List;

public interface IBusinessCurNetServiceData {
    public abstract List<IData> getCurDataList(CollectResult paramCollectResult, List<BusiDictItem> paramList);

    public abstract List<IData> getParCurDataList(List<CollectResult> paramList, List<InstructionItem> paramList1);
}
