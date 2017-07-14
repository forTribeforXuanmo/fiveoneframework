package com.lsz.fiveoneframework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:23:13
 */
public class View {
    private String path;
    private Map<String,Object> model;
    public View(String path){
        this.path=path;
        model=new HashMap<String,Object>();
    }
    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }
    public String getPath(){
        return path;
    }
    public Map<String,Object> getModel(){
        return model;
    }
    public View setModel(Map<String,Object> map){
        this.model=map;
        return this;
    }
}
