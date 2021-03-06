package com.lsz.fiveoneframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/7/14
 * time:23:11
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Data {

}
