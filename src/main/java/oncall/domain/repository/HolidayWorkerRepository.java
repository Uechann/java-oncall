package oncall.domain.repository;

import oncall.domain.model.HolidayWorkers;
import oncall.domain.model.Worker;


public class HolidayWorkerRepository {

    private HolidayWorkers holidayWorkers = new HolidayWorkers();

    public void save(Worker worker) {
        holidayWorkers.add(worker);
    }

    public HolidayWorkers findAll() {
        return new HolidayWorkers(holidayWorkers.findAll());
    }
}
