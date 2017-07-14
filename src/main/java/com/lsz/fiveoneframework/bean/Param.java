package com.lsz.fiveoneframework.bean;

import com.lsz.fiveoneframework.util.CastUtil;

import java.util.Map;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:23:09
 */
public class Param {
    private Map<String,Object> paramMap;

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
    public long getLong(String name){
        return CastUtil.castLong(paramMap.get(name));
    }
    public int getInt(String name){
        return CastUtil.castInt(paramMap.get(name));
    }
    public String getString(String name){
        return CastUtil.castString(paramMap.get(name));
    }

}
