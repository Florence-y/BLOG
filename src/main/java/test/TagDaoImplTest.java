package test;

import dao.TagDaoImpl;
import myinterface.TagDao;
import org.junit.Test;

/**
 * 分类测试类
 * @author Florence
 */
public class TagDaoImplTest {
    @Test
    public void addTag(){
        TagDao a= new TagDaoImpl();
        a.getArticleAllTags(4);
    }
    @Test
    public void findArticleIdByTagNameTest(){
        TagDao a = new TagDaoImpl();
        System.out.println(a.getSomeTagArticlesId("后端"));
    }
    @Test
    public void getSomeTagAllCount(){
        TagDao a = new TagDaoImpl();
        System.out.println(a.getSomeTagCount("算法")+"--------"+a.getSomeTagCount("后端"));
    }
}