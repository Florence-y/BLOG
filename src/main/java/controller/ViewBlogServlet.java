package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import myinterface.ArticleServer;
import server.ArticleServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 查看blog的服务
 * @author Florence
 */
@WebServlet("/ViewBlogServlet")
public class ViewBlogServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("viewBlog被访问");
        //设置编码
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        //打印下名字和id
        System.out.println(request.getParameter("myName"));
        System.out.println(request.getParameter("id"));
        ArticleServer server = new ArticleServerImpl();
        //调用服务方法
        Map<String,Object> map = server.viewBlog(request.getParameter("id"),request.getParameter("myName"));
        ObjectMapper mapper = new ObjectMapper();
        //返回文章对象给前端渲染
        mapper.writeValue(response.getWriter(),map);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
