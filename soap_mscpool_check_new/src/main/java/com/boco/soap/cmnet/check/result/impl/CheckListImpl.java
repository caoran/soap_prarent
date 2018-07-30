package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckListImpl implements IData {
    private String checkitemid;
    private String checkitem;
    private String collectkey;
    private String logfile;
    private IBusinessInstruction instruction;
    private List<IDataItem> items;
    private Map<String, IDataItem> checkDataItems;
    private String errormsg = null;
    private Map<String, String> StandData;

    public CheckListImpl(List<IDataItem> itemsList, Map<String, String> mapData, IBusinessInstruction instruction)
    {
        this.items = itemsList;
        this.StandData = mapData;
        this.instruction = instruction;
        String checkdesc = mapData.get("CHECKDESC") != null ? ((String)mapData.get("CHECKDESC")).toString().trim() : "";
        String paraid = mapData.get("PARAID") != null ? ((String)mapData.get("PARAID")).toString().trim() : "";
        this.checkitemid = paraid;
        this.checkitem = checkdesc;
        if (itemsList.size() > 0)
            init(itemsList);
    }

    private void init(List<IDataItem> itemsList)
    {
        for (IDataItem item : itemsList) {
            if (this.checkDataItems == null) {
                this.checkDataItems = new HashMap();
            }
            this.checkDataItems.put(item.getParamName(), item);
        }
    }

    public IBusinessInstruction getInstruction()
    {
        return this.instruction;
    }

    public void setInstruction(IBusinessInstruction instruction)
    {
        this.instruction = instruction;
    }

    public Map<String, String> getStandData()
    {
        return this.StandData;
    }

    public void setStandData(Map<String, String> stdData)
    {
        this.StandData = stdData;
    }

    public String getCheckitemid() {
        return this.checkitemid;
    }

    public void setCheckitemid(String checkitemid) {
        this.checkitemid = checkitemid;
    }

    public String getCheckitem() {
        return this.checkitem;
    }

    public String getCollectkey() {
        return this.collectkey;
    }

    public void setCollectkey(String collectkey) {
        this.collectkey = collectkey;
    }

    public void setCheckitem(String checkitem) {
        this.checkitem = checkitem;
    }

    public void setKey(String key)
    {
    }

    public String getKey() {
        return null;
    }

    public String getCode()
    {
        return null;
    }

    public String getQuery()
    {
        return null;
    }

    public String getIpQuery()
    {
        return null;
    }

    public Map<String, IDataItem> getCheck()
    {
        return this.checkDataItems;
    }

    public IDataItem getItemByName(String name)
    {
        return null;
    }

    public IDataItem getItemByIndex(int index)
    {
        return null;
    }

    public List<IDataItem> getItems()
    {
        return this.items;
    }

    public void setItems(List<IDataItem> items)
    {
        this.items = items;
    }

    public String getValueByName(String name, int type)
    {
        String result = null;

        if (this.checkDataItems != null)
        {
            IDataItem item = (IDataItem)this.checkDataItems.get(name);
            if (null != item) {
                result = getValue(item, type);
            }
            else {
                result = this.errormsg;
            }
        }
        else
        {
            result = this.errormsg;
        }
        return result;
    }

    private String getValue(IDataItem item, int type) {
        return item.getEnglishValue();
    }

    public String getValueByIndex(int index, int type)
    {
        return null;
    }

    public long getRangeCheckedBeg()
    {
        return 0L;
    }

    public void setRangeCheckedBeg(long rangeCheckBeg)
    {
    }

    public long getRangeCheckedEnd()
    {
        return 0L;
    }

    public void setRangeCheckedEnd(long rangeCheckEnd)
    {
    }

    public String getLogfile()
    {
        return this.logfile != null ? this.logfile : "报文地址错误！";
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }


}
