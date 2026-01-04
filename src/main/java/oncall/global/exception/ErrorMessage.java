package oncall.global.exception;

public enum ErrorMessage {
    // 공통
    INPUT_EMPTY_OR_BLANK("[ERROR] 입력값이 비어있거나 공백입니다."),

    // 월과 요일
    INVALID_MONTH_AND_WEEKDAY("ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요."),
    INVALID_WEEKDAY_KOREA_NAME("[ERROR] 해당되는 한글 요일이 없습니다."),
    WEEKDAY_NOT_FOUND("[ERROR] 해당 요일이 없습니다."),

    // 근무자
    INVALID_WORKERS_INPUT("[ERROR] 유효하지 않은 근무자 입력 형식입니다. 다시 입력해주세요"),
    WORKER_NOT_FOUND("[ERROR] 해당 근무자가 없습니다.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
