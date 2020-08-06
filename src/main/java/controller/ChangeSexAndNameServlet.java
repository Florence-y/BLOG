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
 * 改变用户性别和名字
 */
@WebServlet("/ChangeSexAndNameServlet")
public class ChangeSexAndNameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置编码，防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        UserServer server= new UserServerImpl();
        //调用服务方法进行修改
        server.changUserSexAndName(request.getParameter("account"),request.getParameter("sex"),request.getParameter("name"));
        //修改成功
        response.getWriter().write("success");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
