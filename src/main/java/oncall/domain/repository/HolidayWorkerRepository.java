package oncall.domain.repository;

import oncall.domain.model.HolidayWorker;

import java.util.ArrayList;
import java.util.List;

public class HolidayWorkerRepository {

    private List<HolidayWorker> holidayWorkers = new ArrayList<>();

    public void save(HolidayWorker holidayWorker) {
        holidayWorkers.add(holidayWorker);
    }

    public List<HolidayWorker> findAll() {
        return List.copyOf(holidayWorkers);
    }
}
