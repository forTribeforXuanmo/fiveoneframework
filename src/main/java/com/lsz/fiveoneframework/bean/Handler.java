package com.lsz.fiveoneframework.bean;

import java.lang.reflect.Method;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:22:37
 * 封装  Action
 */
public class Handler {
    //哪个类里面的哪个方法
    private Class<?> controllerClass;
    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
