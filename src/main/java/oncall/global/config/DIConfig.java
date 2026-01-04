package oncall.global.config;

import oncall.controller.OnCallController;
import oncall.domain.repository.*;
import oncall.domain.service.OnCallService;
import oncall.global.util.MonthAndWeekDayParser;
import oncall.global.util.Parser;
import oncall.global.util.WorkersParser;
import oncall.global.validator.Validator;
import oncall.view.InputView;
import oncall.view.OutputView;

public class DIConfig {

    private final WorkMonthRepository workMonthRepository = new WorkMonthRepository();
    private final HolidayRepository holidayRepository = new HolidayRepository();
    private final WorkDayRepository workDayRepository = new WorkDayRepository();
    private final WorkerRepository workerRepository = new WorkerRepository();
    private final WeekDayWorkerRepository weekDayWorkerRepository = new WeekDayWorkerRepository();
    private final HolidayWorkerRepository holidayWorkerRepository = new HolidayWorkerRepository();

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
                holidaysRepository(),
                workMonthRepository(),
                workDayRepository(),
                workerRepository(),
                weekDayWorkerRepository(),
                holidayWorkerRepository(),
                monthAndWeekDayParser(),
                workersParser()
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

    public Parser<String> workersParser() {
        return new WorkersParser();
    }

    public HolidayRepository holidaysRepository() {
        return holidayRepository;
    }

    public WorkMonthRepository workMonthRepository() {
        return workMonthRepository;
    }

    public WorkDayRepository workDayRepository() {
        return workDayRepository;
    }

    public WorkerRepository workerRepository() {
        return workerRepository;
    }

    public WeekDayWorkerRepository weekDayWorkerRepository() {
        return weekDayWorkerRepository;
    }

    public HolidayWorkerRepository holidayWorkerRepository() {
        return holidayWorkerRepository;
    }
}
