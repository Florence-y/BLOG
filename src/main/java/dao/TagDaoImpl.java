package dao;

import myinterface.SortLinkArticleDao;
import myinterface.TagDao;
import until.C3P0Until;
import until.MySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Florence
 * 分类与文章连接表数据库操作实现类
 */
public class TagDaoImpl implements TagDao {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    /**
     * 添加某一篇文章所属的分类（这里使用的是中间表来添加分类，否则会导致数据浪费）ps:这里只是添加一次
     * @param idOfArticle 文章的id
     * @param idOfTag 分类的id
     */
    @Override
    public void addArticleTag(int idOfArticle, int idOfTag) {
        try {
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.INSERT_SORT_LINK_ARTICLE.toString());
            //设置文章id
            preparedStatement.setInt(1,idOfArticle);
            //设置分类的id
            preparedStatement.setInt(2,idOfTag);
            //在分类连接文章的表中建立关系
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
    }



    /**
     * 根据传入的分类具体类型，来建立文章id与分类id之间的联系
     * @param articleId 文章id
     * @param tags 分类集合（根据这些集合的值（比如“算法”，“后端”），来设置文章id和分类id之间的联系）
     * @return 是否成功
     */
    @Override
    public boolean addArticleIdLinkTagId(int articleId, List<String> tags) {
        //分类连接文章的表的对象
        SortLinkArticleDao sortLinkArticleDao = new SortLinkArticleDaoImpl();
        for(String tagName:tags){
            //得到文章的id，并且用分类属性具体的值去寻找分类的id，然后添加分类id与文章id之间联系
            addArticleTag(articleId,sortLinkArticleDao.searchTagIdByTagName(tagName));
        }
        return true;
    }

    /**
     * 根据文章id给文章对象添加分类(查询文章分类连接表，得到分类的id，在向根据分类id去分类表中查询)
     * @param id 文章的id
     * @return 文章分类list
     */
    @Override
    public List<String> getArticleAllTags(int id) {
        try {
            //分类属性集合，用于页面展示
            List<String> tags = new ArrayList<>();
            //分类连接文章的表的对象
            SortLinkArticleDao sortLinkArticleDao=new SortLinkArticleDaoImpl();
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_TAG_BY_ARTICLE_ID.toString());
            //设置文章id
            preparedStatement.setInt(1,id);
            //进行查询，找到该文章有哪些分类
            resultSet = preparedStatement.executeQuery();
            //查询文章分类连接表，得到这篇文章有的分类的id，在向根据分类id去分类表中查询具体的分类内容
            while (resultSet.next()){
                tags.add(sortLinkArticleDao.searchTagNameByTagId(resultSet.getInt("sortId")));
            }
            //返回包含该文章所有分类的集合
            return tags;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        System.out.println("给单独一个文章对象添加分类出现错误");
        return null;
    }

    /**
     * 根据分类名得到该分类总共有多少文章
     * @param tag 分类名
     * @return 文章数量
     */
    @Override
    public int getSomeTagCount(String tag) {
        try {
            //分类连接文章的表的对象
            SortLinkArticleDao sortLinkArticleDao = new SortLinkArticleDaoImpl();
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_ARTICLE_COUNT_BY_TAG.toString());
            //首先根据分类的值去查找分类的id，然后根据分类的id在分类连接文章的表中进行条件查询属于该分类的有多少文章
            preparedStatement.setInt(1,sortLinkArticleDao.searchTagIdByTagName(tag));
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                //返回属于该分类的文章数目
                return resultSet.getInt(1);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //获取属于该分类的文章总数失败
        return -1;
    }

    /**
     * 根据分类名查询属于该分类的所有文章id
     * @param tag 分类名
     * @return 文章id集合
     */
    @Override
    public List<Integer> getSomeTagArticlesId(String tag) {
        try {
            //分类连接文章的表的对象
            SortLinkArticleDao sortLinkArticleDao = new SortLinkArticleDaoImpl();
            //文章id集合
            List<Integer> list = new ArrayList<>();
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_ARTICLE_ID_BY_TAG.toString());
            //根据分类名查询分类具体的id,然后根据分类的id，去查询属于该分类的文章id的结果集
            preparedStatement.setInt(1,sortLinkArticleDao.searchTagIdByTagName(tag));
            resultSet=preparedStatement.executeQuery();
            //根据结果集获取id
            while (resultSet.next()){
                list.add(resultSet.getInt("articleId"));
            }
            //返回属于该分类的所有文章id
            return list;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //获取某个分类所有的文章id失败
        return null;
    }

    /**
     * 删除文章现有分类
     * @param articleId 要删除文章的id
     * @return 是否成功
     */
    @Override
    public boolean deleteArticleSort(int articleId) {
        try {
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.DELETE_ARTICLE_SORT.toString());
            //设置文章id
            preparedStatement.setInt(1,articleId);
            //执行删除
            preparedStatement.executeUpdate();
            //删除成功
            return true;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
        //删除失败
        return false;
    }


}
