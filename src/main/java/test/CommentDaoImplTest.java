package test;

import dao.CommentDaoImpl;
import myinterface.CommentDao;
import org.junit.Test;
import pojo.Comment;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Florence
 * 评论区测试类
 */

public class CommentDaoImplTest {
    @Test
    public void addComent(){
        Comment a = new Comment(0,0,2,"吴晓吟","干得好呀！！！","1975-07-17","../img/default.png");
        CommentDao b = new CommentDaoImpl();
        b.add(a);
    }

    @Test
    public void getComments(){
        CommentDao b = new CommentDaoImpl();
        List<Comment> commentOfArticle = b.getCommentOfArticle(2);
        for (Comment a:commentOfArticle){
            System.out.println((a.getDate()).getClass());
        }
        System.out.println(b.getCommentOfArticle(2));
    }
}