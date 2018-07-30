package com.boco.soap.cmnet.check.result.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameCheckDataImpl implements ICheckData {
    private List<IData> datas;
    private EnumCheckDataType dataType;
    private ICheckData keyEquData;
    private ICheckData keyAbbrData;
    private List<ICheckData> keyExtDatas;
    private boolean curPairChecked = false;

    private boolean stdChecked = false;

    private boolean exitNet = false;

    private boolean initiative = false;

    private boolean passivity = false;

    private List<ICheckData> relationCheckDatas = new ArrayList();

    private boolean isCurCheckFlag = false;

    private Map<String, List<ICheckData>> stdRelationCheckDatas = new HashMap();

    private Map<String, List<ICheckData>> curRelationCheckDatas = new HashMap();

    public ParameCheckDataImpl(List<IData> datas, EnumCheckDataType dataType) {
        this.datas = datas;
        this.dataType = dataType;
    }

    public IData getData()
    {
        return null;
    }

    public EnumCheckDataType getDataType()
    {
        return this.dataType;
    }

    public List<ICheckData> getUnionCheckData()
    {
        return null;
    }

    public boolean isExitNet()
    {
        return this.exitNet;
    }

    public boolean isInitiativeRelevance()
    {
        return this.initiative;
    }

    public boolean isPassivityRelevance()
    {
        return this.passivity;
    }

    public boolean isUnion()
    {
        return false;
    }

    public void setData(IData data)
    {
    }

    public void setDataType(EnumCheckDataType checkDataType)
    {
        this.dataType = checkDataType;
    }

    public void setExitNet(boolean isExitNet)
    {
        this.exitNet = isExitNet;
    }

    public void setUnionCheckData(List<ICheckData> unionCheckData)
    {
    }

    public ICheckData getKeyEquData()
    {
        return this.keyEquData;
    }

    public void setKeyEquData(ICheckData keyEquData)
    {
        this.keyEquData = keyEquData;
    }

    public ICheckData getKeyAbbrData()
    {
        return this.keyAbbrData;
    }

    public void setKeyAbbrData(ICheckData keyAbbrData)
    {
        this.keyAbbrData = keyAbbrData;
    }

    public List<ICheckData> getKeyExtDatas()
    {
        return this.keyExtDatas;
    }

    public void setKeyExtDatas(List<ICheckData> keyExtDatas)
    {
        this.keyExtDatas = keyExtDatas;
    }

    public boolean isCurPairChecked()
    {
        return this.curPairChecked;
    }

    public void setCurPairChecked(boolean curPairChecked)
    {
        this.curPairChecked = curPairChecked;
    }

    public boolean isStdChecked()
    {
        return this.stdChecked;
    }

    public void setStdChecked(boolean stdChecked)
    {
        this.stdChecked = stdChecked;
    }

    public void setKeyExtDatas(ICheckData keyExtData)
    {
        if (this.keyExtDatas == null) {
            this.keyExtDatas = new ArrayList();
        }

        this.keyExtDatas.add(keyExtData);
    }

    public void setRangePair(ICheckData relationCheckData)
    {
        this.relationCheckDatas.add(relationCheckData);
    }

    public List<ICheckData> getRangePair()
    {
        return this.relationCheckDatas;
    }

    public boolean isRangeCheckFlag()
    {
        return false;
    }

    public void setRangeCheckFlag(boolean isRangeRela)
    {
    }

    public Map<String, List<ICheckData>> getCurRelation()
    {
        return this.curRelationCheckDatas;
    }

    public Map<String, List<ICheckData>> getStdRelation()
    {
        return this.stdRelationCheckDatas;
    }

    public void setCurRelation(ICheckData checkCurRelation, String curKey)
    {
        if (this.curRelationCheckDatas.get(curKey) != null) {
            ((List)this.curRelationCheckDatas.get(curKey)).add(checkCurRelation);
        } else {
            List curCheckDataList = new ArrayList();
            curCheckDataList.add(checkCurRelation);
            this.curRelationCheckDatas.put(curKey, curCheckDataList);
        }
    }

    public void setStdRelation(ICheckData checkStdRelation, String stdKey)
    {
        if (this.stdRelationCheckDatas.get(stdKey) != null) {
            ((List)this.stdRelationCheckDatas.get(stdKey)).add(checkStdRelation);
        } else {
            List stdCheckDataList = new ArrayList();
            stdCheckDataList.add(checkStdRelation);
            this.stdRelationCheckDatas.put(stdKey, stdCheckDataList);
        }
    }

    public boolean isRangeCheckCurFlag()
    {
        return this.isCurCheckFlag;
    }

    public void setRangeCheckCurFlag(boolean isCurCheckFlag)
    {
        this.isCurCheckFlag = isCurCheckFlag;
    }

    public List<IData> getDatas()
    {
        return this.datas;
    }

    public void setDatas(List<IData> datas) {
        this.datas = datas;
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
}
