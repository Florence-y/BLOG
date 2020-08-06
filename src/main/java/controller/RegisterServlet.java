package controller;

import myinterface.UserServer;
import pojo.User;
import server.UserServerImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Florence
 * 登录服务
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("收到连接");
        //设置编码防止中文乱码
        response.setContentType("application/json; charset=utf-8");
        UserServer server = new UserServerImpl();
        //调用服务方法添加用户
        server.addUser(setUserInf(request));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.doPost(request, response);
    }

    /**
     * 设置用户信息
     * @param request 请求头
     * @return 设置用户信息
     */
    private User setUserInf (HttpServletRequest request){
        User user = new User();
        user.setName(request.getParameter("name"));
        user.setAccount(request.getParameter("account"));
        user.setPassword(request.getParameter("passWord"));
        return user;
    }
}
