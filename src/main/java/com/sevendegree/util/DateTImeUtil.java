package com.sevendegree.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by aqiod on 2017/12/28.
 */
public class DateTimeUtil {

    //joda-time
    //str -> Date
    //Date -> str

    public static final String STANDARD_FORMAT =  "yyyy-MM-dd HH:mm:ss";

    public static Date strToDate(String dateTimeString, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr);
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeString);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dateTimeString) {
        return strToDate(dateTimeString, STANDARD_FORMAT);
    }

    public static String dateToStr(Date date) {
        return dateToStr(date, STANDARD_FORMAT);
    }


}
