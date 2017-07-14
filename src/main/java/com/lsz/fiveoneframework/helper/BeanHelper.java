package com.lsz.fiveoneframework.helper;

import com.lsz.fiveoneframework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:21:48
 *  实例化class对象获得bean实例
 */
public final class BeanHelper {
    private static final Map<Class<?>,Object> BEAN_MAP=new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet =ClassHelper.getBeanClassSet();
        for (Class<?> beanClass:beanClassSet) {
            Object obj= ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T>  T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("无法获取bean的实例:"+cls);
        }
        return (T) BEAN_MAP.get(cls);
    }
    public static void setBean(Class<?> cls,Object obj){
        BEAN_MAP.put(cls,obj);
    }
}
