package oncall.domain.service;

import oncall.domain.model.*;
import oncall.domain.repository.*;
import oncall.dto.WorkerResultDto;
import oncall.global.util.Parser;

import java.time.Month;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

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
            MonthDay monthDay = MonthDay.of(month, i);
            WeekDayHoliday weekDayHoliday = WeekDayHoliday.of(currentWeekDay);
            boolean isHoliday = holidays.stream().anyMatch(holiday -> holiday.getMonthDay().equals(monthDay));

            WorkDay workDay = WorkDay.create(
                    weekDayHoliday,
                    isHoliday,
                    workMonth,
                    monthDay,
                    currentWeekDay
            );
            workDayRepository.save(workDay);
//            System.out.println(workDay.getWorkMonth().getMonth().getValue() + "월 " + workDay.getMonthDay().getDayOfMonth() + "일 "
//                    + workDay.getDayOfWeek().getKoreaName() + "요일" + (workDay.isHoliday() ? "(휴일)" : "") + " 근무 일 초기화 완료!");
            currentWeekDay = currentWeekDay.getNext();
        }
    }

    // 평일 근무자 순번 초기화
    public void initializeWeekDayWorkers(String weekDaysWorkersInput) {
        List<String> workersInput = workersParser.parse(weekDaysWorkersInput);
        for (String workerName : workersInput) {
            Worker worker = new Worker(workerName);
            workerRepository.save(worker);
            weekDayWorkerRepository.save(worker);
        }
    }

    // 휴일 근무자 순번 초기화
    public void initializeHolidayWorkers(String holidayWorkersInput) {
        List<String> workersInput = workersParser.parse(holidayWorkersInput);
        for (String workerName : workersInput) {
            Worker worker = workerRepository.findByName(workerName)
                    .orElseThrow(() -> new IllegalArgumentException(WORKER_NOT_FOUND.getMessage()));
            holidayWorkerRepository.save(worker);
        }
    }

    public List<WorkerResultDto> assignWorkers() {
        List<WorkDay> workDays = workDayRepository.findAll();
        WeekDayWorkers weekDayWorkers = weekDayWorkerRepository.findAll();
        HolidayWorkers holidayWorkers = holidayWorkerRepository.findAll();

        int weekDaySequence = 0;
        int holidaySequence = 0;
        List<WorkerResultDto> workerResultDtos = new ArrayList<>();
        for (WorkDay workDay : workDays) {
            if (workDay.getWeekDayHoliday().equals(WeekDayHoliday.WEEKDAY) && !workDay.isHoliday()) {
                weekDaySequence = assignWeekDayWorker(workDay, weekDayWorkers, weekDaySequence, workerResultDtos);
            }

            if (workDay.getWeekDayHoliday().equals(WeekDayHoliday.HOLIDAY) || workDay.isHoliday()) {
                holidaySequence = assignHolidayWorker(workDay, holidayWorkers, holidaySequence, workerResultDtos);
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

    private static int assignHolidayWorker(WorkDay workDay, HolidayWorkers holidayWorkers, int holidaySequence, List<WorkerResultDto> workerResultDtos) {
        Worker worker = holidayWorkers.findBySequence(holidaySequence);

        // 이전 근무자가 현재 근무자와 겹칠 경우
        if (!workerResultDtos.isEmpty() &&
                workerResultDtos.get(workerResultDtos.size() - 1).workerName().equals(worker.getName())) {
            // 근무 순서 바꾸기
            holidayWorkers.changeSequence(holidaySequence);
            worker = holidayWorkers.findBySequence(holidaySequence);
        }

        workDay.assignWorker(worker);
        holidaySequence++;
//                System.out.println(workDay.getMonthDay() + workDay.getWorker().getName() + "배정 완료");
        return holidaySequence;
    }

    private static int assignWeekDayWorker(WorkDay workDay, WeekDayWorkers weekDayWorkers, int weekDaySequence, List<WorkerResultDto> workerResultDtos) {
        Worker worker = weekDayWorkers.findBySequence(weekDaySequence);

        // 이전 근무자가 현재 근무자와 겹칠 경우
        if (!workerResultDtos.isEmpty() &&
                workerResultDtos.get(workerResultDtos.size() - 1).workerName().equals(worker.getName())) {
            // 근무 순서 바꾸기
            weekDayWorkers.changeSequence(weekDaySequence);
            worker = weekDayWorkers.findBySequence(weekDaySequence);
        }

        workDay.assignWorker(worker);
        weekDaySequence++;
//                System.out.println(workDay.getMonthDay() + workDay.getWorker().getName() + "배정 완료");
        return weekDaySequence;
    }
}
