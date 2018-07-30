package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.pojo.MatchResultElement;

import java.util.List;

public class MatchResult {
    private String solutionid;
    private String stdtablename;
    private Ne ne;
    private List<MatchResultElement> matchResultElements;

    public String getSolutionid()
    {
        return this.solutionid;
    }

    public void setSolutionid(String solutionid) {
        this.solutionid = solutionid;
    }

    public String getStdtablename() {
        return this.stdtablename;
    }

    public void setStdtablename(String stdtablename) {
        this.stdtablename = stdtablename;
    }

    public List<MatchResultElement> getMatchResultElements() {
        return this.matchResultElements;
    }

    public void setMatchResultElements(List<MatchResultElement> matchResultElements)
    {
        this.matchResultElements = matchResultElements;
    }

    public Ne getNe() {
        return this.ne;
    }

    public void setNe(Ne ne) {
        this.ne = ne;
    }
}
