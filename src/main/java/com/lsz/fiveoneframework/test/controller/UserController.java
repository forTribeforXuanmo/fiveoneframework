package com.lsz.fiveoneframework.test.controller;

import com.lsz.fiveoneframework.annotation.Action;
import com.lsz.fiveoneframework.annotation.Controller;
import com.lsz.fiveoneframework.annotation.Inject;
import com.lsz.fiveoneframework.bean.Param;
import com.lsz.fiveoneframework.bean.View;
import com.lsz.fiveoneframework.helper.DataBaseHelper;
import com.lsz.fiveoneframework.test.model.User;
import com.lsz.fiveoneframework.test.model.UserFunction;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/10
 * time:17:40
 */
@Controller
public class UserController {
    @Inject
    private UserFunction userFunction;
    @Action("get:/")
    public View goIndex(Param param){
        return new View("index.jsp").addModel("name","lishengzhu").addModel("password","1234");
    }
    @Action("get:/go2")
    public View go2(Param param){
        userFunction.sayHello();
        userFunction.sayGoodBye();
        DataBaseHelper.insertEntity(User.class,param.getParamMap());
        return new View("go2.jsp");
    }
}
