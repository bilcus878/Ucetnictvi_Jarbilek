package cz.itnetwork.constant;

import java.time.LocalDate;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // gettery
    public int getDay() {
        return day;
    }
    public int getMonth() {
        return month;
    }
    public int getYear() {
        return year;
    }

    // statická metoda na vytvoření instance s dnešním datem
    public static Date today() {
        LocalDate now = LocalDate.now();
        return new Date(now.getDayOfMonth(), now.getMonthValue(), now.getYear());
    }

    @Override
    public String toString() {
        return day + "." + month + "." + year;
    }
}