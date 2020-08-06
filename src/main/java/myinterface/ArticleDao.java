package myinterface;

import pojo.Article;

import java.util.List;

/**
 * @author Florence
 * 文章操作接口
 */
public interface ArticleDao {
    /**
     * 添加文章
     * @param name 名字
     * @param article 文章信息
     * @return 文章id
     */
    int add(String name, Article article);

    /**
     * 获取文章总共有多少篇
     * @return 文章篇数
     */
    int getAllCount();

    /**
     * 获取当前页的文章数据
     * @param start 开始查询的地方
     * @param pageSize 每一页多少条内容
     * @return 存有具体文章数据的列表
     */
    List<Article> getPageData(int start, int pageSize);

    /**
     * 根据id获取某项值
     * @param id id
     * @param wantToGet 想要获取的信息
     * @return 获取到的信息
     */
    String getSomethingById(int id,String wantToGet);

    /**
     * 获取分类页的数据
     * @param start 开始搜索的地方
     * @param pageSize 每一页页面的大小
     * @param wantToGetArticleId 想要获取文章的id集合（根据中间表查询得来）
     * @return 页面对象
     */
    List<Article> getPageDataByTag(int start, int pageSize, List<Integer> wantToGetArticleId);

    /**
     * 根据搜索条件获取符合该条件的文章总数
     * @param condition 条件
     * @return 返回总数
     */
    int getSomeConditionCount(String condition);

    /**
     * 根据搜索条件获取展示的页面对象
     * @param start 开始搜索的地方
     * @param pageSize 页面大小
     * @param condition 条件
     * @return 符合条件的文章集合
     */
    List<Article> getPageDataByCondition(int start, int pageSize, String condition);

    /**
     * 根据用户名查找属于该用户的文章数目
     * @param userName 用户名
     * @return 文章数目
     */
    int getArticleCountByUserName(String userName);

    /**
     * 根据文章名获取文章对象
     * @param start 开始查找的地方
     * @param pageSize 一页文章数目多少
     * @param userName 用户名
     * @return 返回文章集合
     */
    List<Article> getPageDataByUserName(int start, int pageSize, String userName);

    /**
     * 根据文章id获取文章对象
     * @param articleId 文章id
     * @return 获取到的文章对象
     */
    Article getArticleByArticleId(int articleId);

    /**
     * 编辑博客
     * @param articleId 文章id
     * @param title 标题
     * @param content 内容
     * @return 是否成功
     */
    boolean editBlog(int articleId, String title, String content);

    /**
     * 删除文章
     * @param articleId 文章id
     * @return 是否成功
     */
    boolean delete(int articleId);
}
