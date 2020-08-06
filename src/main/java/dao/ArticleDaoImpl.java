package dao;

import myinterface.ArticleDao;
import myinterface.TagDao;
import pojo.Article;
import until.C3P0Until;
import until.MySql;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Florence
 * 文章数据库操作类
 */
public class ArticleDaoImpl implements ArticleDao {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    /**
     * 添加文章
     * @param userName 用户名字
     * @param article 文章信息
     * @return 刚插入文章的id
     */
    @Override
    public int add(String userName, Article article) {
        try {
            //当前这条记录插入的ID
            int nowArticleId = 0;
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明插入预备语句,并且获取插入时候的id
            preparedStatement=connection.prepareStatement(MySql.INSERT_ARTICLE.toString(), Statement.RETURN_GENERATED_KEYS);
            //设置id
            preparedStatement.setInt(1,0);
            //设置文章名
            preparedStatement.setString(2,article.getName());
            //设置文章内容
            preparedStatement.setString(3,article.getContent());
            //设置作者名字
            preparedStatement.setString(4,userName);
            //执行更新语句
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
            C3P0Until.close(connection,preparedStatement);
        }
        return 0;
    }

    /**
     * 获取文章总数
     * @return 总数多少
     */
    @Override
    public int getAllCount() {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_ALL_ARTICLES_COUNT.toString());
            //执行查询
            resultSet = preparedStatement.executeQuery();
            //获取总数
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    /**
     * 获取当前页的文章数据(博文总览)
     * @param start 开始的地方
     * @param pageSize 要展示多少条内容
     * @return 页面展示的文章数据
     */
    @Override
    public List<Article> getPageData(int start, int pageSize) {
        try {
            //要展示的文章集合
            List<Article> dataList = new ArrayList<>();
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_ARTICLE_BY_PAGE.toString());
            //设置从哪里开始获取文章
            preparedStatement.setInt(1,start);
            //设置获取多少篇
            preparedStatement.setInt(2,pageSize);
            //执行查询
            resultSet = preparedStatement.executeQuery();
            //循环添加文章
            while (resultSet.next()){
                //自定义封装方法获取文章
                dataList.add(setArticleInf(resultSet));
            }
            //获取文章集合成功
            return dataList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
            System.out.println("清除成功");
        }
        //获取文章集合失败
        return null;
    }

    /**
     * 根据id查询某项想要获取的信息
     * @param id id
     * @param wantToGet 想要获取的信息
     * @return 获取到的信息
     */
    @Override
    public String getSomethingById(int id, String wantToGet) {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_SOMETHING_BY_ID.toString());
            //设置文章id
            preparedStatement.setInt(1,id);
            //执行查询
            resultSet=preparedStatement.executeQuery();
            //获取某项信息（wantToGet）就是你想获得信息的列名
            if(resultSet.next()){
                return resultSet.getString(wantToGet);
            }
            //获取失败或者无记录
            return null;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 根据传入的id集合获取这些对象（分类页面）
     * @param start 开始搜索的地方
     * @param pageSize 每一页页面的大小
     * @param wantToGetArticleId 想要获取文章的id集合（根据中间表查询得来）
     * @return 返回的文章页面对象
     */
    @Override
    public List<Article> getPageDataByTag(int start, int pageSize, List<Integer> wantToGetArticleId) {
        try {
            //包含页面要展示的文章的list集合
            List<Article> dataList = new ArrayList<>();
            //用来计算查到第几篇文章了
            int count=0;
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_SOMETHING_BY_ID.toString());
            for (int id:wantToGetArticleId){
                //数目达到需要查询的数目
                if(count-start==pageSize){
                    break;
                }
                //到达开始添加数据的的地方
                if(count>=start){
                    preparedStatement.setInt(1,id);
                    resultSet=preparedStatement.executeQuery();
                    if(resultSet.next()) {
                        dataList.add(setArticleInf(resultSet));
                    }
                }
                //记录查询到第几篇文章（这也是我们开始获取数据的依据）
                count++;
            }
            return dataList;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 根据条件获取符合条件的文章总数
     * @param condition 条件
     * @return 文章总数
     */
    @Override
    public int getSomeConditionCount(String condition) {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_SOME_ARTICLES_COUNT_BY_CONDITION.toString());
            //设置标题查询条件
            preparedStatement.setString(1,"%"+condition+"%");
            //设置作者查询条件
            preparedStatement.setString(2,"%"+condition+"%");
            //执行查询
            resultSet = preparedStatement.executeQuery();
            //获取总数
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        System.out.println("获取失败");
        return 0;
    }

    /**
     * 获取具体的文章数据（搜索文章）
     * @param start 开始查询的地方
     * @param pageSize 页面大小
     * @param condition 条件
     * @return 页面展示对象
     */
    @Override
    public List<Article> getPageDataByCondition(int start, int pageSize, String condition) {
        try {
            List<Article> dataList = new ArrayList<>();
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_ARTICLES_BY_PAGE_AND_CONDITION.toString());
            //设置文章名字查询条件
            preparedStatement.setString(1,"%"+condition+"%");
            //设置作者名字查询条件
            preparedStatement.setString(2,"%"+condition+"%");
            //设置开始查询的位置
            preparedStatement.setInt(3,start);
            //设置查询的数量
            preparedStatement.setInt(4,pageSize);
            //执行查询
            resultSet = preparedStatement.executeQuery();
            //循环添加文章
            while (resultSet.next()){
                //自定义封装方法获取文章
                dataList.add(setArticleInf(resultSet));
            }
            return dataList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
            System.out.println("清除成功");
        }
        System.out.println("获取文章失败");
        return null;
    }

    /**
     * 根据用户名查找属于该用户的文章总数
     * @param userName 用户名
     * @return 文章总数
     */
    @Override
    public int getArticleCountByUserName(String userName) {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_ARTICLE_COUNT_BY_USERNAME.toString());
            //设置用户名
            preparedStatement.setString(1,userName);
            //执行查询
            resultSet = preparedStatement.executeQuery();
            //获取总数
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return 0;
    }

    /**
     * 根据用户名查找属于该用户的文章对象
     * @param start 当前页码
     * @param pageSize 一页文章数目多少
     * @param userName 用户名
     * @return 文章数据集合
     */
    @Override
    public List<Article> getPageDataByUserName(int start, int pageSize, String userName) {
        try {
            //要展示的文章集合
            List<Article> dataList = new ArrayList<>();
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_ARTICLE_BY_PAGE_USERNAME.toString());
            //设置作者名称
            preparedStatement.setString(1,userName);
            //设置从哪里开始获取文章
            preparedStatement.setInt(2,start);
            //设置获取多少篇
            preparedStatement.setInt(3,pageSize);
            //执行查询
            resultSet = preparedStatement.executeQuery();
            //循环添加文章
            while (resultSet.next()){
                //自定义封装方法获取文章
                dataList.add(setArticleInf(resultSet));
            }
            //获取文章集合成功
            return dataList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
            System.out.println("清除成功");
        }
        //获取文章集合失败
        return null;
    }

    /**
     * 根据文章id获取文章对象
     * @param articleId 文章id
     * @return 文章对象
     */
    @Override
    public Article getArticleByArticleId(int articleId) {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_SOMETHING_BY_ID.toString());
            //设置文章id
            preparedStatement.setInt(1,articleId);
            //执行查询文章
            resultSet = preparedStatement.executeQuery();
            //获取文章
            if(resultSet.next()){
                return setArticleInf(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 编辑博客
     * @param articleId 文章id
     * @param title 标题
     * @param content 内容
     * @return 是否成功
     */
    @Override
    public boolean editBlog(int articleId, String title, String content) {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.UPDATE_ARTICLE.toString());
            //设置要修改的标题
            preparedStatement.setString(1,title);
            //设置要修改的内容
            preparedStatement.setString(2,content);
            //设置要修改的文章id
            preparedStatement.setInt(3,articleId);
            //执行修改
            return  preparedStatement.executeUpdate()!=0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //修改失败
        return false;
    }

    /**
     * 删除文章
     * @param articleId 文章id
     * @return 是否成功
     */
    @Override
    public boolean delete(int articleId) {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.DELETE_ARTICLE.toString());
            //设置要删除的文章id
            preparedStatement.setInt(1,articleId);
            //执行删除
            return  preparedStatement.executeUpdate()!=0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //删除失败
        return false;
    }

    /**
     * 自定义封装方法：根据一个resultSet将数据封装成一个list 返回
     * @param resultSet 文章查询结果集
     * @return 返回信息设置好的文章实体对象
     */
    private Article setArticleInf(ResultSet resultSet){
        try {
            //用来设置分类的
            TagDao tagDao=new TagDaoImpl();
            //文章对象
            Article article = new Article();
            //设置id
            article.setId(resultSet.getInt("id"));
            //设置文章名
            article.setName(resultSet.getString("name"));
            //设置文章内容
            article.setContent(resultSet.getString("content"));
            //设置作者
            article.setAuthor(resultSet.getString("author"));
            //设置分类
            article.setTags(tagDao.getArticleAllTags(article.getId()));
            return article;
        } catch (SQLException throwable) {
            System.out.println("文章信息设置出现问题");
            throwable.printStackTrace();
            return null;
        }
    }
}
