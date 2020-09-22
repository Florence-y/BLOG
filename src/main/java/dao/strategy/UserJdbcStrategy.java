package dao.strategy;

import myinterface.JdbcGetPojoStrategy;
import pojo.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Florence
 */
public class UserJdbcStrategy  implements JdbcGetPojoStrategy<User> {
    @Override
    public User strategy(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setFace(resultSet.getString("face"));
        user.setPassword(resultSet.getString("password"));
        user.setSex(resultSet.getString("sex"));
        user.setAccount(resultSet.getString("account"));
        user.setName(resultSet.getString("name"));
        return user;
    }
}
