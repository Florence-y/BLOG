package test;

import dao.ArticleDaoImpl;
import myinterface.ArticleDao;
import myinterface.ArticleServer;
import org.junit.Test;
import pojo.Article;
import pojo.Page;
import server.ArticleServerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florence
 * 文章操作类测试
 */
public class ArticleDaoImplTest {
    @Test
    public void addBlog(){
        ArticleServer a = new ArticleServerImpl();
        Article b= new Article();
        List<String> c = new ArrayList<>();
        c.add("后端");
        b.setAuthor("吴彦臻");
        b.setContent("JAVA部落最棒啦");
        b.setId(0);
        b.setTags(c);
        b.setName("Java部落！");
        a.addBlog("吴彦臻",b);
    }
    @Test
    public void page(){
        ArticleServer server = new ArticleServerImpl();
        Page<Article> page =server.getMainPage("1","5");
        System.out.println(page);
    }
    @Test
    public void testFindArticleByList(){
        ArticleDao a = new ArticleDaoImpl();
        List<Integer> b= new ArrayList<>();
        b.add(3);
        b.add(4);
        b.add(5);
        b.add(11);
        b.add(12);
        b.add(13);
        b.add(14);
        System.out.println(a.getPageDataByTag(0,3,b));
    }
    @Test
    public void getCount(){
        ArticleDao a = new ArticleDaoImpl();
        System.out.println(a.getSomeConditionCount("彦臻"));
    }
    @Test
    public void getCount2(){
        ArticleDao a = new ArticleDaoImpl();
        a.getArticleCountByUserName("吴彦臻");
        System.out.println(a.getArticleCountByUserName("吴彦臻"));
    }
    @Test
    public void getPageData(){
        ArticleDao a = new ArticleDaoImpl();
        System.out.println(a.getPageDataByUserName(0,2,"吴晓吟"));
    }
}