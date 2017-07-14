package com.lsz.fiveoneframework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/9
 * time:7:50
 */
public class ProxyChain {
    private final Class<?> targetClass;
    private final Object targetObject;
    private final Object[] targetParams;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final List<Proxy> proxyList;

    private int proxyIndex=0;
    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object getTargetObject() {
        return targetObject;
    }

    public Object[] getTargetParams() {
        return targetParams;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }

    public List<Proxy> getProxyList() {
        return proxyList;
    }

    public ProxyChain(Class<?> targetClass, Object targetObject, Object[] targetParams, Method targetMethod, MethodProxy methodProxy, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetParams = targetParams;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.proxyList = proxyList;
    }
    public Object doProxyChain() throws Throwable {
        Object result=null;
        if(proxyIndex<proxyList.size()){
           result=proxyList.get(proxyIndex++).doProxy(this);
        }else {
            result=methodProxy.invokeSuper(targetObject,targetParams);
        }
        return result;
    }

}
