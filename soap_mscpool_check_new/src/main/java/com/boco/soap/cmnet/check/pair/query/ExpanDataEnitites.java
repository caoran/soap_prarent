package com.boco.soap.cmnet.check.pair.query;

import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.List;

public class ExpanDataEnitites{

    private ICheckData data;

    private List<ICheckData> listdata;


    public ICheckData getData() {
        return data;
    }

    public void setData(ICheckData data) {
        this.data = data;
    }

    public List<ICheckData> getListdata() {
        return listdata;
    }

    public void setListdata(List<ICheckData> listData) {
        this.listdata = listData;
    }
}
