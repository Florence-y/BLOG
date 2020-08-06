package test;

import myinterface.ArticleServer;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import server.ArticleServerImpl;

import static org.junit.Assert.*;

/**
 * @author Florence
 * 文章服务测试类
 */
public class ArticleServerImplTest {
    @Test
    public void getTagPageTest(){
        ArticleServer a = new ArticleServerImpl();
        System.out.println(a.getTagPage("2","3","算法"));
    }
    @Test
    public void getExploreData(){
        ArticleServer a = new ArticleServerImpl();
        System.out.println(a.exploreArticles("1","5","鬼"));
    }
    @Test
    public void getPageDataByName(){
        ArticleServer a = new ArticleServerImpl();
        System.out.println(a.getPersonalArticles("2","2","吴晓吟"));
    }
    @Test
    public void editBlog(){
        ArticleServer a = new ArticleServerImpl();
        a.editArticle("1","我们最棒","是真的哦","算法&后端");
    }
    @Test
    public void delete(){
        ArticleServer  a = new ArticleServerImpl();
        a.deleteArticle("1");
    }
    @Test
    public void agree() {
        ArticleServer a = new ArticleServerImpl();
        a.agreeComment("1","1");
    }
}