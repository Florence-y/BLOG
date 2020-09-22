package until;

import annontation.DbCol;
import annontation.DbPriKey;

import java.lang.reflect.Field;

/**
 * @author Florence
 */
public class ReflectUtil {

    public static <T> String getIdField(T pojo) {
        Class clazz=pojo.getClass();
        for (Field field:clazz.getDeclaredFields()){
            field.setAccessible(true);
            if (field.isAnnotationPresent(DbPriKey.class)){
                DbCol dbCol=field.getAnnotation(DbCol.class);
                return dbCol.value();
            }
        }
        return "null";
    }
}
