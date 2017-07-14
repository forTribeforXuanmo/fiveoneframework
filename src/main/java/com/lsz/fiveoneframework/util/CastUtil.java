package com.lsz.fiveoneframework.util;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:17:22
 */
public final class CastUtil {
    /**
     * 转为stirng
     */
    public static String castString(Object obj){
        return castString(obj,"");
    }

    public static String castString(Object obj, String defaultValue) {
        return obj!=null ? String.valueOf(obj) : defaultValue;
    }
    /**
     * 转为double
     */
    public static double castDouble(Object obj){
        return castDouble(obj,0);
    }

    public static double castDouble(Object obj, double defaultValue) {
        double value=defaultValue;
        if(obj!=null){
            String strValue=castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                value=Double.parseDouble(strValue);
            }

        }
        return value;
    }
    /**
     * 转为Long
     * **/
    public  static long castLong(Object obj){
        return  castLong(obj,0L);
    }

    public static long castLong(Object obj, long defaultValue) {
        long value=defaultValue;
        if(obj!=null){
            String strValue=castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                value=Long.parseLong(strValue);
            }
        }
        return value;
    }
    /***
     *转为Int
     * */
    public static int  castInt(Object obj){
        return castInt(obj,0);
    }

    public static int castInt(Object obj, int defaultValue) {
        int value=defaultValue;
        if(obj!=null){
            String strValue=castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                value=Integer.parseInt(strValue);
            }
        }
        return value;
    }
    /***
     * 转为boolean
     * */
    public static boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }

    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean value=defaultValue;
        if(obj!=null){
            value=Boolean.parseBoolean(castString(obj));
        }
        return value;
    }
}
