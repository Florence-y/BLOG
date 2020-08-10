package myinterface;

import pojo.Comment;

import java.util.List;

/**
 * @author Florence
 * 评论操作类接口
 */
public interface CommentDao {
    /**
     * 添加评论
     * @param comment 评论
     * @return 评论id
     */
    int add(Comment comment);

    /**
     * 根据文章id获取文章评论
     * @param articleId 文章评论
     * @return 文章评论集合
     */
    List<Comment> getCommentOfArticle(int articleId);

    /**
     * 修改点赞数
     * @param commentId 评论的id
     * @param nowAgreeCount 当前评论数
     * @return 当前点赞数
     */
    String changeAgreeCount(int commentId ,int nowAgreeCount);

    /**
     * 根据文章id删除评论
     * @param id 文章id
     * @return 是否成功
     */
    boolean deleteByArticleId(int id);
}
