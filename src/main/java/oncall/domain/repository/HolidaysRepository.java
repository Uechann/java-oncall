package oncall.domain.repository;

import oncall.domain.model.Holiday;

import java.util.ArrayList;
import java.util.List;

public class HolidaysRepository {

    private List<Holiday> holidays = new ArrayList<>();

    public HolidaysRepository() {}

    public void save(Holiday holiday) {
        holidays.add(holiday);
    }
}
