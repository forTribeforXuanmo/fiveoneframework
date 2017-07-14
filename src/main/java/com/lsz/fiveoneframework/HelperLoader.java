package com.lsz.fiveoneframework;

import com.lsz.fiveoneframework.helper.*;
import com.lsz.fiveoneframework.proxy.ProxyManage;
import com.lsz.fiveoneframework.util.ClassUtil;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:22:59
 */
public class HelperLoader {

    public static void init(){
        Class<?> [] classList={ClassHelper.class, BeanHelper.class,AopHelper.class, IocHelper.class, ControllerHelper.class };
        for(Class<?> cls:classList){
            ClassUtil.loadClass(cls.getName(),true);
            System.out.println("加载顺序Loaderhelper:"+cls.getSimpleName());
        }
    }
}
