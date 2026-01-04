package oncall.domain.model;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static oncall.global.exception.ErrorMessage.INVALID_MONTH_AND_WEEKDAY;

public class WorkMonth {

    // 월
    private final Month month;

    // 시작 요일
    private final CustomDayOfWeek startDayOfWeek;

    // 법정 공휴일 리스트
    private final List<Holiday> holidays;

    // workday 리스트

    private WorkMonth(Month month, CustomDayOfWeek startDayOfWeek, List<Holiday> holidays) {
        this.month = month;
        this.startDayOfWeek = startDayOfWeek;
        this.holidays = holidays;
    }

    public static WorkMonth create(Month month, CustomDayOfWeek customDayOfWeek, List<Holiday> holidays) {
        return new WorkMonth(month, customDayOfWeek, holidays);
    }

    public Month getMonth() {
        return month;
    }

    public CustomDayOfWeek getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }
}
