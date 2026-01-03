package oncall.global.exception;

public enum ErrorMessage {
    // 공통
    INPUT_EMPTY_OR_BLANK("[ERROR] 입력값이 비어있거나 공백입니다."),

    // 월과 요일
    INVALID_MONTH_AND_WEEKDAY("ERROR] 유효하지 않은 입력 값입니다. 다시 입력해 주세요.");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
