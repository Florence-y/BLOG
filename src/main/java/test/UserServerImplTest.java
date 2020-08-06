package test;

import myinterface.UserServer;
import org.junit.Test;
import pojo.User;
import server.UserServerImpl;


/**
 * @author Florence
 * 用户服务测试类
 */
public class UserServerImplTest {
    @Test
    public void password(){
        UserServer a= new UserServerImpl();
        User b= new User();
        b.setAccount("2213576511");
        b.setPassword("qq123456");
        a.login(b);
    }
    @Test
    public void delete(){
        UserServer a = new UserServerImpl();
        System.out.println(a.deleteUser("13288522374","qq123456"));
    }
}