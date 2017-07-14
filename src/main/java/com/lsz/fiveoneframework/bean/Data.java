package com.lsz.fiveoneframework.bean;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:23:18
 *  @deprecated  不推荐使用
 *  使用的话json字符串会多出object:出来不方便
 */
public class Data {
    private Object model;
    public Data(){}
    public Data(Object object){
        this.model =object;
    }
    public Object getModel(){
        return model;
    }

    public void setModel(Object object) {
        this.model = object;
    }
}
