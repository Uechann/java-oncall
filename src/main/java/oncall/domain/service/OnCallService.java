package oncall.domain.service;

import oncall.domain.model.Holiday;
import oncall.domain.model.WorkMonth;
import oncall.domain.repository.HolidaysRepository;
import oncall.domain.repository.WorkMonthRepository;
import oncall.global.util.Parser;

import java.time.Month;
import java.time.MonthDay;
import java.util.List;

public class OnCallService {

    private final HolidaysRepository holidaysRepository;
    private final WorkMonthRepository workMonthRepository;
    private final Parser<String> monthAndWeekDayParser;

    public OnCallService(
            HolidaysRepository holidaysRepository,
            WorkMonthRepository workMonthRepository,
            Parser<String> monthAndWeekDayParser) {
        this.holidaysRepository = holidaysRepository;
        this.workMonthRepository = workMonthRepository;
        this.monthAndWeekDayParser = monthAndWeekDayParser;
    }


    //- 법정 공휴일 리스트
    //- 1/1 신정
    //- 3/1 삼일절
    //- 5/5 어린이날
    //- 6/6 현충일
    //- 8/15 광복절
    //- 10/3 개천절
    //- 10/9 한글날
    //- 12/25 성탄절
    public void initializeHolidays() {
        holidaysRepository.save(Holiday.of(Month.of(1), MonthDay.of(1,1), "신정"));
        holidaysRepository.save(Holiday.of(Month.of(3), MonthDay.of(3,1), "삼일절"));
        holidaysRepository.save(Holiday.of(Month.of(5), MonthDay.of(5,5), "어린이날"));
        holidaysRepository.save(Holiday.of(Month.of(6), MonthDay.of(6,6), "현충일"));
        holidaysRepository.save(Holiday.of(Month.of(8), MonthDay.of(8,15), "광복절"));
        holidaysRepository.save(Holiday.of(Month.of(10), MonthDay.of(10,3), "개천절"));
        holidaysRepository.save(Holiday.of(Month.of(10), MonthDay.of(10,9), "한글날"));
        holidaysRepository.save(Holiday.of(Month.of(12), MonthDay.of(12,25), "성탄절"));
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
