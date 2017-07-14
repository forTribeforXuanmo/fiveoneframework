package com.lsz.fiveoneframework.test.controller;

import com.alibaba.fastjson.JSON;
import com.lsz.fiveoneframework.bean.Data;
import com.lsz.fiveoneframework.test.model.Teather;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/7/14
 * time:22:02
 *
 * 传入前台的Json
 */
public class ReturnDataTest {
    private static final Logger logger= LoggerFactory.getLogger(ReturnDataTest.class);
    @Test
    public void test1(){
        Data data=new Data();
        Map map=new HashMap<>();
        map.put("a","110");
        map.put("b","2200");
        List list=new ArrayList();
        list.add("aa");
        list.add("bbb");
        list.add(1);
        Map all=new HashMap();
        all.put("map",map);
        all.put("rows",list);
        data.setModel(all);
        String jsonStr= JSON.toJSONString(data.getModel());
        logger.info(jsonStr);

        Data data2=new Data();
        Teather teather=new Teather();
        teather.setName("lsz");
        teather.setId("11dda");

        String jsonStr2=JSON.toJSONString(teather);
        logger.info(jsonStr2);
    }
}
