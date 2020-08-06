package dao;

import myinterface.CommentDao;
import myinterface.UserDao;
import pojo.Comment;
import until.C3P0Until;
import until.MySql;
import until.TimeUntil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Florence
 * 评论数据库操作类实现类
 */
public class CommentDaoImpl implements CommentDao {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    UserDao userDao;
    Comment comment;

    /**
     * 添加评论
     * @param comment 评论
     * @return 评论id
     */
    @Override
    public int add(Comment comment) {
        try {
            //获取当前日期
            Date date = new Date();
            int nowArticleId=0;
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.INSERT_COMMENT.toString(), Statement.RETURN_GENERATED_KEYS);
            //设置默认id
            preparedStatement.setInt(1,0);
            //设置评论者
            preparedStatement.setString(2,comment.getUserName());
            //设置评论内容
            preparedStatement.setString(3,comment.getContent());
            //设置时间
            preparedStatement.setDate(4,new java.sql.Date(date.getTime()));
            //设置点赞数
            preparedStatement.setInt(5,0);
            //设置所评论的文章id
            preparedStatement.setInt(6,comment.getArticleId());
            //进行添加评论
            preparedStatement.executeUpdate();
            //获取刚插入对象id结果集
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            //获取刚插入的对象ID
            if(generatedKeys.next()){
                nowArticleId=generatedKeys.getInt(1);
            }
            System.out.println(nowArticleId);
            return nowArticleId;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //不成功
        return 0;
    }

    /**
     * 根据文章id获取文章评论
     * @param articleId 文章评论
     * @return 文章评论集合
     */
    @Override
    public List<Comment> getCommentOfArticle(int articleId) {
        try {
            List<Comment> comments = new ArrayList<>();
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_COMMENT_BY_ARTICLE_ID.toString());
            //设置文章id
            preparedStatement.setInt(1,articleId);
            //执行查询
            resultSet=preparedStatement.executeQuery();
            //循环封装评论对象然后加入到集合中
            while (resultSet.next()){
                //自定义封装方法设置评论信息
                comments.add(setCommentInf(resultSet));
            }
            //返回封装的评论对象集合
            return comments;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //获取评论集合失败
        return null;
    }
    /**
     * 修改点赞数
     * @param commentId 评论的id
     * @param nowAgreeCount 要设置的点赞数
     * @return 当前点赞数
     */
    @Override
    public String changeAgreeCount(int commentId, int nowAgreeCount) {
        try {
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.UPDATE_COMMENT_AGREEMENT.toString());
            //设置要设置的点赞数目
            preparedStatement.setInt(1,nowAgreeCount);
            //设置评论id
            preparedStatement.setInt(2,commentId);
            //进行更新
            preparedStatement.executeUpdate();
            //返回点赞数
            return Integer.toString(nowAgreeCount);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return "-1";
    }



    /**
     * 自定义获取文章信息方法
     * @param resultSet 要封装的结果集
     * @return 返回封装好信息的评论对象
     */
    private Comment setCommentInf(ResultSet resultSet) {
        try {
            //用户操作对象用于获取用户头像
            userDao = new UserDaoImpl();
            //评论对象
            comment = new Comment();
            String userName =resultSet.getString("userName");
            //设置评论id
            comment.setId(resultSet.getInt("id"));
            //设置评论者
            comment.setUserName(userName);
            //设置评论内容
            comment.setContent(resultSet.getString("content"));
            //设置日期
            comment.setDate(TimeUntil.getStringTime(resultSet.getDate("commentDate")));
            //设置点赞数目
            comment.setAgreeCount(resultSet.getInt("agreeCount"));
            //设置文章id
            comment.setArticleId(resultSet.getInt("articleId"));
            //设置用户头像
            comment.setUserFace(userDao.getUserByName(userName).getFace());
            //返回封装好的评论对象
            return comment;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        //获取失败
        return null;
    }

}
