package com.boco.soap.cmnet.common.util;

import java.util.HashMap;
import java.util.Map;

public class FormatMap {
    public static Map<String, Object> FormatMap(Map<String, ?> map)
    {
        Map resultmap = new HashMap();

        if (map != null) {
            for (String key : map.keySet()) {
                Object result = map.get(key);
                String newkey = key.toUpperCase();
                if (result != null)
                    resultmap.put(newkey, result);
                else {
                    resultmap.put(newkey, "");
                }
            }

        }

        return resultmap;
    }

    public static Map<String, String> FormatStrMap(Map<String, String> map)
    {
        Map resultmap = new HashMap();

        if (map != null) {
            for (String key : map.keySet()) {
                String result = (String)map.get(key);
                String newkey = key.toUpperCase();
                if (result != null)
                    resultmap.put(newkey, result);
                else {
                    resultmap.put(newkey, "");
                }
            }

        }

        return resultmap;
    }
}
