package until;

import myinterface.InsertStrategy;
import myinterface.JdbcGetPojoStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Florence
 */
public class JdbcUtil {
    static Connection  connection;
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;


    public static int executeSql(String sql){
        try {
            connection=C3P0Until.getConnection();
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
     *
     * @param sql
     * @param strategyObject
     * @param <T>
     * @return
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

    public static <T> List<T> queryForJavaBeanAllData(String sql,JdbcGetPojoStrategy strategy){
        try {
            connection=C3P0Until.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            List<T> resultList= new LinkedList<>();
            while (resultSet.next()){
                resultList.add((T)strategy.strategy(resultSet));
            }
            return resultList;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    public static int update(){
        return 0;
    }

    public static int insertOneRow(String sql, InsertStrategy pojoStrategy, Object[] value) {
        try {
            connection=C3P0Until.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            pojoStrategy.strategy(value,preparedStatement);
            return preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
        return -1;
    }
}
