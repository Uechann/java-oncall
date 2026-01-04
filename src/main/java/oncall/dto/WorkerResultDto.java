package oncall.dto;

public record WorkerResultDto(
        int month,
        int day,
        String dayOfWeek,
        boolean isHolidayAndWeekDay,
        String workerName
) {

    public static WorkerResultDto of(int month, int day, String dayOfWeek, boolean isHolidayAndWeekDay, String workerName) {
        return new WorkerResultDto(month, day, dayOfWeek, isHolidayAndWeekDay, workerName);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(month).append("월 ").append(day).append("일 ");
        stringBuilder.append(dayOfWeek);

        if (isHolidayAndWeekDay) {
            stringBuilder.append("(휴일)");
        }

        stringBuilder.append(" ").append(workerName);

        return stringBuilder.toString();
    }
}
