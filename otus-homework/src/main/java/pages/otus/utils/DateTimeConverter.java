package pages.otus.utils;

import java.time.LocalDate;

public class DateTimeConverter {

    public static String getNumericMonth(String month) {
        if (month.toLowerCase().contains("янв")) {
            return "01";
        }
        if (month.toLowerCase().contains("февр")) {
            return "02";
        }
        if (month.toLowerCase().contains("март")) {
            return "03";
        }
        if (month.toLowerCase().contains("апрел")) {
            return "04";
        }
        if (month.toLowerCase().contains("ма")) {
            return "05";
        }
        if (month.toLowerCase().contains("июн")) {
            return "06";
        }
        if (month.toLowerCase().contains("июл")) {
            return "07";
        }
        if (month.toLowerCase().contains("авгус")) {
            return "08";
        }
        if (month.toLowerCase().contains("сентябр")) {
            return "09";
        }
        if (month.toLowerCase().contains("октябр")) {
            return "10";
        }
        if (month.toLowerCase().contains("ноябр")) {
            return "11";
        }
        if (month.toLowerCase().contains("декабр")) {
            return "12";
        }
        throw new IllegalArgumentException("Месяц не найден");
    }
}
