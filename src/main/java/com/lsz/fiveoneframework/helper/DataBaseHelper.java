package com.lsz.fiveoneframework.helper;

import com.lsz.fiveoneframework.ConfigConstant;
import com.lsz.fiveoneframework.util.PropsUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * author: lishengzhu
 * eamil:530735771@qq.com
 * date:2017/6/2
 * time:15:35
 */
public class DataBaseHelper {
    private static  final Logger logger= LoggerFactory.getLogger(DataBaseHelper.class);
    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL;
    private static final QueryRunner QUERY_RUNNER;
    private static final BasicDataSource DATA_SOURCE;
    private static final String DRIVER;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final String  URL;
    static {
        Properties props= PropsUtil.loadProps("fiveone.properties");
            DRIVER=props.getProperty(ConfigConstant.JDBC_DRIVER);
            USERNAME=props.getProperty(ConfigConstant.JDBC_USERNAME);
            PASSWORD=props.getProperty(ConfigConstant.JDBC_PASSWORD);
            URL=props.getProperty(ConfigConstant.JDBC_URL);
            CONNECTION_THREAD_LOCAL=new ThreadLocal<Connection>();
            QUERY_RUNNER=new QueryRunner();
            DATA_SOURCE=new BasicDataSource();
            DATA_SOURCE.setDriverClassName(DRIVER);
            DATA_SOURCE.setUrl(URL);
            DATA_SOURCE.setUsername(USERNAME);
            DATA_SOURCE.setPassword(PASSWORD);
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("找不到数据库连接驱动",e);
        }
    }

    public static Connection getConnection(){
        Connection con=CONNECTION_THREAD_LOCAL.get();
        if(con==null){
            try {
                con=DATA_SOURCE.getConnection();

            } catch (SQLException e) {
                logger.error("获取连接失败",e);
                throw  new RuntimeException(e);

            }finally {
                CONNECTION_THREAD_LOCAL.set(con);
            }
        }
        return con;
    }

    public static void executeSqlFile(String filepath){
        InputStream is=Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String line="";
        try {
            while ((line=br.readLine())!=null){
                executeUpdate(line);
            }
        } catch (IOException e) {
            logger.error("sql文件读取失败");
            e.printStackTrace();
        }
    }


    /**
     * 查询单个实体
     * **/
    public static <T> T queryEntity(Class<T> entityClass,String sql,Object... params){
            T entity;
            Connection con=getConnection();
        try {
            entity=QUERY_RUNNER.query(con,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e) {
            logger.error("查询实体失败:  实体类为"+entityClass.getName());
            throw new RuntimeException(e);
        }
        return entity;
    }
    /**
     * 查询实体列表
     * **/
    public static <T>List<T> queryEntityLsit(Class<T> entityClass,String sql,Object...params){
        List<T>entityList;
        Connection con=getConnection();
        try {
            entityList=QUERY_RUNNER.query(con,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            logger.error("查询实体集合失败:  实体类为"+entityClass.getName());
            throw new RuntimeException(e);
        }
        return entityList;
    }
    /**
     * 执行查询语句
     * **/
    public static List<Map<String,Object>> executQuery(String sql,Object...params) {
        List<Map<String, Object>> result = null;
        Connection con = getConnection();
        try {
            result = QUERY_RUNNER.query(con, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error("查询sql语句List<Map<String,Object>>集合失败:"+sql,e);
            throw new RuntimeException(e);
        }
        return result;
        }
    /**
     * 更新语句,增删改
     * **/
    public static int executeUpdate(String sql,Object...params){
        int rows=0;
        Connection con=getConnection();
        try {
            rows=QUERY_RUNNER.update(con,sql,params);
        } catch (SQLException e) {
            logger.error("增删改 sql语句执行失败:"+sql,e);
            throw new RuntimeException(e);
        }
        return rows;
    }
    public static int executeUpdate(String sql){
        return executeUpdate(sql,(Object[])null);
    }


    public static long executeInsert(String sql,Object...params){
        Long id;
        Connection con=getConnection();
        try {
            //update返回boolean  insert返回的是某个字段的值，比如主键，所有可以用来回写主键
            //当ScalarHandler参数为空或者Null,返回第一条记录的第一列数据
           id= QUERY_RUNNER.insert(con,sql,new ScalarHandler<Long>(),params);
        } catch (SQLException e) {
            logger.error("insert sql语句执行失败:"+sql,e);
            throw new RuntimeException(e);
        }
        return id;
    }

    public static long executeInsert(String sql){
        return executeInsert(sql,(Object[]) null);
    }


    /**
     * 更新实体
     *
     * **/
    public static <T>boolean updateEntity(Class<T> entityClass,long id,Map<String,Object>fieldMap){
        if(MapUtils.isEmpty(fieldMap)){
            logger.error("更新单个实体失败--》参数为空");
            return false;
        }
        String sql="update "+getTableName(entityClass)+" set ";
        StringBuilder sb=new StringBuilder();
        for(String key:fieldMap.keySet()){
            sb.append(key).append("=?,");
        }
        sql=sql+sb.substring(0,sb.lastIndexOf(","))+"  where"+" id=?";  //不过这里主键只能为id，应该用注解获取主键
        List<Object> paramList=new ArrayList<Object>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params=paramList.toArray();
        return executeUpdate(sql,params)==1;
    }

    /**
     * 插入单个实体
     * **/

    public static <T>boolean insertEntity(Class<T> entityClass,Map<String,Object>fieldMap){
        if(MapUtils.isEmpty(fieldMap)){
            logger.error("插入单个实体失败--》参数为空");
            return false;
        }
        String sql="insert into "+getTableName(entityClass);
        StringBuilder columns=new StringBuilder("(");
        StringBuilder values=new StringBuilder("(");
        for(String fieldName:fieldMap.keySet()){
            columns.append(fieldName).append(",");
            values.append("?,");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql=sql+columns+" values"+values;
        Object[] params=fieldMap.values().toArray();
        return executeUpdate(sql,params)==1;
    }
    /**
     * 删除单个实体
     * **/
    public static <T> boolean deleteEntity(Class<T> entityClass,long id){
        String sql="delete from "+getTableName(entityClass)+" where id=?";
        return  executeUpdate(sql,id)==1;
    }

    private static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName();
    }

    /**
     * 开始事务
     */
    public static void beginTransaction(){
        Connection con=getConnection();
        try {
            con.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("开始事务失败",e);
            throw new RuntimeException(e);
        }finally {
            CONNECTION_THREAD_LOCAL.set(con);
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction(){
        Connection con=getConnection();
        try {
            con.commit();
            con.close();
        } catch (SQLException e) {
            logger.error("提交事务失败",e);
            throw new RuntimeException(e);
        }finally {
            CONNECTION_THREAD_LOCAL.remove();
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction(){
        Connection con=getConnection();
        try {
            con.rollback();
            con.close();
        } catch (SQLException e) {
            logger.error("回滚事务失败",e);
            throw new RuntimeException(e);
        }finally {
            CONNECTION_THREAD_LOCAL.remove();
        }
    }
}
