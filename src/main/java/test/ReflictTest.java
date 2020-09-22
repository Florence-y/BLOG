package test;

import org.junit.Test;
import pojo.User;
import until.ReflectUtil;

import java.lang.annotation.Target;

public class ReflictTest {
    @Test
    public void getIDFile(){
        System.out.println(ReflectUtil.getIdField(new User()));
    }
}
