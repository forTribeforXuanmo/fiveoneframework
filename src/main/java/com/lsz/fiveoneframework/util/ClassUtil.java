package com.lsz.fiveoneframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/1
 * time:18:00
 *
 *  加载指定的包下的全部字节码class对象的集合
 *  工具类
 *
 */

public final class ClassUtil {
    private static final Logger logger= LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }
    /**
     * 加载类
     * **/
    public static Class<?> loadClass(String className,boolean isInit){
        Class<?> cls;
        try {
            cls=Class.forName(className,isInit,getClassLoader());
            return cls;
        } catch (ClassNotFoundException e) {
           logger.error("类加载失败:"+e);
           throw new RuntimeException("类没有找到",e);
        }
    }
    /**
     * 加载包名下的所有类
     * **/
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classSet=new HashSet<Class<?>>();
        try {
            Enumeration<URL> urls=getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url=urls.nextElement();
                if(url!=null){
                    String protocol=url.getProtocol();
                    if("file".equals(protocol)){
                        String packagePath=url.getPath().replaceAll("%20","");
                        addClass(classSet,packagePath,packageName);
                    }else if("jar".equals(protocol)){
                        JarURLConnection jarURLConnection= (JarURLConnection) url.openConnection();
                        if(jarURLConnection!=null){
                            JarFile jarFile=jarURLConnection.getJarFile();
                            if(jarFile!=null){
                                Enumeration<JarEntry> jarEntries=jarFile.entries();
                                while (jarEntries.hasMoreElements()){
                                    JarEntry jarEntry=jarEntries.nextElement();
                                    String jarEntryName=jarEntry.getName();
                                    if(jarEntryName.endsWith(".class")){
                                        String className=jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                        doAddClass(classSet,className);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("获取包下所有类set集合失败",e);
            throw  new RuntimeException(e);
        }
        return classSet;
    }
    /**添加进set集合**/
    public static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> cls=loadClass(className,true);
        classSet.add(cls);
    }
    /**如果是目录继续寻找**/
    public static void addClass(Set<Class<?>> classSet, String packagePath, final String packageName) {
        File[] files=new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (file.isFile()&&file.getName().endsWith(".class"))||file.isDirectory();
            }
        });
        for(File file:files){
            String fileName=file.getName();
            if(file.isFile()){
                String className=fileName.substring(0,fileName.lastIndexOf("."));
                if(StringUtil.isNotEmpty(className)){
                    className=packageName+"."+className;
                }
                doAddClass(classSet,className);
            }else {
                String subPackagePath=fileName;
                if(StringUtil.isNotEmpty(packagePath)){
                    subPackagePath=packagePath+"/"+subPackagePath;
                }
                String  subPackageName=fileName;
                if(StringUtil.isNotEmpty(packageName)){
                    subPackageName=packageName+"."+subPackageName;
                }
                addClass(classSet,subPackagePath,subPackageName);
           }
        }

    }
}
