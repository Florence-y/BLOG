package dao;

import myinterface.BaseDao;
import myinterface.JdbcGetPojoStrategy;
import until.JdbcUtil;
import until.ReflectUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Florence
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    /**
     * 获取 T 表的名字
     * @return 数据库表的名字
     */
    public abstract String getTableName();

    /**
     * 获取某一列名
     * @return 返回列名
     */
    public abstract String getTableIdField();

    /**
     * 获取相应的jdbc策略
     * @return
     */
    public abstract JdbcGetPojoStrategy getStrategy();
    /**
     * 根据id获取一个对象
     * @param id 要求的id
     * @return
     */
    @Override
    public T selectById(int id) {
        String sql = MessageFormat.format("select * from {0} where {1} = {3}",getTableName(),getTableIdField(),id);
        return JdbcUtil.queryForJavaBean(sql,getStrategy());
    }

    /**
     * 根据id更新一个对象
     * @param pojo 实体类对象
     * @return 返回更新到几行
     */
    @Override
    public int updateOneColById(T pojo) {
        return 0;
    }

    /**
     * 插入一行数据
     * @param pojo 实体类对象
     * @return 影响到第几行
     */
    @Override
    public int insertOneRow(T pojo) {
        return 0;
    }

    /**
     * 根据id删除一条数据
     * @param id id
     * @return
     */
    @Override
    public int deleteById(T pojo,int id) {
        String sql= MessageFormat.format("DELETE FROM {0} WHERE {1} = {2}",getTableName(), ReflectUtil.getIdField(pojo),id);
        return JdbcUtil.executeSql(sql);
    }

    @Override
    public List<T> getAllRow(){
        String sql =MessageFormat.format("select * from {0}",getTableName());
        List<T> resultList =JdbcUtil.queryForJavaBeanAllData(sql,getStrategy());
        return resultList;
    }
}
