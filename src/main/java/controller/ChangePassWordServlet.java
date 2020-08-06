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
 * 修改密码
 */
@WebServlet("/ChangePassWordServlet")
public class ChangePassWordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置编码，防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        UserServer server =new UserServerImpl();
        //调用服务方法修改密码
        boolean isSuccess= server.changeUserPassWord(request.getParameter("account"),request.getParameter("nowPassWord"),request.getParameter("wantToChangePassWord"));
        //修改成功
        if(isSuccess){
            response.getWriter().write("success");
            return;
        }
        //修改失败
        response.getWriter().write("fail");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
