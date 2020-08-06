package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myinterface.ArticleServer;
import pojo.Article;
import server.ArticleServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Florence
 * 根据文章id获取文章信息
 */
@WebServlet("/GetArticleByIdServlet")
public class GetArticleByIdServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置编码，防止中文乱码
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ArticleServer server = new ArticleServerImpl();
        //调用服务方法去文章
        Article article = server.getArticleByArticleId(request.getParameter("id"));
        //返回文章信息
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),article);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
