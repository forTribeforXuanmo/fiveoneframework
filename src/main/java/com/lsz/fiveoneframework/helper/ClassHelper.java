package com.lsz.fiveoneframework.helper;


import com.lsz.fiveoneframework.annotation.Component;
import com.lsz.fiveoneframework.annotation.Controller;
import com.lsz.fiveoneframework.annotation.Service;
import com.lsz.fiveoneframework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:20:54
 * 从包下的class对象中 获取想要的class对象，
 * 可以获取带有service、controller、component等注解的class对象
 *
 *
 */
public final class ClassHelper {
    private static final Set<Class<?>> CLASS_SET ;
    static {
        String basePackage=ConfigHelper.getAppBasePackage();
        CLASS_SET= ClassUtil.getClassSet(basePackage);
    }
    /**
     * 获取应用包下的所有类
     * **/
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取Service类
     * **/
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
            if(cls.isAnnotationPresent(Service.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
    /**
     * 获取controller类
     * **/
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
            if(cls.isAnnotationPresent(Controller.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取component类
     * @return
     */
    public static Set<Class<?>> getComponentClass(){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
            if(cls.isAnnotationPresent(Component.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
    /**
     * 获取Service、controller、Component等
     * **/
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet=new HashSet<Class<?>>();
        beanClassSet.addAll(getControllerClassSet());
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getComponentClass());
        return beanClassSet;
    }
    /**
     * 获取应用包下某父类（或接口）的所有子类（或实现类）
     ***/

    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls:CLASS_SET){
            //a.isAssignableFrom(b) 判断代表a是否和b是同一类或者a是b的父类或接口
            if(superClass.isAssignableFrom(cls)&&!superClass.equals(cls)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
    /**
     * 获取应用包下带有某个注解的类
     * **/
    public static  Set<Class<?>> getClassByAnnotation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        for(Class<?> cls: CLASS_SET){
            if(cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
