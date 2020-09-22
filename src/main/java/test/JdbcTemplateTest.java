package test;

import dao.UserDaoImpl;
import dao.strategy.UserJdbcStrategy;

import myinterface.BaseDao;
import org.junit.Test;
import pojo.User;
import until.JdbcUtil;

import java.text.MessageFormat;

/**
 * @author Florence
 */
public class JdbcTemplateTest {
    @Test
    public void queryForObject(){
        System.out.println(JdbcUtil.queryForJavaBean("select * from users where id=3",new UserJdbcStrategy()));
        System.out.println(MessageFormat.format("select * from users where id={0} {1} {2}",new Integer[]{1,2,3}));
    }
    @Test
    public void baseDao(){
        BaseDao dao = new UserDaoImpl();
        dao.insertOneRow(new User(),-1,"2213576512","www","123456","男","../dadad/wwafafa");
    }
}
