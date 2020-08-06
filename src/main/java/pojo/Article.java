package pojo;

import java.util.List;

/**
 * @author Florence
 * 文章实体类
 */
public class Article {
    /**
     * 文章id
     */
    private int id ;
    /**
     * 文章标题
     */
    private String name;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章作者
     */
    private String author;
    /**
     * 文章分类
     */
    private List<String> tags;

    public Article(int id, String name, String content, String author, List<String> tags) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.author = author;
        this.tags = tags;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", tags=" + tags +
                '}';
    }
}
