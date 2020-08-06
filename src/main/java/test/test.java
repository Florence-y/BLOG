package test;

import dao.UserDaoImpl;
import myinterface.UserDao;
import org.junit.Test;
import pojo.User;
import until.C3P0Until;
import until.TimeUntil;
import java.util.Date;
import java.sql.*;

/**
 * @author Florence
 * 普通测试类
 */
public class test {
    @Test
    public void function(){
        Connection connection = C3P0Until.getConnection();

        try {
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("select * from sort");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            while(resultSet.next()){
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    @Test
    public void addUserTest(){
        UserDao a=new UserDaoImpl();
        User b = new User(12,"11111","131546465","515","45454","55");
        a.add(b);
    }
    @Test
    public void time(){
        java.util.Date date= new java.util.Date();
        System.out.println(date);
    }
    @Test
    public void time2(){
        System.out.println(TimeUntil.getStringTime(new Date()));
    }
    @Test
    public void C3p0Test(){
        C3P0Until.getConnection();
    }
}
