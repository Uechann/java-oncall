package oncall.domain.model;

import java.time.Month;
import java.time.MonthDay;

public class Holiday {

    private final Month month;

    private final MonthDay monthDay;

    private final String description;

    private Holiday(Month month, MonthDay monthDay, String description) {
        this.month = month;
        this.monthDay = monthDay;
        this.description = description;
    }

    public static Holiday of(Month month, MonthDay monthDay, String description) {
        return new Holiday(month, monthDay, description);
    }

    public Month getMonth() {
        return month;
    }
}
