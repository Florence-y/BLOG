package until;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Florence
 */
public class ArrayUtil {
    public static final int step=2;
    public static final boolean odd=true;
    public static final boolean even=false;
    public static <T> T[] getArrByOddOrEven(T[] sourceArr,boolean isOddOrEven){
        List<T> list =new LinkedList<>();
        //奇数位
        if (isOddOrEven){
            for (int i=0;i<sourceArr.length-1;i+=step){
                list.add(sourceArr[i]);
            }
            return (T[]) list.toArray();
        }
        for (int i=1;i<sourceArr.length;i+=step){
            list.add(sourceArr[i]);
        }
        return (T[]) list.toArray();
    }
}
