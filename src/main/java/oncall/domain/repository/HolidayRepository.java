package oncall.domain.repository;

import oncall.domain.model.Holiday;

import java.util.ArrayList;
import java.util.List;

public class HolidayRepository {

    private List<Holiday> holidays = new ArrayList<>();

    public HolidayRepository() {}

    public void save(Holiday holiday) {
        holidays.add(holiday);
    }
}
