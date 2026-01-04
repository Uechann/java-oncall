package oncall.domain.repository;

import oncall.domain.model.WeekDayWorker;

import java.util.ArrayList;
import java.util.List;

public class WeekDayWorkerRepository {

    private List<WeekDayWorker> weekDayWorkers = new ArrayList<>();

    public void save(WeekDayWorker weekDayWorker) {
        weekDayWorkers.add(weekDayWorker);
    }
}
