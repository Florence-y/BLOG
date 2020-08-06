package myinterface;

import pojo.User;


/**
 * @author Florence
 * 用户服务类接口
 */
public interface UserServer {
    /**
     * 添加用户
     * @param user 用户信息
     */
    void addUser(User user);

    /**
     * 用户是否存在
     * @param name 用户名
     * @return 是否存在
     */
    boolean isExist(String name);

    /**
     * 账号是否存在
     * @param account 账号
     * @return 是否存在
     */
    boolean isExistByAccount(String account);

    /**
     * 登录方法
     * @param user 用户
     * @return 登录用户信息返回
     */
    User login(User user);

    /**
     * 根据账号获取用户信息
     * @param account 账号
     * @return 用户信息对象
     */
    User getUserInf(String account);

    /**
     * 改变用户头像
     * @param account 账号
     * @param face 头像
     */
    void changeUserFace(String account,String face);

    /**
     * 改变用户性别和名字
     * @param account 账号
     * @param sex 性别
     * @param name 名字
     */
    void changUserSexAndName(String account,String sex,String name);

    /**
     * 改变用户密码
     * @param account 账号
     * @param nowPassWord 现在的密码
     * @param wantToChangePassWord 想要改的密码
     * @return 返回是否成功
     */
    boolean changeUserPassWord(String account,String nowPassWord,String wantToChangePassWord);

    /**
     * 用户注销服务
     * @param account 要删除的账号
     * @param passWord 用户输入的密码
     * @return 是否成功
     */
    boolean deleteUser(String account, String passWord);
}
