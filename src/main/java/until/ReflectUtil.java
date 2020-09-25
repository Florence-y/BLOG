package until;

import annontation.DbCol;
import annontation.DbPriKey;
import annontation.DbTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Florence
 */
public class ReflectUtil {

    public static <T> String getIdField(T pojo) {
        Class<?> clazz=pojo.getClass();
        for (Field field:clazz.getDeclaredFields()){
            field.setAccessible(true);
            if (field.isAnnotationPresent(DbPriKey.class)){
                DbCol dbCol=field.getAnnotation(DbCol.class);
                return dbCol.value();
            }
        }
        return "null";
    }

    public static <T> String getUpdateSql(T pojo, Object[] paraArr) {
        Class<?> clazz =pojo.getClass();
        Map<String,String> map = new HashMap<>(10);
        Boolean isFirst=true;
        //将参数输入
        for (Field field:clazz.getDeclaredFields()){
            if (field.isAnnotationPresent(DbCol.class)){
                map.put(field.getName(),field.getAnnotation(DbCol.class).value());
            }
        }
        //获取表名
        DbTable table= clazz.getAnnotation(DbTable.class);
        String tableName="",sql;
        if (table!=null){
            tableName=table.value();
        }
        sql="UPDATE "+tableName+" SET "+map.get(paraArr[0])+" = ?";
        for (int i=1;i<paraArr.length-1;i++){
            String dbCol=map.get(paraArr[i]);
            if (dbCol!=null){
                sql+=",  "+dbCol+" = ?";
            }
        }
        sql+= " WHERE "+map.get(paraArr[paraArr.length-1])+" = ?";
        return sql;
    }
}
