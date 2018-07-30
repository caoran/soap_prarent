package com.boco.soap.cmnet.common.util;

import com.boco.soap.cmnet.check.pair.query.ExpanDataEnitites;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;

import java.util.*;

public class CheckExpansion {
    public static Map<String, ExpanDataEnitites> getexpansion(List<ICheckData> list)
    {
        Map map = new HashMap();

        for (ICheckData checkdata : list) {
            String code = checkdata.getData().getCode();
            if ((code != null) && (!code.equals("")))
            {
                IData data = checkdata.getData();
                String key = data.getKey();

                List keys = getkeys(key);

                Iterator e = keys.iterator();

                while (e.hasNext())
                {
                    String newkey = (String)e.next();

                    if (map.containsKey(newkey)) {
                        ExpanDataEnitites entity = (ExpanDataEnitites)map.get(newkey);
                        if ((entity != null) && (entity.getListdata() != null))
                        {
                            List tmplist = entity.getListdata();
                            tmplist.add(checkdata);
                            entity.setListdata(tmplist);
                        }

                        if ((entity.getData() == null) && (newkey.equals(key)))
                        {
                            entity.setData(checkdata);
                        }

                        map.put(newkey, entity);
                    }
                    else
                    {
                        ExpanDataEnitites entity = new ExpanDataEnitites();
                        if (newkey.equals(key))
                        {
                            entity.setData(checkdata);
                        }
                        List newcheckdata = new ArrayList();
                        newcheckdata.add(checkdata);
                        entity.setListdata(newcheckdata);
                        map.put(newkey, entity);
                    }
                }
            }
        }
        return map;
    }

    private static List<String> getkeys(String extendKey)
    {
        List list = new ArrayList();

        list.add(extendKey);

        boolean isRun = true;

        while (isRun)
        {
            if (extendKey.length() != 0)
            {
                extendKey = extendKey.substring(0, extendKey.length() - 1);

                isRun = (!extendKey.endsWith("|")) && (extendKey.length() > 0);

                if (isRun) {
                    list.add(extendKey);
                }
            }
            else
            {
                isRun = false;
            }

        }

        return list;
    }

    public static void main(String[] args) {
        String key = "A|B|C|138001";

        List list = getkeys(key);

        System.out.println(list);
    }
}
