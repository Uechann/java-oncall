package oncall.domain.repository;

import oncall.domain.model.WorkDay;

import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WorkDayRepository {

    private List<WorkDay> workDays = new ArrayList<>();

    public WorkDayRepository() {}

    public void save(WorkDay workDay) {
        workDays.add(workDay);
    }

    public void findByMonth(Month month) {

    }

    public List<WorkDay> findAll() {
        return List.copyOf(workDays);
    }
}
