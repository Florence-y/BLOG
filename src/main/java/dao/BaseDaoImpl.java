package dao;
import myinterface.BaseDao;
import myinterface.InsertStrategy;
import myinterface.JdbcGetPojoStrategy;
import until.ArrayUtil;
import until.C3P0Until;
import until.JdbcUtil;
import until.ReflectUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.List;
import static until.ArrayUtil.getArrByOddOrEven;

/**
 * @author Florence
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private static final int two=2;
    /**
     * 获取 T 表的名字
     * @return 数据库表的名字
     */
    public abstract String getTableName();

    /**
     * 获取某一列名
     * @return 返回列名
     */
    public abstract String getTableIdField();

    /**
     * 获取相应的jdbc策略
     * @return
     */
    public abstract JdbcGetPojoStrategy getPackageStrategy();

    /**
     * 获取插入行的策略
     * @return
     */
    public abstract InsertStrategy getInsertRowStrategy();
    /**
     * 根据id获取一个对象
     * @param id 要求的id
     * @return
     */
    @Override
    public T selectById(int id) {
        String sql = MessageFormat.format("select * from {0} where {1} = {3}",getTableName(),getTableIdField(),id);
        return JdbcUtil.queryForJavaBean(sql,getPackageStrategy());
    }

    /**
     * 根据最后一个条件更新一些
     * 函数的用法 key value key value key value （condition） key value
     * @param pojo 实体类对象
     * @return 返回更新到几行
     */
    @Override
    public int updateColByOneCondition(T pojo,Object... value) {
        try {
            if (value.length%two!=0){
                throw new Exception("参数长度有问题");
            }
            //先利用反射拼接语句，然后根据参数设置
            return JdbcUtil.update(ReflectUtil.getUpdateSql(pojo,getArrByOddOrEven(value, ArrayUtil.odd)),getArrByOddOrEven(value,ArrayUtil.even));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 插入一行数据
     * @param pojo 实体类对象
     * @return 影响到第几行
     */
    @Override
    public int insertOneRow(T pojo,Object... value) {
        StringBuilder unKnowParameter= new StringBuilder();
        for (int i=0;i<value.length-1;i++){
            unKnowParameter.append("?,");
        }
        unKnowParameter.append("?");
        String sql =MessageFormat.format("INSERT INTO "+getTableName()+" VALUES ("+unKnowParameter+")",value);
        return JdbcUtil.insertOneRow(sql,getInsertRowStrategy(),value);
    }

    /**
     * 根据id删除一条数据
     * @param id id
     * @return
     */
    @Override
    public int deleteById(int id) {
        String sql= MessageFormat.format("DELETE FROM {0} WHERE {1} = {2}",getTableName(),getTableIdField(),id);
        return JdbcUtil.executeSql(sql);
    }

    /**
     * 获取全部的行
     * @return
     */
    @Override
    public List<T> getAllRow(){
        String sql =MessageFormat.format("select * from {0}",getTableName());
        List<T> resultList =JdbcUtil.queryForJavaBeanAllData(sql,getPackageStrategy());
        return resultList;
    }

    /**
     *
     * @param keyAndValue 键值对
     * @return 只有两个条件
     */
    @Override
    public boolean isExistQueryBySomeCondition(Object... keyAndValue) {
        try {
            if (keyAndValue.length!=2){
                throw new Exception("可变参数输入异常");
            }
            connection=C3P0Until.getConnection();
            String sql="SELECT * FROM "+getTableName()+" WHERE "+keyAndValue[0]+" = ? limit 1";
            resultSet=JdbcUtil.queryForGetResultSet(connection,sql,keyAndValue[1]);
            if (resultSet.next()){
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            C3P0Until.close(connection,resultSet);
        }
        return false;
    }
}
