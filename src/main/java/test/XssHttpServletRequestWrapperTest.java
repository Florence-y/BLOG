package test;

import filter.XssHttpServletRequestWrapper;
import org.junit.Test;


/**
 * @author Florence
 * xss防御测试类
 */
public class XssHttpServletRequestWrapperTest {
    @Test
    public void xssDefender(){
        System.out.println(XssHttpServletRequestWrapper.xssEncode("<script>alert(6666)</script> <h1>dada</h1>"));
    }
}