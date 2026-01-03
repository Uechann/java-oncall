package oncall.global.validator;

import java.time.MonthDay;

import static oncall.global.exception.ErrorMessage.INVALID_MONTH_AND_WEEKDAY;
import static oncall.global.validator.Pattern.MONTH_AND_WEEKDAYS_PATTERN;

public class Validator {

    public Validator() {}

    public void validateMonthAndWeekday(String monthAndWeekday) {
        if (!monthAndWeekday.matches(MONTH_AND_WEEKDAYS_PATTERN)){
            throw new IllegalArgumentException(INVALID_MONTH_AND_WEEKDAY.getMessage());
        }
    }
}
