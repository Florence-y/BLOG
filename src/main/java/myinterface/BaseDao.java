package myinterface;

import java.util.List;

/**
 * @author Florence
 * 基本dao类接口
 */
public interface BaseDao<T> {
    /**
     * 根据id查找
     * @param id 要求的id
     * @return 返回值
     */
    T selectById(int id);

    /**
     * 根据id更新
     * @param pojo 实体类对象
     * @return 返回在第几行影响
     */
    int updateOneColById(T pojo);

    /**
     * 插入一条数据
     * @param pojo 实体类对象
     * @return 影响在第几行
     */
    int insertOneRow(T pojo);

    /**
     * 根据id删除
     * @param pojo
     * @param id id
     * @return 返回值
     */
    int deleteById(T pojo,int id);

    /**
     * 获取全部数据
     * @return 返回拥有全部数据的对象
     */
     List<T> getAllRow();
}
