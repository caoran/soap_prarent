package com.boco.soap.cmnet.pojo;

import java.util.List;
import java.util.Map;

public class CollectResult {
    private boolean success;
    private String errorMsg;
    private String neId;
    private String neName;
    private Integer neVendor;
    private Integer neType;
    private String neVersion;
    private String cmd;
    private String resultMeaning;
    private String resultPath;
    private boolean isSecond;
    private String Collectkey;
    private String logfilepath;

    private List<Map<String, String>> results;

    public String getNeId()
    {
        return this.neId;
    }
    public void setNeId(String neId) {
        this.neId = neId;
    }
    public Integer getNeVendor() {
        return this.neVendor;
    }
    public void setNeVendor(Integer neVendor) {
        this.neVendor = neVendor;
    }
    public Integer getNeType() {
        return this.neType;
    }
    public void setNeType(Integer neType) {
        this.neType = neType;
    }
    public String getNeVersion() {
        return this.neVersion;
    }
    public void setNeVersion(String neVersion) {
        this.neVersion = neVersion;
    }
    public String getCmd() {
        return this.cmd;
    }
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
    public String getResultMeaning() {
        return this.resultMeaning;
    }
    public void setResultMeaning(String resultMeaning) {
        this.resultMeaning = resultMeaning;
    }
    public String getResultPath() {
        return this.resultPath;
    }
    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }
    public String getNeName() {
        return this.neName;
    }
    public void setNeName(String neName) {
        this.neName = neName;
    }
    public boolean isSuccess() {
        return this.success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getErrorMsg() {
        return this.errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public List<Map<String, String>> getResults() {
        return results;
    }

    public void setResults(List<Map<String, String>> results) {
        this.results = results;
    }

    public boolean isSecond() {
        return isSecond;
    }

    public void setSecond(boolean second) {
        isSecond = second;
    }

    public String getCollectkey() {
        return Collectkey;
    }

    public void setCollectkey(String collectKey) {
        this.Collectkey = collectKey;
    }

    public String getLogfilepath() {
        return logfilepath;
    }

    public void setLogfilepath(String logfilepath) {
        this.logfilepath = logfilepath;
    }
}
