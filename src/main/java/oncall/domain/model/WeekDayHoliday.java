package oncall.domain.model;

import java.time.DayOfWeek;

public enum WeekDayHoliday {
    WEEKDAY("평일"),
    HOLIDAY("휴일");

    private String name;

    WeekDayHoliday(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static WeekDayHoliday of(CustomDayOfWeek customDayOfWeek) {
        if (customDayOfWeek.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                customDayOfWeek.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            return WeekDayHoliday.HOLIDAY;
        }
        return WeekDayHoliday.WEEKDAY;
    }
}
