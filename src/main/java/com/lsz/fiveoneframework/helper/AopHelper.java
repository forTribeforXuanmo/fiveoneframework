package com.lsz.fiveoneframework.helper;

import com.lsz.fiveoneframework.annotation.Aspect;
import com.lsz.fiveoneframework.annotation.Service;
import com.lsz.fiveoneframework.proxy.AspectProxy;
import com.lsz.fiveoneframework.proxy.Proxy;
import com.lsz.fiveoneframework.proxy.ProxyManage;
import com.lsz.fiveoneframework.proxy.TransactionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/11
 * time:22:10
 * aop助手类，获取切面与切点的映射
 */
public class AopHelper {
    private static  final Logger logger= LoggerFactory.getLogger(AopHelper.class);
    static {
        Map<Class<?>,Set<Class<?>>> proxyMap=createProxyMap();
        System.out.println("proxyMap:"+proxyMap.size());

            try {
                Map<Class<?>,List<Proxy>> targetMap=getTargetMap(proxyMap);
                for(Map.Entry<Class<?>,List<Proxy>> targetEntry:targetMap.entrySet()){
                     Class<?> targetClass=targetEntry.getKey();
                     List<Proxy> proxyList=targetEntry.getValue();
                    try {
                        Object proxy= ProxyManage.createProxy(targetClass,proxyList);
                        BeanHelper.setBean(targetClass,proxy);
                        logger.info("生成"+targetClass.getSimpleName()+" 的代理类"+proxy.toString());
                    } catch (Exception e) {
                        logger.error("aophelper 失败", e);
                        throw new RuntimeException("aophelper 失败", e);
                    }
                }

            } catch (IllegalAccessException e) {
                logger.error("非法进入异常IllegalAccessException");
                e.printStackTrace();
            } catch (InstantiationException e) {
                logger.error("实例化异常InstantiationException");
                e.printStackTrace();
            }

    }

    /**
     *  获取map<切面类Proxy的子类---》【需要切的多个类集合】>
     * @return
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap(){
        Map<Class<?>,Set<Class<?>>> proxyMap=new HashMap<Class<?>, Set<Class<?>>>();
        //获取AspectProxy的子类，因为子类重写了里面的方法
        Set<Class<?>> proxyClassSet=ClassHelper.getClassSetBySuper(AspectProxy.class);
        for(Class<?> proxyClass:proxyClassSet){
            if(proxyClass.isAnnotationPresent(Aspect.class)){
                Aspect aspect=proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet=getTargetClassSet(aspect);
                proxyMap.put(proxyClass,targetClassSet);
            }
        }
        //添加事务的切面
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 事务切面，直接让TransactionProxy来拦截全部的service,这样当service上有@Transaction注解则开启事务
     * @param proxyMap
     */
    public static void addTransactionProxy(Map<Class<?>,Set<Class<?>>> proxyMap){
        Set<Class<?>> serviceClassSet = ClassHelper.getClassByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class,serviceClassSet);
    }



    /**
     * 获取Aspect注解中的value的值，而这个值又是个注解,为了针对带有某个注解的类来拦截
     * @param aspect 注解对象
     * @return  value（）中的带有某个注解的类的集合
     */
    private static Set<Class<?>> getTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet=new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        //不能自己切自己
        if(annotation!=null && (annotation!=Aspect.class)){
            targetClassSet = ClassHelper.getClassByAnnotation(annotation);
        }
        return targetClassSet;
    }

    /**反转之前的proxy,获取 要代理类和代理类的集合映射
     *
     * @param proxyMap MAP:( 代理类--》【要代理的多个类】)
     * @return  Map:要代理的类---》代理类的实例集合
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static Map<Class<?>,List<Proxy>> getTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException {
        Map<Class<?>,List<Proxy>> targetMap= new HashMap<Class<?>, List<Proxy>>();
        for(Map.Entry<Class<?>,Set<Class<?>>> proxyEntry:proxyMap.entrySet()){
            Class<?> proxyClass=proxyEntry.getKey();  //代理类
            Set<Class<?>> targetClassSet=proxyEntry.getValue(); //目标类集合
            for(Class<?> targetClass:targetClassSet){
                Proxy proxy=(Proxy) proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)){
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList=new ArrayList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass,proxyList);
                }
                logger.info("要代理的类："+targetClass.getName()+"---------"+"代理的切面类:"+proxy.getClass());
            }
        }
        return targetMap;
    }
}
