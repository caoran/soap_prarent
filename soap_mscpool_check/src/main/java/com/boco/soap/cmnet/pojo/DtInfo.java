package com.boco.soap.cmnet.pojo;

public class DtInfo {
    public static final String DT_TYPE_SCRIPT = "script";
    public static final String DT_TYPE_COMMENT = "comment";
    public static final String ERROR_CHECK_TYPE_MORE = "MORE";
    public static final String ERROR_CHECK_TYPE_ERROR = "ERROR";
    public static final String ERROR_CHECK_TYPE_LESS = "LESS";
    public static final String OPERATE_TYPE_ADD = "ADD";
    public static final String OPERATE_TYPE_DEL = "DEL";
    public static final String OPERATE_TYPE_MOD = "MOD";
    private String resultId;
    private String code;
    private String dt;
    private String dtType = "script";
    private String groupId = "0";
    private String checkType = "";
    private String operateType = "";
    private String comment = "";
    private String[] colsVal;
    private String dtId;

    public DtInfo(String dtType, String dt)
    {
        this.dtType = dtType;
        this.dt = dt;
    }

    public String getDtType()
    {
        return this.dtType;
    }

    public void setDtType(String dtType)
    {
        this.dtType = dtType;
    }

    public String getComment()
    {
        return this.comment;
    }

    public void setComment(String comment)
    {
        if (this.comment.equals(""))
            this.comment = comment;
        else
            this.comment = (this.comment + "," + comment);
    }

    public String getGroupId()
    {
        return this.groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getCheckType()
    {
        return this.checkType;
    }

    public void setCheckType(String checkType)
    {
        this.checkType = checkType;
    }

    public String getOperateType()
    {
        return this.operateType;
    }

    public void setOperateType(String operateType)
    {
        this.operateType = operateType;
    }

    public DtInfo()
    {
    }

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDtId()
    {
        return this.dtId;
    }

    public void setDtId(String dtId)
    {
        this.dtId = dtId;
    }

    public DtInfo(String resultId, String dt, String[] colsVal) {
        this.resultId = resultId;
        this.dt = dt;
        this.colsVal = colsVal;
    }
    public String getResultId() {
        return this.resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getDt() {
        return this.dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String[] getColsVal() {
        return this.colsVal;
    }

    public void setColsVal(String[] colsVal) {
        this.colsVal = colsVal;
    }
}
