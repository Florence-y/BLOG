package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myinterface.UserServer;
import pojo.User;
import server.UserServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 获取用户信息
 * @author Florence
 */
@WebServlet("/GetUserInfServlet")
public class GetUserInfServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("获取用户收到访问");
        //设置编码防止乱码
        response.setContentType("application/json;charset=UTF-8");
        UserServer server =new UserServerImpl();
        //获取用户信息对象
        User user = server.getUserInf(request.getParameter("account"));
        //返回用户信息
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),user);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

}
