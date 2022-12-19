package com.ccgautomation.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTools {

    private final SimpleDateFormat datePattern;
    private static final String DEFAULT_DATE_PATTERN = "MMddyyyy";

    /**
     * Automatically assigns the SimpleDateFormat pattern to "MM/dd/yyyy" and Time Zone to
     * "Greenwich Mean Time".
     */
    public DateTools() {
        this(DEFAULT_DATE_PATTERN, TimeZone.getTimeZone("Greenwich Mean Time"));
    }

    /**
     * Automatically assigns the Time Zone to "Greenwich Mean Time".
     * @param datePattern SimpleDateFormat pattern for String to Date conversion.
     */
    public DateTools(String datePattern) {
        this(datePattern, TimeZone.getTimeZone("Greenwich Mean Time"));
    }

    /**
     * Automatically assigns the SimpleDateFormat pattern to "MM/dd/yyyy".
     *
     * @param timeZone Assigns the time zone to base calculations in.
     */
    public DateTools(TimeZone timeZone) {
        this(DEFAULT_DATE_PATTERN, timeZone);
    }

    /**
     *
     * @param datePattern SimpleDateFormat string describing the format for Date and Time conversions.
     * @param timeZone Assigns the time zone to base calculations in.
     */
    public DateTools(String datePattern, TimeZone timeZone) {
        if (timeZone == null) throw new NullPointerException("TimeZone Object is null.");
        this.datePattern = new SimpleDateFormat(datePattern);
        this.datePattern.setTimeZone(timeZone);
    }

    /**
     * Returns the SimpleDateFormat pattern being used.
     * @return Date object assigned to the Date and Time of dateString.
     */
    public String getSimpleDateFormatPattern() {
        return datePattern.toLocalizedPattern();
    }

    /**
     *
     * @return The default date pattern.
     */
    public String getDefaultDatePattern() {
        return DEFAULT_DATE_PATTERN;
    }

    /**
     *
     * @return Display Name of the current Time Zone.
     */
    public String getTimeZoneDisplayName() {
        return datePattern.getTimeZone().getDisplayName();
    }

    /**
     *
     * @param date Date object to be converted.
     * @return Date and Time contained in the supplied Date Object formatted with the SimpleDateFormat assigned.
     */
    public String convertDateToStringFormat(Date date) {
        return datePattern.format(date);
    }

    /**
     * Converts a Date String to a Date value.  Returns ParseException on failure.
     * @param dateString Date to be converted.
     * @return Date object assigned to the date provided or null on failure.
     */
    public Date convertStringToDate(String dateString) throws ParseException {
        return datePattern.parse(dateString);
    }

}