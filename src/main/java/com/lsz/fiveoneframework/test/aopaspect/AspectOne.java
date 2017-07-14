package com.lsz.fiveoneframework.test.aopaspect;

import com.lsz.fiveoneframework.annotation.Aspect;
import com.lsz.fiveoneframework.annotation.Controller;
import com.lsz.fiveoneframework.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/10
 * time:21:01
 */
@Aspect(Controller.class)
public class AspectOne extends AspectProxy{
    @Override
    public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
        return super.intercept(cls, method, params);
    }

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println(cls.getSimpleName()+":"+method.getName()+"开始。。。。");
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println(cls.getSimpleName()+":"+method.getName()+"结束。。。。");
    }
}
