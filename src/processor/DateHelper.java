package processor;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateHelper {
    public static Date fromSql(String date) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:dd").parse(date);
    }

    public static Date fromSqlDate(String date) throws Exception{
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
    public static String toSqlDate(Date date ) throws  Exception {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }
    public static String toSql(Date date) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:dd").format(date);
    }
}
