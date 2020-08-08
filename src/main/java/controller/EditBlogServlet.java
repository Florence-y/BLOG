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
 * 编辑博客
 */
@WebServlet("/EditBlogServlet")
public class EditBlogServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArticleServer server =new ArticleServerImpl();
        //调用服务查看是否修改成功
        boolean isSuccess=server.editArticle(request.getParameter("id"),request.getParameter("title"),
                request.getParameter("content"),request.getParameter("tagsDetail"));
        System.out.println(request.getParameter("tagsDetail"));
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
