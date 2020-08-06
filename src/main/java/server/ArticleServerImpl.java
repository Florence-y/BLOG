package server;

import dao.ArticleDaoImpl;
import dao.CommentDaoImpl;
import dao.TagDaoImpl;
import dao.UserDaoImpl;
import myinterface.*;
import pojo.Article;
import pojo.Comment;
import pojo.Page;
import pojo.User;

import java.util.*;

/**
 * @author Florence
 * 文章服务类
 */
public class ArticleServerImpl implements ArticleServer {
    ArticleDao articleDao;
    TagDao tagDao;
    UserDao userDao;
    CommentDao commentDao;
    /**
     * 调用dao添加文章
     * @param userName 用户名
     * @param article 博客对象
     * @return 是否成功
     */
    @Override
    public boolean addBlog(String userName, Article article) {
        articleDao=new ArticleDaoImpl();
        tagDao =new TagDaoImpl();
        //先添加文章内容
        int articleId=articleDao.add(userName,article);
        //返回0说明添加博客有问题
        if(0==articleId){
            return false;
        }
        //否则进行添加博客的分类信息（这里用的是一个中间表来记录博客的分类信息，因为数据库设计的原因才这么做，师兄具体可以看我数据库设计思路）
        return tagDao.addArticleIdLinkTagId(articleId,article.getTags());
    }

    /**
     * 获取主页面文章的分页展示对象
     * @param currentPage 现在的页数
     * @param size 每一页的数量
     * @return 封装好的分页对象
     */
    @Override
    public Page<Article> getMainPage(String currentPage, String size) {
        articleDao=new ArticleDaoImpl();
        //给前端渲染的页面对象
        Page<Article> page = new Page<>();
        //当前第几页
        int curPage =Integer.parseInt(currentPage);
        //页面文章大小
        int pageSize=Integer.parseInt(size);
        //获取文章总数
        int allCount=articleDao.getAllCount();
        //总的页面数计算方式，如果整除那么恰好就是那么页，如果不整除就加一页
        int totalPage=allCount%pageSize==0?allCount/pageSize:allCount/pageSize+1;
        //获取当前页面具体文章内容
        List<Article> pageData = articleDao.getPageData((curPage - 1) * pageSize, pageSize);
        //设置当前页码
        page.setCurrentPage(curPage);
        //设置当前页面数据数量
        page.setPageSize(pageSize);
        //获取总的记录数
        page.setTotalRecord(allCount);
        //设置文章内容
        page.setDataList(pageData);
        //设置总共几页
        page.setTotalPage(totalPage);
        //返回页面对象
        return page;
    }

    /**
     * 查看文章服务
     * @param articleId 文章id
     * @param theViewerName 查看的名字，可以为无
     * @return 返回查看文章页面需要的信息
     */
    @Override
    public Map<String, Object> viewBlog(String articleId, String theViewerName) {
        //map用于存储查看文章需要的信息
        Map<String,Object> map = new HashMap<>(8);
        //文章dao操作对象
        articleDao=new ArticleDaoImpl();
        //用户dao操作对象
        userDao=new UserDaoImpl();
        //评论操作对象
        commentDao= new CommentDaoImpl();
        //将字符串转化为整数
        int id =Integer.parseInt(articleId);
        //获取评论集合
        List<Comment> commentOfArticle = commentDao.getCommentOfArticle(id);
        //获取作者名
        String authorName=articleDao.getSomethingById(id,"author");
        //获取作者对象
        User user =userDao.getUserByName(authorName);
        //获取作者头像(可能已经注销用户,如果注销就返回默认头像)
        String authorFace = user==null?"../img/userAlreadyDeleted.jpg":user.getFace();
        //插入文章名
        map.put("title",articleDao.getSomethingById(id,"name"));
        //插入文章内容
        map.put("content",articleDao.getSomethingById(id,"content"));
        //插入作者名
        map.put("authorName",authorName);
        //插入作者头像
        map.put("authorFace",authorFace);
        //插入我的头像（登录用户）
        if(theViewerName!=null) {
            map.put("myFace", userDao.getUserByName(theViewerName).getFace());
            //插入所有评论
            map.put("allComment",commentOfArticle);
            return map;
        }
        //游客用户（给个默认头像）
        map.put("myFace","../img/defaultFace.jpg");
        //插入评论
        map.put("allComment",commentOfArticle);
        //返回对象信息
        return map;
    }

    /**
     * 根据分类查找
     * @param currentPage 当前页码
     * @param size 大小
     * @param tag 要的分类
     * @return 返回页码对象
     */
    @Override
    public Page<Article> getTagPage(String currentPage, String size, String tag) {
        //文章dao对象
        articleDao=new ArticleDaoImpl();
        //分类dao对象
        tagDao =new TagDaoImpl();
        //页面展示对象，用户前台的页面展示需要的信息的存储
        Page<Article> page = new Page<>();
        //当前页码
        int curPage =Integer.parseInt(currentPage);
        //每一页文章多少
        int pageSize=Integer.parseInt(size);
        //总文章数
        int allCount=tagDao.getSomeTagCount(tag);
        //总的页面数计算方式，如果整除那么恰好就是那么页，如果不整除就加一页
        int totalPage=allCount%pageSize==0?allCount/pageSize:allCount/pageSize+1;
        //获取当前页面具体文章内容
        List<Article> pageData = articleDao.getPageDataByTag((curPage - 1) * pageSize,
                pageSize,tagDao.getSomeTagArticlesId(tag));
        //设置当前页码
        page.setCurrentPage(curPage);
        //设置当前页面数据数量
        page.setPageSize(pageSize);
        //获取总的记录数
        page.setTotalRecord(allCount);
        //设置文章内容
        page.setDataList(pageData);
        //设置总共几页
        page.setTotalPage(totalPage);
        //返回文章对象
        return page;
    }

    /**
     * 根据搜索条件获取文章
     * @param currentPage 当前页码
     * @param size 一页多少文章数量
     * @param condition 提供的条件
     * @return 展示的页面对象
     */
    @Override
    public Page<Article> exploreArticles(String currentPage, String size, String condition) {
        Page<Article> page = new Page<>();
        articleDao = new ArticleDaoImpl();
        //当前页码
        int curPage=Integer.parseInt(currentPage);
        //页面文章多少
        int pageSize=Integer.parseInt(size);
        //总文章数
        int allCount=articleDao.getSomeConditionCount(condition);
        //总的页面数计算方式，如果整除那么恰好就是那么页，如果不整除就加一页
        int totalPage=allCount%pageSize==0?allCount/pageSize:allCount/pageSize+1;
        //获取搜索到的文章对象
        List<Article> pageData= articleDao.getPageDataByCondition((curPage - 1) * pageSize,pageSize,condition);
        //设置当前页码
        page.setCurrentPage(curPage);
        //设置当前页面数据数量
        page.setPageSize(pageSize);
        //获取总的记录数
        page.setTotalRecord(allCount);
        //设置文章内容
        page.setDataList(pageData);
        //设置总共几页
        page.setTotalPage(totalPage);
        //返回文章对象
        return page;
    }

    /**
     * 根据用户名获取用户管理文章
     * @param currentPage 当前页码
     * @param size 当前页面文章多少
     * @param userName 用户名
     * @return 返回管理文章对象
     */
    @Override
    public Page<Article> getPersonalArticles(String currentPage,String size,String userName) {
        articleDao=new ArticleDaoImpl();
        Page<Article> page = new Page<>();
        //当前第几页
        int curPage =Integer.parseInt(currentPage);
        //页面文章大小
        int pageSize=Integer.parseInt(size);
        //获取文章总数
        int allCount=articleDao.getArticleCountByUserName(userName);
        //总的页面数计算方式，如果整除那么恰好就是那么页，如果不整除就加一页
        int totalPage=allCount%pageSize==0?allCount/pageSize:allCount/pageSize+1;
        //获取当前页面具体文章内容
        List<Article> pageData = articleDao.getPageDataByUserName((curPage - 1) * pageSize, pageSize,userName);
        //设置当前页码
        page.setCurrentPage(curPage);
        //设置当前页面数据数量
        page.setPageSize(pageSize);
        //获取总的记录数
        page.setTotalRecord(allCount);
        //设置文章内容
        page.setDataList(pageData);
        //设置总共几页
        page.setTotalPage(totalPage);
        //返回页面对象
        return page;
    }

    /**
     * 根据文章id获取文章信息
     * @param id 文章id
     * @return 文章信息对象
     */
    @Override
    public Article getArticleByArticleId(String id) {
        articleDao = new ArticleDaoImpl();
        //调用dao获取文章
        return articleDao.getArticleByArticleId(Integer.parseInt(id));
    }

    /**
     * 修改文章
     * @param id 文章id
     * @param title 标题
     * @param content 内容
     * @param tagsDetail 分类
     * @return 是否成功
     */
    @Override
    public boolean editArticle(String id, String title, String content, String tagsDetail) {
        articleDao = new ArticleDaoImpl();
        tagDao = new TagDaoImpl();
        //先删除原有分类，再添加新的分类
        boolean changeSort= tagDao.deleteArticleSort(Integer.parseInt(id))&&tagDao.addArticleIdLinkTagId(Integer.parseInt(id),
                new ArrayList<>(Arrays.asList(tagsDetail.split("&"))));
        //修改文章
        boolean changeBlog= articleDao.editBlog(Integer.parseInt(id),title,content);
        //是否成功
        return changeBlog&&changeSort;
    }

    /**
     * 删除文章
     * @param articleId 文章id
     * @return 文章id
     */
    @Override
    public boolean deleteArticle(String articleId) {
        articleDao = new ArticleDaoImpl();
        tagDao = new TagDaoImpl();
        //删除文章主体内容
        boolean deleteContent=articleDao.delete(Integer.parseInt(articleId));
        //删除文章分类
        boolean deleteTag =tagDao.deleteArticleSort(Integer.parseInt(articleId));
        //返回删除结果
        return deleteContent&&deleteTag;
    }

    /**
     * 添加评论
     * @param comment 评论对象
     * @return 返回评论id
     */
    @Override
    public int addComment(Comment comment) {
        commentDao= new CommentDaoImpl();
        return commentDao.add(comment);
    }

    /**
     * 评论点赞功能
     * @param commentId 评论的id
     * @param nowAgreeCount 当前评论数
     * @return 点赞完的点赞数
     */
    @Override
    public String agreeComment(String commentId,String nowAgreeCount) {
        commentDao = new CommentDaoImpl();
        return commentDao.changeAgreeCount(Integer.parseInt(commentId),Integer.parseInt(nowAgreeCount));
    }

}
