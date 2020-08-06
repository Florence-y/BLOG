package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myinterface.ArticleServer;
import pojo.Article;
import pojo.Page;
import server.ArticleServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Florence
 * 根据用户名获取文章
 */
@WebServlet("/GetArticleByNameServlet")
public class GetArticleByNameServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置编码，防止中文乱码
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ArticleServer server = new ArticleServerImpl();
        //调用服务方法获取我的博客信息
        Page<Article> page = server.getPersonalArticles(request.getParameter("currentPage"),
                request.getParameter("pageSize"),request.getParameter("userName"));
        ObjectMapper mapper = new ObjectMapper();
        //获取我的博客成功
        if (page!=null){
            mapper.writeValue(response.getWriter(),page);
            return;
        }
        //获取我的博客失败
        System.out.println("获取管理文章失败");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
