package until;

import myinterface.InsertStrategy;
import myinterface.JdbcGetPojoStrategy;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Florence
 */
public class JdbcUtil {
    static Connection  connection;
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;

    /**
     * 执行一条sql语句（一般用来执行DDL语句）
     * @param sql sql语句
     * @return 影响第几行
     */
    public static int executeSql(String sql){
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            preparedStatement=connection.prepareStatement(sql);
            return preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
        return -1;
    }

    /**
     * 根据可变参数
     * @param sql sql语句（更新或者删除语句）
     * @param value 具体参数值
     * @return 影响第几行了
     */
    public static int executeSqlByPrepareStatement(String sql,Object... value){
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            preparedStatement=connection.prepareStatement(sql);
            setPrepareStatementUnknown(preparedStatement,value);
            return preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
        return -1;
    }

    /**
     * 根据可变参数设置未知值
     * @param preparedStatement 预设定语句
     * @param value 参数值
     */
    private static void setPrepareStatementUnknown(PreparedStatement preparedStatement, Object[] value) {
        try {
            for (int i=0;i<value.length;i++) {
                if (value[i].getClass().isAssignableFrom(Integer.class)) {
                    preparedStatement.setInt(i + 1, (Integer) value[i]);
                }
                else if (value[i].getClass().isAssignableFrom(String.class)){
                    preparedStatement.setString(i+1,(String)value[i]);
                }
                else if (value[i].getClass().isAssignableFrom(Date.class)){
                    preparedStatement.setDate(i+1,(Date)value[i]);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static int update(String sql,Object... value){
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            preparedStatement=connection.prepareStatement(sql);
            setPrepareStatementUnknown(preparedStatement,value);
            return preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
        return -1;
    }
    /**
     *
     * @param sql sql语句
     * @param strategyObject 获取设置对象的策略
     * @param <T> 泛型参数
     * @return 返回一个javabean对象
     */
    public static<T> T queryForJavaBean(String sql, JdbcGetPojoStrategy strategyObject){
        try {
            connection=C3P0Until.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            if (resultSet.next()) {
                return (T) strategyObject.strategy(resultSet);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 根据sql查询（多条数据）然后进行封装
     * @param sql sql语句
     * @param pojoStrategy 获取对象的策略
     * @param <T> 泛型类型
     * @return 返回的结果集合
     */
    public static <T> List<T> queryForJavaBeanAllData(String sql,JdbcGetPojoStrategy pojoStrategy){
        try {
            connection=C3P0Until.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            List<T> resultList= new LinkedList<>();
            while (resultSet.next()){
                resultList.add((T)pojoStrategy.strategy(resultSet));
            }
            return resultList;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 根据sql获取ResultSet
     * @param sql sql语句
     * @param value 值
     * @return 返回获取到的结果集
     */
    public static ResultSet queryForGetResultSet(Connection con,String sql,Object... value){
        try {
            connection=con;
            assert connection != null;
            preparedStatement=connection.prepareStatement(sql);
            setPrepareStatementUnknown(preparedStatement,value);
            resultSet=preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /**
     * 根据sql插入一个一条数据
     * @param sql 插入语句
     * @param pojoStrategy 插入的策略
     * @param value 参数的值
     * @return 返回的行数
     */
    public static int insertOneRow(String sql, InsertStrategy pojoStrategy, Object[] value) {
        try {
            int nowArticleId=0;
            connection=C3P0Until.getConnection();
            assert connection != null;
            preparedStatement=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            pojoStrategy.strategy(value,preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            //获取刚插入的对象ID
            if(generatedKeys.next()){
                nowArticleId=generatedKeys.getInt(1);
            }
            System.out.println(nowArticleId);
            return nowArticleId;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
        return -1;
    }
}
