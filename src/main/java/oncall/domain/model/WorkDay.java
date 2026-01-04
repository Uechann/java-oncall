package oncall.domain.model;

import java.time.MonthDay;

public class WorkDay {

    // 평일 휴일 enum
    private WeekDayHoliday weekDayHoliday;

    // 법정 공휴일 여부
    private boolean isHoliday;

    // WorkMonth 필드
    private WorkMonth workMonth;

    // day 일 필드
    private MonthDay monthDay;

    // 요일 필드
    private CustomDayOfWeek dayOfWeek;

    // 근무자 필드
    private Worker worker;

    private WorkDay(
            WeekDayHoliday weekDayHoliday,
            boolean isHoliday,
            WorkMonth workMonth,
            MonthDay monthDay,
            CustomDayOfWeek dayOfWeek,
            Worker worker
    ) {
        this.weekDayHoliday = weekDayHoliday;
        this.isHoliday = isHoliday;
        this.workMonth = workMonth;
        this.monthDay = monthDay;
        this.dayOfWeek = dayOfWeek;
        this.worker = worker;
    }

    public static WorkDay create(
            WeekDayHoliday weekDayHoliday,
            boolean isHoliday,
            WorkMonth workMonth,
            MonthDay monthDay,
            CustomDayOfWeek dayOfWeek
    ) {
        return new WorkDay(
                weekDayHoliday,
                isHoliday,
                workMonth,
                monthDay,
                dayOfWeek,
                null
        );
    }

    public void assignWorker(Worker worker) {
        this.worker = worker;
    }

    public WorkMonth getWorkMonth() {
        return workMonth;
    }

    public WeekDayHoliday getWeekDayHoliday() {
        return weekDayHoliday;
    }
}
