package oncall.domain.service;

import oncall.domain.model.*;
import oncall.domain.repository.*;
import oncall.global.util.Parser;

import java.time.Month;
import java.time.MonthDay;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static oncall.global.exception.ErrorMessage.WORKER_NOT_FOUND;

public class OnCallService {

    private final HolidayRepository holidayRepository;
    private final WorkMonthRepository workMonthRepository;
    private final WorkerRepository workerRepository;
    private final WeekDayWorkerRepository weekDayWorkerRepository;
    private final HolidayWorkerRepository holidayWorkerRepository;
    private final Parser<String> monthAndWeekDayParser;
    private final Parser<String> workersParser;

    public OnCallService(
            HolidayRepository holidayRepository,
            WorkMonthRepository workMonthRepository,
            WorkerRepository workerRepository,
            WeekDayWorkerRepository weekDayWorkerRepository,
            HolidayWorkerRepository holidayWorkerRepository,
            Parser<String> monthAndWeekDayParser,
            Parser<String> workersParser
    ) {
        this.holidayRepository = holidayRepository;
        this.workMonthRepository = workMonthRepository;
        this.workerRepository = workerRepository;
        this.weekDayWorkerRepository = weekDayWorkerRepository;
        this.holidayWorkerRepository = holidayWorkerRepository;
        this.monthAndWeekDayParser = monthAndWeekDayParser;
        this.workersParser = workersParser;
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
        holidayRepository.save(Holiday.of(Month.of(1), MonthDay.of(1, 1), "신정"));
        holidayRepository.save(Holiday.of(Month.of(3), MonthDay.of(3, 1), "삼일절"));
        holidayRepository.save(Holiday.of(Month.of(5), MonthDay.of(5, 5), "어린이날"));
        holidayRepository.save(Holiday.of(Month.of(6), MonthDay.of(6, 6), "현충일"));
        holidayRepository.save(Holiday.of(Month.of(8), MonthDay.of(8, 15), "광복절"));
        holidayRepository.save(Holiday.of(Month.of(10), MonthDay.of(10, 3), "개천절"));
        holidayRepository.save(Holiday.of(Month.of(10), MonthDay.of(10, 9), "한글날"));
        holidayRepository.save(Holiday.of(Month.of(12), MonthDay.of(12, 25), "성탄절"));
    }

    public void initializeWorkMonth(String monthAndWeekDayInput) {
        List<String> monthAndWeekDay = monthAndWeekDayParser.parse(monthAndWeekDayInput);

        int monthInput = Integer.parseInt(monthAndWeekDay.get(0));
        String weekDayInput = monthAndWeekDay.get(1);
        List<Holiday> holidays = holidayRepository.findByMonth(Month.of(monthInput));
        workMonthRepository.save(WorkMonth.create(Month.of(monthInput), weekDayInput, holidays));

        System.out.println("월과 시작 요일 저장 성공 !");
    }

    // 평일 근무자 순번 초기화
    public void initializeWeekDayWorkers(String weekDaysWorkersInput) {
        AtomicInteger sequence = new AtomicInteger(0);
        List<String> workersInput = workersParser.parse(weekDaysWorkersInput);

        for (String workerName : workersInput) {
            Worker worker = new Worker(workerName);
            workerRepository.save(worker);
            weekDayWorkerRepository.save(WeekDayWorker.create(worker, sequence.incrementAndGet()));
        }
    }

    // 휴일 근무자 순번 초기화
    public void initializeHolidayWorkers(String holidayWorkersInput) {
        AtomicInteger sequence = new AtomicInteger(0);
        List<String> workersInput = workersParser.parse(holidayWorkersInput);

        for (String workerName : workersInput) {
            Worker worker = workerRepository.findByName(workerName)
                    .orElseThrow(() -> new IllegalArgumentException(WORKER_NOT_FOUND.getMessage()));
            holidayWorkerRepository.save(HolidayWorker.create(worker, sequence.incrementAndGet()));
        }
    }
}
