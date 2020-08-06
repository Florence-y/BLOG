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
 * 根据搜索查询文章
 * @author Florence
 */

@WebServlet("/FindArticlesByExploreServlet")
public class FindArticlesByExploreServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArticleServer server = new ArticleServerImpl();
        //设置编码，防止中文乱码
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //获取搜索文章的页面对象
        Page<Article> page = server.exploreArticles(request.getParameter("currentPage"),
                request.getParameter("pageSize"),request.getParameter("condition"));
        //返回页面对象给前端渲染
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),page);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
