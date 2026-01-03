package oncall.global.validator;

public class Pattern {

    public static final String MONTH = "(1|2|3|4|5|6|7|8|9|10|11|12)";
    public static final String WEEKDAYS = "(월|화|수|목|금|토|일)";

    public static final String MONTH_AND_WEEKDAYS_PATTERN =
            String.format("^%s+,+%s$", MONTH, WEEKDAYS);
}
