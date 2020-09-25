package pojo;

import annontation.DbCol;
import annontation.DbPriKey;
import annontation.DbTable;
import jdk.nashorn.internal.objects.annotations.Property;

/**
 * @author Florence
 * 用户实体类
 */
@DbTable("users")
public class User {
    /**
     * 用户id
     */
    @DbCol("id")
    @DbPriKey
    private int id;
    /**
     * 用户名
     */
    @DbCol("name")
    private String name;
    /**
     * 用户账号
     */
    @DbCol("account")
    private String account;
    /**
     * 用户密码
     */
    @DbCol("password")
    private String password;
    /**
     * 用户性别
     */
    @DbCol("sex")
    private String sex;
    /**
     * 用户头像
     */
    @DbCol("face")
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
