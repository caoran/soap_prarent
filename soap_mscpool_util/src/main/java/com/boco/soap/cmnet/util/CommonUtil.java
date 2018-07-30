package com.boco.soap.cmnet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public final class CommonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CommonUtil.class);
    private static final IdWorker idWorker = new IdWorker(31, 31);
    private static final CUIDHexGenerator cuidHexGenerator = CUIDHexGenerator.getInstance();
    public static final String UTF8 = "UTF-8";

    private CommonUtil() {
        //
    }
	
	public static String smsCode() {
		return smsCode(4);
	}
	
	public static String smsCode(int len) {
		int mount;
		if(len < 4){
			mount = 4;
		}else if(len > 6){
			mount = 6;
		}else{
			mount=len;
		}
		StringBuilder strBuilder = new StringBuilder();
		for(int i=0;i<mount;i++){
			strBuilder.append((new Random()).nextInt(10));
		}
		return strBuilder.toString();
	}

    public static String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private static String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Numbers.toString(hi | (val & (hi - 1)), Numbers.MAX_RADIX).substring(1);
    }

    /**
     * 以62进制（字母加数字）生成19位UUID，最短的UUID
     * 
     * @return
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        StringBuilder sb = new StringBuilder();
        sb.append(digits(uuid.getMostSignificantBits() >> 32, 8));
        sb.append(digits(uuid.getMostSignificantBits() >> 16, 4));
        sb.append(digits(uuid.getMostSignificantBits(), 4));
        sb.append(digits(uuid.getLeastSignificantBits() >> 48, 4));
        sb.append(digits(uuid.getLeastSignificantBits(), 12));
        return sb.toString();
    }
    
    public static String uid() {
    	return UUID.randomUUID().toString().toLowerCase().replace("-", "");
    }
    
    public static long nextId(){
    	return idWorker.nextId();
    }

    public static String nextStrId(){return cuidHexGenerator.generate();}

    public static final String[] removeBlankElement(String[] arr) {
        List<String> list = new ArrayList<>();
        if (arr != null) {
            for (String _element : arr) {
                if (_element == null || _element.length() == 0 || _element.trim().length() == 0) {
                    continue;
                }
                list.add(_element);
            }
        }
        String[] temp = new String[list.size()];
        return list.toArray(temp);
    }

}
