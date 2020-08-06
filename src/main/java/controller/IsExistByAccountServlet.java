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
@WebServlet("/IsExistByAccountServlet")
public class IsExistByAccountServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置编码防止乱码
        response.setContentType("text/html;charset=utf-8");
        String account = request.getParameter("message");
        System.out.println(account);
        UserServer server = new UserServerImpl();
        //账号已经存在
        if(server.isExistByAccount(account)){
            response.getWriter().write("yes");
        }
        //账号不存在
        else {
            response.getWriter().write("no");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
