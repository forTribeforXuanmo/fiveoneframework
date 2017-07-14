package com.lsz.fiveoneframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:23:56
 */
public class StreamUtil {
    private static final Logger logger= LoggerFactory.getLogger(StreamUtil.class);
    public static String getString(InputStream is){
        StringBuilder sb=new StringBuilder();
        BufferedReader reader=new BufferedReader(new InputStreamReader(is));
        String line=null;
        try {
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            logger.error("输入流读取失败",e);
            throw  new RuntimeException(e);
        }
        return sb.toString();
    }


}
