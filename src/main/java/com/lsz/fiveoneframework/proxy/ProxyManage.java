package com.lsz.fiveoneframework.proxy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/9
 * time:22:19
 */
public class ProxyManage {
    private static final Logger loggger= LoggerFactory.getLogger(ProxyManage.class);

    /**
     * 创建出代理后动态生成的类
     * @param targetClass 要代理的类
     * @param proxyList   切面类的集合实例对象
     * @param <T>
     * @return   最终的代理后的类
     */
    public static  <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList){
        Object object= null;
        try {
            object = Enhancer.create(targetClass, new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return new ProxyChain(targetClass,o,objects,method,methodProxy,proxyList).doProxyChain();
                }
            });
        } catch (Throwable e) {
                loggger.error("ProxyManage失败",e);
                throw new RuntimeException("proxymanage代理失败",e);
        }

        return (T) object;
    }


}
