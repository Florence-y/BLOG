package dao;
import myinterface.UserDao;
import pojo.User;
import until.C3P0Until;
import until.Md5Until;
import until.MySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Florence
 * 用户数据库操作类
 */
public class UserDaoImpl implements UserDao {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public UserDaoImpl(){
    }

    /**
     * 添加用户
     * @param user 用户
     */
    @Override
    public void add(User user) {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.INSERT_USER.toString());
            //设置伪id，其实因为id是主键，就别设置比当前最大id大的就好，其他无所谓，所以设置0保险起见
            preparedStatement.setInt(1,0);
            //设置用户账号
            preparedStatement.setString(2,user.getAccount());
            //设置用户名
            preparedStatement.setString(3,user.getName());
            //设置用户密码，用MD5进行加密
            preparedStatement.setString(4, Md5Until.getMd5(user.getPassword()));
            //设置用户性别
            preparedStatement.setString(5,"男");
            //设置用户默认头像
            preparedStatement.setString(6,"../img/initFace.jpg");
            //执行语句添加用户
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }finally {
            //清理数据库资源
            C3P0Until.close(connection,preparedStatement);
        }
    }

    /**
     * 删除用户
     * @param account 账号
     */
    @Override
    public void delete(String account) {
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.DELETE_USER.toString());
            //设置要删除的用户的账号
            preparedStatement.setString(1,account);
            //进行删除
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            //清理数据库资源
            C3P0Until.close(connection,preparedStatement);
        }
    }

    /**
     * 改变用户信息(全部信息)
     * @param user 用户
     */
    @Override
    public void change(User user) {
        try {
            connection = C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.UPDATE_USER.toString());
            //设置用户想要改的密码（MD5加密）
            preparedStatement.setString(1, Md5Until.getMd5(user.getPassword()));
            //设置用户性别
            preparedStatement.setString(2,user.getSex());
            //设置用户头像
            preparedStatement.setString(3,user.getFace());
            //设置用户账号
            preparedStatement.setString(4,user.getAccount());
            //进行更新
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            //清理数据库资源
            C3P0Until.close(connection,preparedStatement);
        }
    }

    /**
     * 查找一个用户的是否存在
     * @param name 用户名
     * @return 是否存在
     */
    @Override
    public boolean findUser(String name) {
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_USER_INF_BY_NAME.toString());
            //设置要查找的用户名
            preparedStatement.setString(1,name);
            //进行查找
            resultSet = preparedStatement.executeQuery();
            //是否存在？
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            //清理数据库资源
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //不存在
        return false;
    }

    /**
     * 根据账号查找一个用户
     * @param account 账号
     * @return 是否存在
     */
    @Override
    public boolean findUserByAccount(String account) {
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_USER_INF_BY_ACCOUNT.toString());
            //设置用户账号
            preparedStatement.setString(1,account);
            //查找用户是否存在
            resultSet = preparedStatement.executeQuery();
            //是否存在？
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            //清理数据库资源
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //不存在
        return false;
    }

    /**
     * 根据账号获取一个用户信息
     * @param account 账号
     * @return 用户
     */
    @Override
    public User getUser(String account) {
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_USER_INF_BY_ACCOUNT.toString());
            //设置用户账号
            preparedStatement.setString(1,account);
            //获取用户
            resultSet = preparedStatement.executeQuery();
            //自定义封装方法获取用户信息
            return setUserInf(resultSet);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            //清理数据库资源
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //获取对象失败
        return null;
    }

    /**
     * 改变用户头像地址
     * @param account 账号
     * @param face 头像
     */
    @Override
    public void changeFace(String account,String face) {
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.UPDATE_USER_FACE.toString());
            //设置用户头像
            preparedStatement.setString(1,face);
            //设置用户账号
            preparedStatement.setString(2,account);
            //改变头像
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
    }

    /**
     * 根据账号来改变某一项的信息(也就是比较泛用的方法，可以自定想要改变的字段和值，但是参数也变多了，有时有点麻烦)
     * @param account 账号
     * @param wanToChange 想要改变的属性
     * @param valueOfChange 改变的值
     */
    @Override
    public void changeOneByOneCondition(String account, String wanToChange, String valueOfChange) {
        try {
            //自定义想要改变的信息的语句，wantToChange:想要改变的列名  account:被修改的用户的账号
            String sql="UPDATE users SET "+wanToChange+" = ? WHERE account = ?";
            connection=C3P0Until.getConnection();
            assert connection != null;
            preparedStatement=connection.prepareStatement(sql);
            //设置想要修改的列的值
            preparedStatement.setString(1,valueOfChange);
            //设置用户账号
            preparedStatement.setString(2,account);
            //进行修改
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            C3P0Until.close(connection,preparedStatement);
        }
    }

    /**
     * 根据用户名获取对象信息
     * @param name 用户名
     * @return 用户对象
     */
    @Override
    public User getUserByName(String name) {
        try {
            connection=C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_USER_INF_BY_NAME.toString());
            //设置用户名
            preparedStatement.setString(1,name);
            //获取用户信息
            resultSet = preparedStatement.executeQuery();
            //自定义封装方法获取对象有的信息
            return setUserInf(resultSet);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            //清理数据库资源
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        return null;
    }

    /**
     * 自定义封装对象方法
     * @param resultset 用户信息结果集
     * @return 用户
     */
    private User setUserInf(ResultSet resultset){
        try {
            //用户不存在
            if(!resultset.next()){
                return null;
            }
            //下面是依次设置信息
            User user = new User();
            //设置用户id
            user.setId(resultset.getInt("id"));
            //设置用户账号
            user.setAccount(resultset.getString("account"));
            //设置用户名
            user.setName(resultset.getString("name"));
            //设置用户密码
            user.setPassword(resultset.getString("password"));
            //设置用户性别
            user.setSex(resultset.getString("sex"));
            //设置用户头像（地址）
            user.setFace(resultset.getString("face"));
            //返回封装好的用户对象
            return user;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        //获取用户对象失败
        return null;
    }
}
