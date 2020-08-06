package controller;

import myinterface.ArticleServer;
import pojo.Comment;
import server.ArticleServerImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Florence
 * 添加评论
 */
@WebServlet("/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //设置编码，防止中文乱码
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        ArticleServer server = new ArticleServerImpl();
        //调用服务添加评论
        int commentId=server.addComment(setCommentInf(request));
        //返回评论id
        response.getWriter().write(Integer.toString(commentId));
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

    /**
     * 自定义方法设置评论信息
     * @param request 请求头
     * @return 评论对象
     */
    private Comment setCommentInf(HttpServletRequest request) {
        Comment comment = new Comment();
        //设置用户名
        comment.setUserName(request.getParameter("userName"));
        //设置评论内容
        comment.setContent(request.getParameter("content"));
        //设置所属文章id
        comment.setArticleId(Integer.parseInt(request.getParameter("articleId")));
        return comment;
    }
}
