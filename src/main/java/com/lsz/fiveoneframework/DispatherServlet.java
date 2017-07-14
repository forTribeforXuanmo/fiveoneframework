package com.lsz.fiveoneframework;

import com.alibaba.fastjson.JSON;
import com.lsz.fiveoneframework.bean.Data;
import com.lsz.fiveoneframework.bean.Handler;
import com.lsz.fiveoneframework.bean.Param;
import com.lsz.fiveoneframework.bean.View;
import com.lsz.fiveoneframework.helper.BeanHelper;
import com.lsz.fiveoneframework.helper.ConfigHelper;
import com.lsz.fiveoneframework.helper.ControllerHelper;
import com.lsz.fiveoneframework.util.ReflectionUtil;
import com.lsz.fiveoneframework.util.StreamUtil;
import com.lsz.fiveoneframework.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:23:21
 *
 * 分发器 处理全部请求/* 0：容器一启动就初始化这个Servlet
 */

@WebServlet(urlPatterns = "/*",loadOnStartup = 0)
public class DispatherServlet extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext=servletConfig.getServletContext();
        //注册处理jsp的servlet
        ServletRegistration jspServlet=servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");

        ServletRegistration defaultServlet=servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestMethod=req.getMethod().toLowerCase();
        String requestPath=req.getPathInfo();
        Handler handler= ControllerHelper.getHandler(requestMethod,requestPath);
        if(handler!=null){
            Class<?> controllerClass=handler.getControllerClass();
            Object controllerBean= BeanHelper.getBean(controllerClass);
            Map<String,Object> paramMap=new HashMap<String, Object>();
            Enumeration<String> paramNames=req.getParameterNames();
            while (paramNames.hasMoreElements()){
                String paramName=paramNames.nextElement();
                String paramValue=req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            String body= URLDecoder.decode(StreamUtil.getString(req.getInputStream()),"utf-8");
            System.out.println("body:"+body);
            /*************/
            if(StringUtil.isNotEmpty(body)){
                String[] params= StringUtils.split(body,"&");
                if(ArrayUtils.isNotEmpty(params)){
                    for(String param:params){
                        String[] array=StringUtils.split(param,"=");
                        if(ArrayUtils.isNotEmpty(array)&&array.length==2){
                            String paramName=array[0];
                            String paramValue=array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }
            /***************/
            Param param=new Param(paramMap);
            //调用action方法
            Method actionMethod=handler.getActionMethod();
            Object result= ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);
            //处理返回值
            if(result instanceof View){
                View view=(View)result;
                String path=view.getPath();
                if(path.startsWith("/")){
                    resp.sendRedirect(req.getContextPath()+path);
                }else {
                    Map<String,Object> model=view.getModel();
                    for(Map.Entry<String,Object> entry:model.entrySet()){
                        req.setAttribute(entry.getKey(),entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(req,resp);
                }
            }else if(result instanceof Data){
                    Data data= (Data) result;
                    Object model=data.getModel();
                    if(model!=null){
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        PrintWriter pw=resp.getWriter();
                        String json= JSON.toJSONString(data);
                        pw.write(json);
                        pw.flush();
                        pw.close();
                    }
            }


        }
    }


}
