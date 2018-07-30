package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataImpl implements IData{
    public static final int ENGLISH_VALUE = 0;
    public static final int CHINESE_VALUE = 1;
    private String key;
    private String code = "";

    private String query = "";

    private String ipquery = "";
    private Map<String, IDataItem> checkDataItems;
    private List<IDataItem> itemsList;
    private Map<String, Integer> itemsIndex;
    private IBusinessInstruction instruction;
    private Map<String, String> mapData;
    private long rangeCheckedBeg;
    private long rangeCheckedEnd;

    public DataImpl(List<IDataItem> itemsList)
    {
        this.itemsList = itemsList;
        if (itemsList.size() > 0)
            init(itemsList);
    }

    public DataImpl(List<IDataItem> itemsList, Map<String, String> mapData)
    {
        this(itemsList);
        this.mapData = mapData;
    }

    public String getCode()
    {
        return this.code;
    }

    public String getIpQuery()
    {
        return this.ipquery;
    }

    public IDataItem getItemByIndex(int index)
    {
        return (IDataItem)this.itemsList.get(index);
    }

    public IDataItem getItemByName(String name)
    {
        int index = ((Integer)this.itemsIndex.get(name)).intValue();
        return (IDataItem)this.itemsList.get(index);
    }

    public String getKey()
    {
        return this.key;
    }

    public String getQuery()
    {
        return this.query;
    }

    public List<IDataItem> getItems()
    {
        return this.itemsList;
    }

    public void setItems(List<IDataItem> items)
    {
        this.itemsList = items;

        for (int i = 0; i < items.size(); i++) {
            IDataItem item = (IDataItem)items.get(i);
            if (this.itemsIndex == null) {
                this.itemsIndex = new HashMap();
            }

            if (this.checkDataItems == null) {
                this.checkDataItems = new HashMap();
            }

            if (EnumFieldUsage.CHECK_FIELD.equals(item.getParam().getFieldUsage()))
            {
                this.checkDataItems.put(item.getParamName(), item);
            }

            this.itemsIndex.put(item.getParamName(), new Integer(i));
        }
    }

    public IBusinessInstruction getInstruction()
    {
        return this.instruction;
    }

    public Map<String, String> getStandData()
    {
        return this.mapData;
    }

    public void setInstruction(IBusinessInstruction instruction)
    {
        this.instruction = instruction;
    }

    public void setStandData(Map<String, String> stdData)
    {
        this.mapData = stdData;
    }

    public Map<String, IDataItem> getCheck()
    {
        return this.checkDataItems;
    }

    private void init(List<IDataItem> itemsList)
    {
        for (IDataItem item : itemsList)
        {
            if (EnumFieldUsage.CODE_FILED.equals(item.getParam().getFieldUsage()))
            {
                this.code = (item.getChineseValue() == null ? "" : item.getChineseValue());
            }
            else if ((EnumFieldUsage.QUERY_FIELD.equals(item.getParam().getFieldUsage())) || (EnumFieldUsage.DYNAMIC_FIELD.equals(item.getParam().getFieldUsage())))
            {
                if (this.query.equals(""))
                    this.query = (item.getChineseValue() == null ? "" : item.getChineseValue());
                else {
                    this.query = (this.query + "|" + item.getChineseValue());
                }

            }
            else if (EnumFieldUsage.IPQUERY_FIELD.equals(item.getParam().getFieldUsage()))
            {
                if (this.ipquery.equals(""))
                    this.ipquery = (item.getChineseValue() == null ? "" : item.getChineseValue());
                else {
                    this.ipquery = (this.ipquery + "|" + item.getChineseValue());
                }
            }
            else if (EnumFieldUsage.CHECK_FIELD.equals(item.getParam().getFieldUsage()))
            {
                if (this.checkDataItems == null) {
                    this.checkDataItems = new HashMap();
                }
                this.checkDataItems.put(item.getParamName(), item);
            }
        }
        createKey();
    }

    private void createKey()
    {
        if ((this.code == null) && (this.query.equals(""))) {
            throw new RuntimeException("核查数据生成是出现异常，码号及查询字段没定义或为空。");
        }
        if ((this.code != null) && (!this.code.equals(""))) {
            if (this.query.equals(""))
                this.key = this.code;
            else
                this.key = (this.query + "|" + this.code);
        }
        else if ((this.ipquery != null) && (!this.ipquery.equals("")))
            this.key = this.ipquery;
        else
            this.key = this.query;
    }

    public String getValueByName(String name, int type)
    {
        String result = "";

        if (this.checkDataItems != null)
        {
            IDataItem item = (IDataItem)this.checkDataItems.get(name);
            if (null != item) {
                result = getValue(item, type);
            }

        }
        else
        {
            result = null;
        }
        return result;
    }

    public String getValueByIndex(int index, int type)
    {
        IDataItem item = (IDataItem)this.checkDataItems.get(Integer.valueOf(index));
        if (null != item) {
            return getValue(item, type);
        }
        return null;
    }

    private String getValue(IDataItem item, int type)
    {
        if (type == 1) {
            return item.getChineseValue();
        }
        return null;
    }

    public long getRangeCheckedBeg() {
        return this.rangeCheckedBeg;
    }

    public void setRangeCheckedBeg(long rangeCheckedBeg) {
        this.rangeCheckedBeg = rangeCheckedBeg;
    }

    public long getRangeCheckedEnd() {
        return this.rangeCheckedEnd;
    }

    public void setRangeCheckedEnd(long rangeCheckedEnd) {
        this.rangeCheckedEnd = rangeCheckedEnd;
    }

    public String getCheckitem()
    {
        return null;
    }

    public String getCollectkey()
    {
        return null;
    }

    public void setCollectkey(String collectkey)
    {
    }

    public String getLogfile()
    {
        return null;
    }

    public void setLogfile(String logfile)
    {
    }
}
