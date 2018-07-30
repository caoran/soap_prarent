package com.boco.soap.cmnet.util;

import java.util.UUID;

public class UUIDTool {

    public UUIDTool() {
    }
    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID() {
        /*UUID uuid = UUID.randomUUID();
        String str = uuid.toString();  */
        // 去掉"-"符号
        return UUID.randomUUID().toString().replace("-", "");
    }


}
