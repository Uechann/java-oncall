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

    public static WorkMonth create(Month month, String dayOfWeek, List<Holiday> holidays) {
        // 월 화 수 목 금 토 일 -> CustomDayOfWeek으로 변환
        CustomDayOfWeek week = Arrays.stream(CustomDayOfWeek.values())
                .filter(customDayOfWeek -> customDayOfWeek.getKoreaName().equals(dayOfWeek))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_MONTH_AND_WEEKDAY.getMessage()));

        return new WorkMonth(month, week, holidays);
    }

    public Month getMonth() {
        return month;
    }

    public CustomDayOfWeek getStartDayOfWeek() {
        return startDayOfWeek;
    }
}
