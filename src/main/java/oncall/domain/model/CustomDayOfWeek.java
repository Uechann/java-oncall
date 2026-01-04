package oncall.domain.model;

import java.time.DayOfWeek;

public enum CustomDayOfWeek {

    MON(DayOfWeek.MONDAY, "월"),
    TUE(DayOfWeek.TUESDAY, "화"),
    WED(DayOfWeek.WEDNESDAY, "수"),
    THU(DayOfWeek.THURSDAY, "목"),
    FRI(DayOfWeek.FRIDAY, "금"),
    SAT(DayOfWeek.SATURDAY, "토"),
    SUN(DayOfWeek.SUNDAY, "일");

    CustomDayOfWeek(DayOfWeek dayOfWeek, String koreaName) {
        this.dayOfWeek = dayOfWeek;
        this.koreaName = koreaName;
    }

    private final DayOfWeek dayOfWeek;
    private final String koreaName;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getKoreaName() {
        return koreaName;
    }
}
