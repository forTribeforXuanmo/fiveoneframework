package com.lsz.fiveoneframework.annotation;

import java.lang.annotation.*;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/7
 * time:20:44
 *
 * 切面类注解
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
   Class<? extends Annotation> value();
}
