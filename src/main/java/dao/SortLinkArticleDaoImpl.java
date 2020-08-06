package dao;
import myinterface.SortLinkArticleDao;
import until.C3P0Until;
import until.MySql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Florence
 * 分类和文章之间的连接数据库实现类
 */
public class SortLinkArticleDaoImpl implements SortLinkArticleDao {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    /**
     * 根据分类的属性来查找分类的id(这个在添加文章的时候建立文章与分类之间的联系要用到)
     * @param tagName 分类的名字
     * @return 获得到的分类的ID
     */
    @Override
    public int searchTagIdByTagName(String tagName) {
        try {
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_TAG_ID.toString());
            //设置分类的属性名（如后端）
            preparedStatement.setString(1,tagName);
            //这里的resultSet需要重新建立，防止关闭了主循环的resultSet
            resultSet = preparedStatement.executeQuery();
            //如果有记录
            if(resultSet.next()){
                //返回该分类属性的id（用于建立分类与文章之间的连接）
                return resultSet.getInt("id");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //获取分类id失败
        return 0;
    }

    /**
     * 根据分类的id去查询分类的属性
     * @param sortId 分类id
     * @return 查到的分类的具体值（比如后端）
     */
    @Override
    public String searchTagNameByTagId(int sortId) {
        try {
            connection= C3P0Until.getConnection();
            assert connection != null;
            //声明预备语句（放在enum类中）
            preparedStatement=connection.prepareStatement(MySql.QUERY_TAG_VALUE.toString());
            //设置分类id
            preparedStatement.setInt(1,sortId);
            //这里的resultSet需要重新建立，防止关闭了主循环的resultSet
            resultSet = preparedStatement.executeQuery();
            //如果有记录
            if(resultSet.next()){
                //返回分类属性名（用于查找某一篇文章拥有的分类,因为我们是记录文章id和分类id，而分类id要得到具体值得到分类表中查）
                return resultSet.getString("name");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }finally {
            C3P0Until.close(connection,preparedStatement,resultSet);
        }
        //获取分类属性值失败
        return null;
    }
}
