package myinterface;

import pojo.User;

/**
 * @author Florence
 * 用户操作接口
 */
public interface UserDao {
    /**
     * 添加用户方法
     * @param user 用户
     */
    void add(User user);

    /**
     * 删除用户方法
     * @param user 用户
     */
    void delete(String user);

    /**
     * 更改用户信息方法
     * @param user 用户
     */
    void change(User user);

    /**
     * 查找一个用户
     * @param name 用户名
     * @return 是否查找到
     */
    boolean findUser(String name);

    /**
     * 根据账号查找一个用户
     * @param account 账号
     * @return 是否查找到
     */
    boolean findUserByAccount(String account);

    /**
     * 获取一个用户
     * @param account 账号
     * @return 获取到的用户 或者是没有获取到为NULL
     */
    User getUser(String account);

    /**
     * 改变用户头像
     * @param account 账号
     * @param face 头像
     */
    void changeFace(String account ,String face);

    /**
     * 根据账号，改变一项的值
     * @param account 账号
     * @param wanToChange 想要改变的属性
     * @param valueOfChange 改变的值
     */
    void changeOneByOneCondition(String account,String wanToChange,String valueOfChange);

    /**
     * 根据用户名获取用户信息
     * @param name 用户名
     * @return 用户对象
     */
    User getUserByName(String name);
}
