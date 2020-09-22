package test;

import dao.UserDaoImpl;
import myinterface.UserDao;
import org.junit.Test;
import pojo.User;
import until.C3P0Until;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Florence
 * 用户数据库操作测试类
 */
public class UserDaoImplTest {

    @Test
    public void delete() {
        UserDao a= new UserDaoImpl();
        User b= new User();
        b.setId(2);
        a.delete("1");
    }
    @Test
    public void change (){
        UserDao a = new UserDaoImpl();
        User b = new User(3,"wwww","11111","恰恰23456","女","21121");
        a.change(b);
    }
    @Test
    public void finUser(){
        try {
            String sql = "SELECT * FROM users WHERE name = ?";
            Connection connection= C3P0Until.getConnection();
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,"我我是");
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(resultSet.getString("name"));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
        }
    }
    @Test
    public void getUser(){
        UserDao dao = new UserDaoImpl();
        User a =dao.getUser("11111");
        System.out.println(a.toString());
    }

    @Test
    public void changByOneCondition(){
        UserDao a = new UserDaoImpl() ;
        a.changeOneByOneCondition("11111","face","../img/initFace.jpg");
    }

    @Test
    public void getAll(){
        UserDao a =new UserDaoImpl();
        System.out.println(a.getAllRow());
    }
    @Test
    public void common(){
        UserDao a = new UserDaoImpl();
        a.insertOneRow(new User(),0,"sss","13144455","dadadadad","女","www.baidu.com");
    }
}