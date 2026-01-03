package oncall.domain.service;

import oncall.domain.model.WorkMonth;
import oncall.domain.repository.WorkMonthRepository;
import oncall.global.util.Parser;

import java.time.Month;
import java.util.List;

public class OnCallService {

    private final WorkMonthRepository workMonthRepository;
    private final Parser<String> monthAndWeekDayParser;

    public OnCallService(WorkMonthRepository workMonthRepository, Parser<String> monthAndWeekDayParser) {
        this.workMonthRepository = workMonthRepository;
        this.monthAndWeekDayParser = monthAndWeekDayParser;
    }

    public void initializeWorkMonth(String monthAndWeekDayInput) {
        List<String> monthAndWeekDay = monthAndWeekDayParser.parse(monthAndWeekDayInput);

        int monthInput = Integer.parseInt(monthAndWeekDay.get(0));
        String weekDayInput = monthAndWeekDay.get(1);

        Month month = Month.of(monthInput);
        workMonthRepository.save(WorkMonth.create(month, weekDayInput));
        System.out.println("월과 시작 요일 저장 성공 !");
    }
}
