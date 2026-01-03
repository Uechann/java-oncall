package oncall.controller;

import oncall.global.validator.Validator;
import oncall.view.InputView;
import oncall.view.OutputView;

import static oncall.global.util.Retry.retry;

public class OncallController {

    private final InputView inputView;
    private final OutputView outputView;
    private final Validator validator;

    public OncallController(InputView inputView, OutputView outputView, Validator validator) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.validator = validator;
    }

    public void run() {
        retry(() -> {
            String monthAndWeekDayInput = inputView.inputMonthAndWeekDay();
            validator.validateMonthAndWeekday(monthAndWeekDayInput);
            return null;
        });

        retry(() -> {


        })
    }
}
