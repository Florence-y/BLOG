package myinterface;

/**
 * @author Florence
 */
public interface SortLinkArticleDao {
    /**
     * 根据分类id去查询分类的具体属性
     * @param sortId 分类id
     * @return 分类的属性
     */
    String searchTagNameByTagId(int sortId);

    /**
     * 根据分类的属性来查找分类的id
     * @param tagName 分类的名字
     * @return 返回id
     */
    int searchTagIdByTagName(String tagName);
}
