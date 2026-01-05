package oncall.domain.repository;

import oncall.domain.model.WeekDayWorkers;
import oncall.domain.model.Worker;


public class WeekDayWorkerRepository {

    private WeekDayWorkers weekDayWorkers = new WeekDayWorkers();

    public void save(Worker worker) {
        weekDayWorkers.add(worker);
    }

    public WeekDayWorkers findAll() {
        return new WeekDayWorkers(weekDayWorkers.findAll());
    }
}
