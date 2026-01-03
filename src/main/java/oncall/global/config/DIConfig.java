package oncall.global.config;

import oncall.controller.OnCallController;
import oncall.domain.repository.WorkMonthRepository;
import oncall.domain.service.OnCallService;
import oncall.global.util.MonthAndWeekDayParser;
import oncall.global.util.Parser;
import oncall.global.validator.Validator;
import oncall.view.InputView;
import oncall.view.OutputView;

public class DIConfig {

    private final WorkMonthRepository workMonthRepository = new WorkMonthRepository();

    public OnCallController oncallController() {
        return new OnCallController(
                onCallService(),
                inputView(),
                outputView(),
                validator()
        );
    }

    public OnCallService onCallService() {
        return new OnCallService(
                workMonthRepository(),
                monthAndWeekDayParser()
        );
    }

    public InputView inputView() {
        return new InputView();
    }

    public OutputView outputView() {
        return new OutputView();
    }

    public Validator validator() {
        return new Validator();
    }

    public Parser<String> monthAndWeekDayParser() {
        return new MonthAndWeekDayParser();
    }

    public WorkMonthRepository workMonthRepository() {
        return workMonthRepository;
    }
}
