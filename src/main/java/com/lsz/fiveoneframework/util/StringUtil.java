package com.lsz.fiveoneframework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:17:27
 */
public final class StringUtil {
    /**
     * 判断是否为空
     * **/
    public static boolean isEmpty(String str){
        if(str!=null){
            str=str.trim();
        }
        return StringUtils.isEmpty(str);
    }
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
