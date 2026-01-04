package oncall.global.validator;

import static oncall.global.exception.ErrorMessage.INVALID_MONTH_AND_WEEKDAY;
import static oncall.global.exception.ErrorMessage.INVALID_WORKERS_INPUT;
import static oncall.global.validator.Pattern.MONTH_AND_WEEKDAYS_PATTERN;
import static oncall.global.validator.Pattern.WORKERS_INPUT_PATTERN;

public class Validator {

    public Validator() {}

    public void validateMonthAndWeekday(String monthAndWeekday) {
        if (!monthAndWeekday.matches(MONTH_AND_WEEKDAYS_PATTERN)){
            throw new IllegalArgumentException(INVALID_MONTH_AND_WEEKDAY.getMessage());
        }
    }

    public void validateWeekDayWorkersInput(String weekDayWorkersInput) {
        validateWorkersInput(weekDayWorkersInput);

    }

    public void validateHolidayWorkersInput(String weekDayWorkersInput) {
        validateWorkersInput(weekDayWorkersInput);
    }

    private static void validateWorkersInput(String weekDayWorkersInput) {
        if (!weekDayWorkersInput.matches(WORKERS_INPUT_PATTERN)) {
            throw new IllegalArgumentException(INVALID_WORKERS_INPUT.getMessage());
        }
    }
}
