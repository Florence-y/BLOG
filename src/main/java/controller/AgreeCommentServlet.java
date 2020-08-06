package controller;

import myinterface.ArticleServer;
import server.ArticleServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Florence
 * 点赞评论
 */
@WebServlet("/AgreeCommentServlet")
public class AgreeCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置编码，防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ArticleServer server = new ArticleServerImpl();
        //调用服务进行点赞功能
        String agreeCount =server.agreeComment(request.getParameter("commentId"),request.getParameter("nowAgree"));
        response.getWriter().write(agreeCount);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
