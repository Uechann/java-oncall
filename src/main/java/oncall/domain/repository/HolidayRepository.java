package oncall.domain.repository;

import oncall.domain.model.Holiday;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class HolidayRepository {

    private List<Holiday> holidays = new ArrayList<>();

    public HolidayRepository() {}

    public void save(Holiday holiday) {
        holidays.add(holiday);
    }

    public List<Holiday> findByMonth(Month month) {
        return holidays.stream()
                .filter(holiday -> holiday.getMonth().equals(month))
                .toList();
    }
}
