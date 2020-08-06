package server;
import dao.UserDaoImpl;
import myinterface.UserDao;
import myinterface.UserServer;
import pojo.User;
import until.Md5Until;
/**
 * @author Florence
 * 用户服务实现类
 */
public class UserServerImpl implements UserServer {
    /**
     * User用户数据库操作对象
     */
    UserDao dao;
    /**
     * 添加用户
     * @param user 用户信息
     */
    @Override
    public void addUser(User user) {
        dao = new UserDaoImpl();
        //调用dao添加用户
        dao.add(user);
    }

    /**
     * 根据名字查找用户
     * @param name 用户名
     * @return 是否存在
     */
    @Override
    public boolean isExist(String name) {
        dao = new UserDaoImpl();
        //调用dao查找用户
        return dao.findUser(name);
    }

    /**
     * 根据账号查找用户
     * @param account 账号
     * @return 是否存在
     */
    @Override
    public boolean isExistByAccount(String account) {
        dao = new UserDaoImpl();
        //调用dao查找用户（根据账号）
        return dao.findUserByAccount(account);
    }

    /**
     * 验证用户登录的服务
     * @param user 用户
     * @return 用户信息
     */
    @Override
    public User login(User user) {
        dao = new UserDaoImpl();
        User myUser= dao.getUser(user.getAccount());
        //用户不存在
        if(myUser==null){
            return null;
        }
        //密码不相同
        if(!myUser.getPassword().equals(Md5Until.getMd5(user.getPassword()))){
            return new User(-1);
        }
        //密码相同
        return myUser;
    }

    /**
     * 根据账号获取用户信息
     * @param account 账号
     * @return 返回User对象
     */
    @Override
    public User getUserInf(String account) {
        dao = new UserDaoImpl();
        //调用dao返回用户信息
        return dao.getUser(account);
    }

    /**
     * 改变用户头像
     * @param account 账号
     * @param face 头像
     */
    @Override
    public void changeUserFace(String account, String face) {
        dao = new UserDaoImpl();
        //调用dao改变用户头像
        dao.changeFace(account,face);
    }

    /**
     * 改变用户名和性别
     * @param account 账号
     * @param sex 性别
     * @param name 名字
     */
    @Override
    public void changUserSexAndName(String account, String sex, String name) {
        dao=new UserDaoImpl();
        //改变性别
        dao.changeOneByOneCondition(account,"sex",sex);
        //改变用户名
        dao.changeOneByOneCondition(account,"name",name);
    }

    /**
     * 改变用户的密码
     * @param account 账号
     * @param nowPassWord 现在的密码
     * @param wantToChangePassWord 想要改的密码
     * @return 是否成功（失败的原因只有数据库出现问题或者是原密码错误。）
     */
    @Override
    public boolean changeUserPassWord(String account, String nowPassWord, String wantToChangePassWord) {
        dao=new UserDaoImpl();
        User user = dao.getUser(account);
        //原来的密码跟用户输入的密码对不上
        if(!user.getPassword().equals(Md5Until.getMd5(nowPassWord))){
            return false;
        }
        //原来的密码跟用户输入的密码相同，进行修改密码
        dao.changeOneByOneCondition(account,"password",Md5Until.getMd5(wantToChangePassWord));
        return true;
    }

    /**
     * 用户注销服务
     * @param account 要删除的账号
     * @param passWord 用户输入的密码
     * @return 是否成功
     */
    @Override
    public boolean deleteUser(String account, String passWord) {
        dao=new UserDaoImpl();
        //获取数据库中真正的密码
        String realPassWord =dao.getUser(account).getPassword();
        //如果密码相同(删除成功)
        if(realPassWord.equals(Md5Until.getMd5(passWord))){
            dao.delete(account);
            return true;
        }
        //删除失败
        return false;
    }
}
