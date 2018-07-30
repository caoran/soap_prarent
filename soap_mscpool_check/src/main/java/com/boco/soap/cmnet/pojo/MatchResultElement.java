package com.boco.soap.cmnet.pojo;

import com.boco.soap.cmnet.check.result.IBusinessConstraint;

import java.util.List;
import java.util.Map;

public class MatchResultElement {
    private IBusinessConstraint constraint;
    private List<Map<String, String>> standDatas;
    private List<Map<String, String>> detailDatas;

    public IBusinessConstraint getConstraint()
    {
        return this.constraint;
    }

    public void setConstraint(IBusinessConstraint constraint) {
        this.constraint = constraint;
    }

    public List<Map<String, String>> getStandDatas() {
        return this.standDatas;
    }

    public void setStandDatas(List<Map<String, String>> standDatas) {
        this.standDatas = standDatas;
    }

    public List<Map<String, String>> getDetailDatas() {
        return this.detailDatas;
    }

    public void setDetailDatas(List<Map<String, String>> detailDatas) {
        this.detailDatas = detailDatas;
    }
}
