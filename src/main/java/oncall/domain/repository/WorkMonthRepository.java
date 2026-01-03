package oncall.domain.repository;

import oncall.domain.model.WorkMonth;

import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

public class WorkMonthRepository {
    private Map<Month, WorkMonth> workMonths = new LinkedHashMap<>();

    public void save(WorkMonth workMonth) {
        workMonths.put(workMonth.getMonth(), workMonth);
    }
}
