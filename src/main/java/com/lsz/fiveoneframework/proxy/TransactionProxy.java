package com.lsz.fiveoneframework.proxy;

import com.lsz.fiveoneframework.annotation.Transaction;
import com.lsz.fiveoneframework.helper.DataBaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/7/13
 * time:23:45
 */
public class TransactionProxy implements Proxy {
    private static final Logger logger= LoggerFactory.getLogger(TransactionProxy.class);
    private static final ThreadLocal<Boolean> FLAG_HOLDER=new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result=null;
        boolean flag=FLAG_HOLDER.get();
        Method method=proxyChain.getTargetMethod();
        if(!flag&& method.isAnnotationPresent(Transaction.class) ){
            FLAG_HOLDER.set(true);
            try {
                DataBaseHelper.beginTransaction();
                logger.debug("====开始事务=======");
                result=proxyChain.doProxyChain();
                DataBaseHelper.commitTransaction();
                logger.debug("=======提交事务=========");
            } catch (Exception e) {
                DataBaseHelper.rollbackTransaction();
               logger.error("==========回滚事务==========");
               throw e;
            } finally {
                FLAG_HOLDER.remove();
            }
        }else {
            proxyChain.doProxyChain();
        }
        return null;
    }
}
