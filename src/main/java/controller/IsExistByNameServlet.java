package controller;

import myinterface.UserServer;
import server.UserServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Florence
 * 用户是否存在
 */
@WebServlet("/IsExistByNameServlet")
public class IsExistByNameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置编码防止乱码
        response.setContentType("text/html; charset=utf-8");
        //获取用户名
        String userName = request.getParameter("message");
        System.out.println(userName);
        UserServer server = new UserServerImpl();
        //用户名已经存在
        if(server.isExist(userName)){
            response.getWriter().write("yes");
        }
        //用户名不存在，可用
        else {
            response.getWriter().write("no");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
