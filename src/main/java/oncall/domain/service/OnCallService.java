package oncall.domain.service;

import oncall.domain.model.*;
import oncall.domain.repository.*;
import oncall.dto.WorkerResultDto;
import oncall.global.util.Parser;

import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static oncall.global.exception.ErrorMessage.WORKER_NOT_FOUND;

public class OnCallService {

    private final HolidayRepository holidayRepository;
    private final WorkMonthRepository workMonthRepository;
    private final WorkDayRepository workDayRepository;
    private final WorkerRepository workerRepository;
    private final WeekDayWorkerRepository weekDayWorkerRepository;
    private final HolidayWorkerRepository holidayWorkerRepository;
    private final Parser<String> monthAndWeekDayParser;
    private final Parser<String> workersParser;

    public OnCallService(
            HolidayRepository holidayRepository,
            WorkMonthRepository workMonthRepository,
            WorkDayRepository workDayRepository,
            WorkerRepository workerRepository,
            WeekDayWorkerRepository weekDayWorkerRepository,
            HolidayWorkerRepository holidayWorkerRepository,
            Parser<String> monthAndWeekDayParser,
            Parser<String> workersParser
    ) {
        this.holidayRepository = holidayRepository;
        this.workMonthRepository = workMonthRepository;
        this.workDayRepository = workDayRepository;
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
        CustomDayOfWeek customDayOfWeek = CustomDayOfWeek.of(monthAndWeekDay.get(1));
        Month month = Month.of(monthInput);
        List<Holiday> holidays = holidayRepository.findByMonth(month);
        WorkMonth workMonth = WorkMonth.create(month, customDayOfWeek, holidays);
        workMonthRepository.save(workMonth);

        // workDay 초기화
        int monthLength = month.length(false);
        CustomDayOfWeek currentWeekDay = workMonth.getStartDayOfWeek();
        for (int i = 1; i <= monthLength; i++) {
            // 요일
            MonthDay monthDay = MonthDay.of(month, i);

            // 평일 휴일 enum
            WeekDayHoliday weekDayHoliday = WeekDayHoliday.of(currentWeekDay);

            // 법정 공휴일 여부
            boolean isholiday = holidays.stream().anyMatch(holiday -> holiday.getMonthDay().equals(monthDay));

            WorkDay workDay = WorkDay.create(
                    weekDayHoliday,
                    isholiday,
                    workMonth,
                    monthDay,
                    currentWeekDay
            );
            workDayRepository.save(workDay);
//            System.out.println(workDay.getWorkMonth().getMonth().getValue() + "월 " + workDay.getMonthDay().getDayOfMonth() + "일 "
//                    + workDay.getDayOfWeek().getKoreaName() + "요일" + (workDay.isHoliday() ? "(휴일)" : "") + " 근무 일 초기화 완료!");

            // 다음 요일로 저장
            currentWeekDay = currentWeekDay.getNext();
        }

//        System.out.println("월과 시작 요일 저장 성공 !");
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

    public List<WorkerResultDto> assignWorkers() {
        List<WorkDay> workDays = workDayRepository.findAll();
        List<WeekDayWorker> weekDayWorkers = weekDayWorkerRepository.findAll();
        List<HolidayWorker> holidayWorkers = holidayWorkerRepository.findAll();

        int weekDaySequence = 0;
        int holidaySequence = 0;

        List<WorkerResultDto> workerResultDtos = new ArrayList<>();

        for (WorkDay workDay : workDays) {

            // 평일과 휴일
            if (workDay.getWeekDayHoliday().equals(WeekDayHoliday.WEEKDAY) && !workDay.isHoliday()) {
                WeekDayWorker weekDayWorker = weekDayWorkers.get((weekDaySequence++) % weekDayWorkers.size());
                Worker worker = weekDayWorker.getWorker();
                workDay.assignWorker(worker);
//                System.out.println(workDay.getMonthDay() + workDay.getWorker().getName() + "배정 완료");
            }

            if (workDay.getWeekDayHoliday().equals(WeekDayHoliday.HOLIDAY) || workDay.isHoliday()) {
                HolidayWorker holidayWorker = holidayWorkers.get((holidaySequence++) % holidayWorkers.size());
                Worker worker = holidayWorker.getWorker();
                workDay.assignWorker(worker);
//                System.out.println(workDay.getMonthDay() + workDay.getWorker().getName() + "배정 완료");

            }

            boolean isHolidayAndWeekDay = false;
            if (workDay.isHoliday() && workDay.getWeekDayHoliday().equals(WeekDayHoliday.WEEKDAY)) {
                isHolidayAndWeekDay = true;
            }

            workerResultDtos.add(WorkerResultDto.of(
                    workDay.getWorkMonth().getMonth().getValue(),
                    workDay.getMonthDay().getDayOfMonth(),
                    workDay.getDayOfWeek().getKoreaName(),
                    isHolidayAndWeekDay,
                    workDay.getWorker().getName()
            ));
        }
        return workerResultDtos;
    }
}
