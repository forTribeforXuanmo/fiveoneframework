package com.lsz.fiveoneframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:21:33
 *
 * 反射工具
 */
public final class ReflectionUtil {
    private static final Logger  logger= LoggerFactory.getLogger(ReflectionUtil.class);

    public static Object newInstance(Class<?>cls){
        Object instance = null;
        try {
            instance=cls.newInstance();
        } catch (InstantiationException e) {
           logger.error("实例化异常",e);
        } catch (IllegalAccessException e) {
            logger.error("实例化异常:",e);
        }
        return instance;
    }
    public static Object invokeMethod(Object obj, Method method,Object... agrs){
        Object result = null;
        method.setAccessible(true);
        try {
             result=method.invoke(obj,agrs);
        } catch (IllegalAccessException e) {
            logger.error("实例化异常",e);
        } catch (InvocationTargetException e) {
            logger.error("实例化异常",e);
        }
        return result;
    }
    public static void setFiled(Object obj, Field field, Object value){
        field.setAccessible(true);
        try {
            field.set(obj,value);
        } catch (IllegalAccessException e) {
            logger.error("设置对象属性失败：",e);
            throw  new RuntimeException(e);
        }

    }
}
