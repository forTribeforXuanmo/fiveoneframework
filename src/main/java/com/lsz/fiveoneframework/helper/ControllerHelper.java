package com.lsz.fiveoneframework.helper;

import com.lsz.fiveoneframework.annotation.Action;
import com.lsz.fiveoneframework.bean.Handler;
import com.lsz.fiveoneframework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:22:39
 * 控制器助手
 */
public final class ControllerHelper {
    //存放请求与处理器的映射
    private static final Map<Request,Handler> ACTION_MAP=new HashMap<Request, Handler>();
    static {
        Set<Class<?>> controllerClassSet=ClassHelper.getControllerClassSet();
        if(CollectionUtils.isNotEmpty(controllerClassSet)){
            for(Class<?> controllerClass:controllerClassSet){
                Method[] methods=controllerClass.getDeclaredMethods();

                if(ArrayUtils.isNotEmpty(methods)){
                    for(Method method:methods){
                        if(method.isAnnotationPresent(Action.class)){
                            //获取action,因为里面必须有路径等的值
                            Action action=method.getAnnotation(Action.class);
                            //获取Url映射
                            String mapping=action.value();
                            //验证URL映射规则
                            if(mapping.matches("\\w+:/\\w*")){
                                String[] array=mapping.split(":");
                                if(ArrayUtils.isNotEmpty(array)&& array.length==2){
                                    String requestMethod=array[0].toLowerCase();
                                    String requestPath=array[1];
                                    Request request=new Request(requestMethod,requestPath);
                                    Handler handler=new Handler(controllerClass,method);
                                    ACTION_MAP.put(request,handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    /**
     * 获取handler
     * **/
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request=new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
