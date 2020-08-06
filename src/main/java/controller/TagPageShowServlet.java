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
 * 展示某个分类页面
 */
@WebServlet("/TagPageShowServlet")
public class TagPageShowServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArticleServer server = new ArticleServerImpl();
        //设置编码
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //调用服务获取分类页面
        Page<Article> tagPage = server.getTagPage(request.getParameter("currentPage"),
                request.getParameter("pageSize"), request.getParameter("tag"));
        //返回得到的页面对象给前端渲染
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(),tagPage);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
