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
 * 删除文章
 */
@WebServlet("/DeleteArticleServlet")
public class DeleteArticleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArticleServer server = new ArticleServerImpl();
        //调用服务去删除文章
        boolean isSuccess = server.deleteArticle(request.getParameter("id"));
        //删除成功
        if(isSuccess){
            response.getWriter().write("success");
            return;
        }
        //删除失败
        response.getWriter().write("fail");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
