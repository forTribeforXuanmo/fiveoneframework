package com.lsz.fiveoneframework.helper;

import com.lsz.fiveoneframework.annotation.Inject;
import com.lsz.fiveoneframework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:22:09
 */
public final  class IocHelper {
    private static final Logger logger= LoggerFactory.getLogger(IocHelper.class);
    static {

        Map<Class<?>,Object> beanMap=BeanHelper.getBeanMap();
        if(MapUtils.isNotEmpty(beanMap)){
            for(Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()){
                Class<?> beanClass=beanEntry.getKey();
                Object beanInstance=beanEntry.getValue();
                Field[] beanFileds=beanClass.getDeclaredFields();
                if(ArrayUtils.isNotEmpty(beanFileds)){
                    for(Field field:beanFileds){
                        if(field.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass=field.getType();
                            Object beanFieldInstance=beanMap.get(beanFieldClass);
                            if(beanFieldInstance!=null){
                                ReflectionUtil.setFiled(beanInstance,field,beanFieldInstance);
                                logger.info(field.getName()+"注入成功");
                            }
                        }
                    }
                }
            }
        }
    }
}
