package io.bxbxbai.zhuanlan.utils;

import android.text.format.DateUtils;
import io.bxbxbai.zhuanlan.App;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author bxbxbai
 */
public class Utils {

    private static final int MINUTE = 60;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY = HOUR * 24;
    private static final int MONTH = DAY * 30;
    private static final int YEAR = MONTH * 12;

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);


    public static String getAuthorAvatarUrl(String origin, String userId, String size) {
        origin = origin.replace(ZhuanLanApi.TEMPLATE_ID, userId);
        return origin.replace(ZhuanLanApi.TEMPLATE_SIZE, size);
    }

    public static String convertPublishTime(String time) {
        time = time.split("T")[0];
        try {
            Date date = FORMAT.parse(time);

            long s = TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - date.getTime());

            long year = s / YEAR;
            if (year != 0) {
                return year + "年前";
            }


            long month = s / MONTH;
            if (month != 0) {
                return month + "月前";
            }

            long day = s / DAY;
            if (day != 0) {
                return day + "天前";
            }

            return "今天";

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

}
