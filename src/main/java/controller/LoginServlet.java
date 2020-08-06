package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myinterface.UserServer;
import pojo.User;
import server.UserServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Florence
 * 登录管理
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置编码，防止中文乱码
            response.setContentType("application/json;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
            ObjectMapper mapper=new ObjectMapper();
            UserServer server = new UserServerImpl();
            //新建一个map做json对象
            Map<String,Object> map = new HashMap<>(3);
            //获取登录状况
            User user =server.login(setUserInf(request));
            //用户不存在
            if(user==null){
                map.put("status","notExist");
                mapper.writeValue(response.getWriter(),map);
                return;
            }
            //账号密码不对
            if(-1==user.getId()){
                map.put("status","wrongPassWord");
                mapper.writeValue(response.getWriter(),map);
                return;
            }
            //登录成功(封装方法)
            successLogin(user,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request, response);
    }
    /**
     * 设置用户信息
     * @param request 请求头
     * @return 设置信息完成的用户
     */
    private User setUserInf (HttpServletRequest request){
        User user = new User();
        user.setAccount(request.getParameter("account"));
        user.setPassword(request.getParameter("passWord"));
        return user;
    }

    /**
     * 成功登录返回信息的方法
     * @param user 登录成功的用户
     * @param response 响应头
     */
    private void successLogin(User user,HttpServletResponse response){
        try {
            //成功登录
            ObjectMapper mapper=new ObjectMapper();
            Map<String,Object> map = new HashMap<>(3);
            map.put("status","success");
            map.put("face",user.getFace());
            //发送登录cookie，用来确认权限
            Cookie cookie1 = new Cookie("login",user.getName());
            Cookie cookie2 = new Cookie("account",user.getAccount());
            //设置浏览器退出5分钟内cookie还存在
//            cookie1.setMaxAge(60*5);
//            cookie2.setMaxAge(60*5);
            cookie1.setPath("/");
            cookie2.setPath("/");
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            mapper.writeValue(response.getWriter(),map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
