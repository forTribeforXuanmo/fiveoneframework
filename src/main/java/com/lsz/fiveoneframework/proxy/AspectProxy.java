package com.lsz.fiveoneframework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/10
 * time:1:48
 *
 * 抽象出代理类的模板，因为一个拦截代理类是用来拦截多个其他类
 * 1.一进来就执行begin()
 * 2.在来一个过滤，inteceptor
 * 3.拦截后方法执行前，before
 * 4.执行方法poxyChain.doProxyChain,也就是说在代理类中需要要传进ProxyChain对象来，
 * 因为里面有List<Proxy>
 * 方法的具体执行放在ProxyChain的链中依个执行
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger logger= LoggerFactory.getLogger(Proxy.class);
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result=null;
        //
        Class<?> cls=proxyChain.getTargetClass();
        //
        Method method=proxyChain.getTargetMethod();

        Object[] params=proxyChain.getTargetParams();
        begin();
        try {
            if(intercept(cls,method,params)){
                before(cls,method,params);
                result=proxyChain.doProxyChain();
                after(cls,method,params);
            }else {
                result=proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            logger.error("代理 失败",e);
            error(cls,method,params);
            throw e;
        }finally {
            end();
        }
        return result;
    }

    public void begin(){

    }
    public boolean intercept(Class<?> cls,Method method,Object[] params) throws  Throwable{
        return true;
    }
    public void before(Class<?> cls,Method method,Object[] params) throws Throwable{

    }
    public void after(Class<?> cls,Method method,Object[] params) throws Throwable{

    }
    public void error(Class<?> cls,Method method,Object[] params) throws Throwable{

    }
    public void end() throws Throwable{

    }
}
