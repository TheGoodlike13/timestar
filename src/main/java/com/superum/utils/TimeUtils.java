package com.superum.utils;

import org.joda.time.Chronology;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TimeUtils {

    public static TimeZone getDefaultJavaTimeZone() {
        return DEFAULT_JAVA_TIME_ZONE;
    }

    public static DateTimeZone getDefaultTimeZone() {
        return DEFAULT_TIME_ZONE;
    }

    public static Chronology getDefaultChronology() {
        return DEFAULT_CHRONOLOGY;
    }

    // Assumes yyyy-MM-dd
    public static java.util.Date fromString(String string) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(getDefaultJavaTimeZone());
        return format.parse(string);
    }

    // PRIVATE

    private static final TimeZone DEFAULT_JAVA_TIME_ZONE = TimeZone.getTimeZone("UTC");

    private static final DateTimeZone DEFAULT_TIME_ZONE = DateTimeZone.UTC;
    private static final Chronology DEFAULT_CHRONOLOGY = ISOChronology.getInstanceUTC();

}
