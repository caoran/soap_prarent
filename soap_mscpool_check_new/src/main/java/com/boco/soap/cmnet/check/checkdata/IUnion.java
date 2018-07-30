package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.List;

public interface IUnion {
    List<ICheckData> unionData(List<ICheckData> paramList);
}
