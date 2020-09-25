package test;

import org.junit.Test;
import pojo.User;
import until.ReflectUtil;

import java.lang.annotation.Target;

public class ReflictTest {
    @Test
    public void getIDFile(){
        ReflectUtil.getUpdateSql(new User(),new String[]{"name","account","id"});
    }
}
