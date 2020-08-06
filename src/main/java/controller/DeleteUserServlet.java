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
 * 用户注销
 */
@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserServer server = new UserServerImpl();
        boolean status = server.deleteUser(request.getParameter("account"),request.getParameter("passWord"));
        //注销成功
        if(status){
            response.getWriter().write("success");
            return;
        }
        //注销失败
        response.getWriter().write("fail");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
