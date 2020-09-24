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
     * @param value 可变参数
     * @return 返回在第几行影响
     */
    int updateOneColById(T pojo,Object... value);

    /**
     * 插入一条数据
     * @param pojo 实体类对象
     * @param value 可变参数
     * @return 影响在第几行
     */
    int insertOneRow(T pojo,Object... value);

    /**
     * 根据id删除
     * @param id id
     * @return 返回值
     */
    int deleteById(int id);

    /**
     * 获取全部数据
     * @return 返回拥有全部数据的对象
     */
     List<T> getAllRow();
}
