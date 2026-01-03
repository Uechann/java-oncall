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
        retry(() -> {
            String monthAndWeekDayInput = inputView.inputMonthAndWeekDay();
            validator.validateMonthAndWeekday(monthAndWeekDayInput);
            onCallService.initializeWorkMonth(monthAndWeekDayInput);
            return null;
        });

        retry(() -> {
            return null;
        });
    }
}
