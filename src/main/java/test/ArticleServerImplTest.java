package test;

import dao.ArticleDaoImpl;
import dao.TagDaoImpl;
import myinterface.ArticleDao;
import myinterface.ArticleServer;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import pojo.Article;
import pojo.Page;
import server.ArticleServerImpl;

import java.util.ArrayList;
import java.util.Arrays;

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
    @Test
    public void getTag(){
        ArticleServer server= new ArticleServerImpl();
        Page<Article> tagPage = server.getTagPage("2",
                "5", "后端");
        System.out.println(tagPage);
        //返回得到的页面对象给前端渲染
    }
    @Test
    public void changeTag(){
        String tagsDetail ="算法&后端";
        ArticleDao articleDao = new ArticleDaoImpl();
        TagDaoImpl tagDao = new TagDaoImpl();
        //先删除原有分类，再添加新的分类
        boolean changeSort= tagDao.deleteArticleSort(4)&&tagDao.addArticleIdLinkTagId(4,
                new ArrayList<>(Arrays.asList(tagsDetail.split("&"))));
        //修改文章
        //是否成功
    }
}