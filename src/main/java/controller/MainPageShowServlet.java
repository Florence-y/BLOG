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
 * 主页面分页展示
 */
@WebServlet("/MainPageShowServlet")
public class MainPageShowServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("受到消息主页面");
        //设置编码，防止中文乱码
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //声明服务对象
        ArticleServer server = new ArticleServerImpl();
        //当前页码
        System.out.println(request.getParameter("currentPage"));
        //获取页面对象
        Page<Article> page =server.getMainPage(request.getParameter("currentPage"),request.getParameter("pageSize"));
        //返回页面对象给前端渲染
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(page));
        mapper.writeValue(response.getWriter(),page);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
