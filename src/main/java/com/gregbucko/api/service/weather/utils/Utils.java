package com.gregbucko.api.service.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class should not be instantiated.");
    }

    public static String getDateUTC(Date date) {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));

        return dateFormatGmt.format(date);
    }
}
