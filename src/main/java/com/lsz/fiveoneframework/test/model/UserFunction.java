package com.lsz.fiveoneframework.test.model;

import com.lsz.fiveoneframework.annotation.Component;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/10
 * time:17:44
 */
@Component
public class UserFunction {
    public void sayHello(){
        System.out.println("=======hello=======");
    }
    public void sayGoodBye(){
        System.out.println("=========good bye=======");
    }
}
