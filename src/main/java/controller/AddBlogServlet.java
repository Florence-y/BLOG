package controller;

import myinterface.ArticleServer;
import pojo.Article;
import server.ArticleServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Florence
 * 添加博客控制器
 */
@WebServlet("/AddBlogServlet")
public class AddBlogServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArticleServer server = new ArticleServerImpl();
        //自定义方法设置信息
        Article article = setArticleInf(request);
        //调用服务
        boolean isSuccessAddBlog =server.addBlog(request.getParameter("userName"),article);
        //是否成功
        if(isSuccessAddBlog){
            //返回消息
            response.getWriter().write("success");
            return;
        }
        //添加失败
        response.getWriter().write("fail");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

    /**
     * 设置文章信息对象
     * @param request 请求头
     */
    private Article setArticleInf(HttpServletRequest request) {
        Article article = new Article();
        //设置作者
        article.setAuthor(request.getParameter("userName"));
        //设置文章内容
        article.setContent(request.getParameter("content"));
        //设置文章标题
        article.setName(request.getParameter("title"));
        //获取标签（多个分类用&分隔开）
        String[] tag=request.getParameter("tagsDetail").split("＆");
        //加入到集合
        List<String> tags = new ArrayList<>(Arrays.asList(tag));
        article.setTags(tags);
        return article;
    }
}
