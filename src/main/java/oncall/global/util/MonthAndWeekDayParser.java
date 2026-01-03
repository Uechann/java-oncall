package oncall.global.util;

import java.util.Arrays;
import java.util.List;

public class MonthAndWeekDayParser implements Parser<String> {

    @Override
    public List<String> parse(String input) {
        return Arrays.stream(input.split(","))
                .toList();
    }
}
