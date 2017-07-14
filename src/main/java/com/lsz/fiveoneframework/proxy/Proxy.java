package com.lsz.fiveoneframework.proxy;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/9
 * time:4:26
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
