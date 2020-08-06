package until;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Florence
 * 时间工具类
 */
public class TimeUntil {
    /**
     * date转String
     * @param date 日期对象
     * @return String日期
     */
    public static String getStringTime(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    /**
     * String转date
     * @param date 日期对象
     * @return date日期
     */
    public static Date getDateTime(String date){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
