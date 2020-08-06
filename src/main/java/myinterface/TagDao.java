package myinterface;

import java.util.List;

/**
 * @author Florence
 * 分类与文章连接表数据库操作接口
 */
public interface TagDao {
    /**
     * 设置文章与分类之间的联系
     * @param idOfArticle 文章的id
     * @param idOfTag 分类的id
     */
    void addArticleTag(int idOfArticle, int idOfTag);


    /**
     * 根据传入的分类集合tags添加文章与分类之间的联系
     * @param articleId 文章id
     * @param tags 分类集合
     * @return 是否成功
     */
    boolean addArticleIdLinkTagId(int articleId, List<String> tags);

    /**
     * 根据文章id给文章添加分类
     * @param id 文章的id
     * @return 分类的集合
     */
    List<String> getArticleAllTags(int id);

    /**
     * 查看某一个分类具有的文章数量
     * @param tag 分类名
     * @return 返回的数量
     */
    int getSomeTagCount(String tag);

    /**
     * 根据分类属性查询属于该分类的文章
     * @param tag 分类名
     * @return 返回id集合
     */
    List<Integer> getSomeTagArticlesId(String tag);

    /**
     * 删除文章现有的分类
     * @param articleId 文章id
     * @return 是否成功
     */
    boolean deleteArticleSort(int articleId);
}
