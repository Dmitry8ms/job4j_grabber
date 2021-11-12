package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;

import static java.util.Map.entry;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, Month> MONTHS = Map.ofEntries(
            entry("янв", Month.JANUARY), entry("фев", Month.FEBRUARY),
            entry("мар", Month.MARCH), entry("май", Month.MAY),
            entry("апр", Month.APRIL), entry("июн", Month.JUNE),
            entry("июл", Month.JULY), entry("авг", Month.AUGUST),
            entry("сен", Month.SEPTEMBER), entry("окт", Month.OCTOBER),
            entry("ноя", Month.NOVEMBER), entry("дек", Month.DECEMBER)
    );

    private static final Map<String, LocalDate> LOCAL_DATE_MAP = Map.ofEntries(
            entry("сегодня", LocalDate.now()), entry("вчера", LocalDate.now().minusDays(1))
    );

    @Override
    public LocalDateTime parse(String parse) {
        LocalDate date = null;
        LocalDateTime localDateTime = null;
        String[] dateTime = parse.split(",");
        String dateStr = dateTime[0].trim();
        String timeStr = dateTime[1].trim();
        String[] hourSec = timeStr.split(":");
        LocalTime time = LocalTime.of(Integer.parseInt(
                hourSec[0].trim()), Integer.parseInt(hourSec[1].trim()));
        String[] dayMonthYear = dateStr.split(" ");
        if (dayMonthYear.length == 3) {
            int day = Integer.parseInt(dayMonthYear[0].trim());
            Month month = MONTHS.get(dayMonthYear[1].trim());
            int year = Integer.parseInt("20" + dayMonthYear[2].trim());
            date = LocalDate.of(year, month, day);
        } else if (dayMonthYear.length == 1) {
            date = LOCAL_DATE_MAP.get(dayMonthYear[0].trim());
        }
        if (date != null) {
            localDateTime = LocalDateTime.of(date, time);
        }
        return localDateTime;
    }
}
