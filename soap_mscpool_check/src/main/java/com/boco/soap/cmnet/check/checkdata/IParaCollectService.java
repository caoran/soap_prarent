package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.pojo.CollectResult;
import com.boco.soap.cmnet.pojo.INeElement;

import java.util.List;

public interface IParaCollectService {
    List<CollectResult> collect(Ne paramINeElement, String paramString1, String paramString2, String paramString3, String paramString4);
}
