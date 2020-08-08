package myinterface;

import pojo.Article;
import pojo.Comment;
import pojo.Page;

import java.util.Map;

/**
 * @author Florence
 * 文章服务类接口
 */
public interface ArticleServer {
    /**
     * 添加博客服务
     * @param userName 用户名
     * @param article 博客对象
     * @return 是否成功
     */
    boolean addBlog(String userName, Article article);

    /**
     * 获取主界面的分类对象
     * @param currentPage 现在的页数
     * @param pageSize 每页的大小
     * @return 页面对象
     */
    Page<Article> getMainPage(String currentPage, String pageSize);

    /**
     * 查看一篇文章
     * @param articleId 文章id
     * @param theViewerName 查看的名字，可以为无
     * @return 返回一个包含文章页面内容的map
     */
    Map<String,Object> viewBlog(String articleId,String theViewerName);

    /**
     * 获取分类页面
     * @param currentPage 当前页码
     * @param pageSize 一页多少文章数量
     * @param tag 要的分类
     * @return 返回的页面对象
     */
    Page<Article> getTagPage(String currentPage,String pageSize,String tag);

    /**
     * 根据搜索条件获取文章
     * @param currentPage 当前页码
     * @param pageSize 一页多少文章数量
     * @param condition 提供的条件
     * @return 返回的页面对象
     */
    Page<Article> exploreArticles(String currentPage, String pageSize, String condition);

    /**
     * 根据用户名获取用户管理文章
     * @param currentPage 当前页码
     * @param size 当前页面文章多少
     * @param userName 用户名
     * @return 返回管理文章对象
     */
    Page<Article> getPersonalArticles(String currentPage,String size,String userName);

    /**
     * 根据文章id来获取文章信息
     * @param id 文章id
     * @return 文章信息对象
     */
    Article getArticleByArticleId(String id);

    /**
     * 修改文章
     * @param id 文章id
     * @param title 标题
     * @param content 内容
     * @param tagsDetail 分类
     * @return 返回是否成修改成功
     */
    boolean editArticle(String id,String title, String content, String tagsDetail);

    /**
     * 删除文章
     * @param articleId 文章id
     * @return 是否删除成功
     */
    boolean deleteArticle(String articleId);

    /**
     * 添加评论
     * @param comment 评论对象
     * @return 评论的对象
     */
    Comment addComment(Comment comment);

    /**
     * 点赞功能
     * @param commentId 评论的id
     * @param nowAgreeCount 当前评论数
     * @return 当前点赞数
     */
    String agreeComment(String commentId,String nowAgreeCount);
}
