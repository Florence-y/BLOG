package pojo;

/**
 * @author Florence
 * 用户实体类
 */
public class User {
    /**
     * 用户id
     */
    private int id;
    /**
     * 用户名
     */
    private String name;
    /**
     * 用户账号
     */
    private String account;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户性别
     */
    private String sex;
    /**
     * 用户头像
     */
    private String face;

    public User(int id, String name, String password, String sex, String face) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.sex = sex;
        this.face = face;
    }

    public User(int id, String name, String account, String password, String sex, String face) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.sex = sex;
        this.face = face;
    }

    public User() {

    }

    public User(int id) {
        this.id=id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", face='" + face + '\'' +
                '}';
    }
}
