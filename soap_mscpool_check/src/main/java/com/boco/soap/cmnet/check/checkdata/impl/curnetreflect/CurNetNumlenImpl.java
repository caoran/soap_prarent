package com.boco.soap.cmnet.check.checkdata.impl.curnetreflect;

import com.boco.soap.cmnet.check.checkdata.IBusinessCustomerReflect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurNetNumlenImpl implements IBusinessCustomerReflect
{
    public List<Map<String, String>> businessCurNetDataReturn(List<Map<String, String>> curNetDataList, String[] colname, String filterlen, String url)
    {
        List list = new ArrayList();

        int len = Integer.parseInt(filterlen);

        if ((curNetDataList != null) && (curNetDataList.size() > 0)) {
            for (Map map : curNetDataList) {
                String phonenumber = "";
                if ((colname != null) && (colname.length > 0))
                {
                    phonenumber = (String)map.get(colname[0]);
                }
                if (phonenumber.length() >= len) {
                    list.add(map);
                }
            }
        }

        return null;
    }
}
