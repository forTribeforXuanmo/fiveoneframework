package com.lsz.fiveoneframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:17:02
 */
public final class PropsUtil {
    private static final Logger logger= LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载属性文件
     *
     */
    public static Properties loadProps(String fileName){
        Properties props=null;
        InputStream is=null;
        is=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if(is==null){
            logger.error(fileName+"文件没找到");
        }else {
        props=new Properties();
        try {
            props.load(is);
            if(is!=null){
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件读取失败");
        }
        }
        return props;
    }
    /***
     * 获取字符型
     * */
    public static String getString(Properties props,String key){
        return getString(props,key,"");
    }

    public static String getString(Properties props, String key, String defaultValue) {
        String value=defaultValue;
        if(props.containsKey(key)){
            value=props.getProperty(key);
        }
        return value;
    }
    /***
     * 获取int类型
     * */
    public static int getInt(Properties props,String key){
        return getInt(props,key,0);
    }

    public static int getInt(Properties props, String key, int defaultValue) {
        int value=defaultValue;
        if(props.containsKey(key)){
            value=CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }
    /**
     * 获取boolean
     */
    public static boolean getBoolean(Properties props,String key){
        return getBoolean(props,key,false);
    }

    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value=defaultValue;
        if(props.containsKey(key)){
            value=CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
