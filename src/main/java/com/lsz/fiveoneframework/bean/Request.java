package com.lsz.fiveoneframework.bean;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:22:29
 */
public class Request {
    private String requestMethod;  //get,post,put,delete
    private String requestPath;   //路径
    public Request(String requestMethod,String requestPath){
        this.requestMethod=requestMethod;
        this.requestPath=requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    @Override
    public boolean equals(Object obj) {
       if(obj instanceof Request){
           Request he= (Request) obj;
           if(he.getRequestMethod().equals(this.requestMethod) && he.getRequestPath().equals(this.requestPath)){
               return true;
           }
       }
       return false;
    }
    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
