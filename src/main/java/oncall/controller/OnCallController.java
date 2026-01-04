package oncall.controller;

import oncall.domain.service.OnCallService;
import oncall.global.validator.Validator;
import oncall.view.InputView;
import oncall.view.OutputView;

import static oncall.global.util.Retry.retry;

public class OnCallController {

    private final OnCallService onCallService;
    private final InputView inputView;
    private final OutputView outputView;
    private final Validator validator;

    public OnCallController(OnCallService onCallService, InputView inputView, OutputView outputView, Validator validator) {
        this.onCallService = onCallService;
        this.inputView = inputView;
        this.outputView = outputView;
        this.validator = validator;
    }

    public void run() {
        // 법정 공휴일 초기화
        onCallService.initializeHolidays();

        retry(() -> {
            String monthAndWeekDayInput = inputView.inputMonthAndWeekDay();
            validator.validateMonthAndWeekday(monthAndWeekDayInput);
            onCallService.initializeWorkMonth(monthAndWeekDayInput);
            return null;
        });

        retry(() -> {
            // 평일 근무자 순번 초기화
            String weekDaysWorkersInput = inputView.inputWeekDaysWorkers();
            validator.validateWeekDayWorkersInput(weekDaysWorkersInput);
            onCallService.initializeWeekDayWorkers(weekDaysWorkersInput);

            // 휴일 근무자 순번 초기화
            String holidaysWorkersInput = inputView.inputHolidaysWorkers();
            validator.validateHolidayWorkersInput(holidaysWorkersInput);
            onCallService.initializeHolidayWorkers(holidaysWorkersInput);
            return null;
        });

        // 근무자 배정

    }
}
