package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ArrangementSetUtil {
    public static Set<Date> getArrangementDates(String start,String end){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Calendar calendar = new GregorianCalendar();
            Set<Date> set = new TreeSet<>();
            Date startTime = simpleDateFormat.parse(start);
            Date endTime = simpleDateFormat.parse(end);
            calendar.setTime(startTime);
            set.add(startTime);
            long persistentDays = (endTime.getTime() - startTime.getTime()) / (24 * 60 * 60 * 1000) - 1;
            for (long i = 0; i < persistentDays; i++) {
                calendar.add(Calendar.DATE,1);
                Date time = calendar.getTime();
                set.add(time);
            }
            set.add(endTime);
            return set;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
