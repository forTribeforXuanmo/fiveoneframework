package com.lsz.fiveoneframework.test.controller;

import com.lsz.fiveoneframework.annotation.Action;
import com.lsz.fiveoneframework.annotation.Controller;
import com.lsz.fiveoneframework.annotation.Inject;
import com.lsz.fiveoneframework.bean.Data;
import com.lsz.fiveoneframework.bean.Param;
import com.lsz.fiveoneframework.bean.View;
import com.lsz.fiveoneframework.helper.DataBaseHelper;
import com.lsz.fiveoneframework.test.model.User;
import com.lsz.fiveoneframework.test.model.UserFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 测试无参数
     * @return
     */
    @Action("get:/")
    public View goIndex(){
        return new View("index.jsp").addModel("name","lishengzhu").addModel("password","1234");
    }

    /**
     * 测试有参数
     * @param param
     * @return
     */
    @Action("get:/go2")
    public View go2(Param param){
        userFunction.sayHello();
        userFunction.sayGoodBye();
        DataBaseHelper.insertEntity(User.class,param.getParamMap());
        return new View("go2.jsp");
    }

    /**
     * 测试无参数切返回字符串对象给前台
     * @return
     */
    @Action("get:/noParamTest")
    public Data noParamTest(){
        Map map=new HashMap<>();
        map.put("a","100");
        map.put("b","200");
        Data data=new Data(map);
        return data;
    }

    /**
     * 测试无参数且返回容器对象给前台
     * @return
     */
    @Action("get:/noParamTest2")
    public Data noParamTest2(){
        Data data=new Data("aaa");
        return data;
    }

    /**
     * 测试Data注解
     * @param param
     * @return
     */
    @Action("post:/annotationTest")
    @com.lsz.fiveoneframework.annotation.Data
    public List annotationTest(Param param){
        String name=param.getString("name");
        List list=new ArrayList();
        for(int i=0;i<10;i++){
            list.add(name+i);
        }
        return list;
    }
}
