package pojo;

/**
 * @author Florence
 * 评论实体类
 */
public class Comment {
    /**
     * 评论id
     */
    private int id;
    /**
     * 点赞数目
     */
    private int agreeCount;
    /**
     * 文章id
     */
    private int articleId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 日期
     */
    private String date;
    /**
     * 用户头像
     */
    private String userFace;



    public Comment(int id, int agreeCount, int articleId, String userName, String content, String date, String userFace) {
        this.id = id;
        this.agreeCount = agreeCount;
        this.articleId = articleId;
        this.userName = userName;
        this.content = content;
        this.date = date;
        this.userFace = userFace;
    }

    public Comment() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(int agreeCount) {
        this.agreeCount = agreeCount;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", agreeCount=" + agreeCount +
                ", articleId=" + articleId +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", userFace='" + userFace + '\'' +
                '}';
    }
}
