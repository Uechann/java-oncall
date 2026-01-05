package oncall.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeekDayWorkers {

    private List<Worker> workersSequence = new ArrayList<>();

    public WeekDayWorkers() {
    }

    public WeekDayWorkers(List<Worker> workersSequence) {
        this.workersSequence = workersSequence;
    }

    public void add(Worker worker) {
        workersSequence.add(worker);
    }

    public List<Worker> findAll() {
        return new ArrayList<>(workersSequence);
    }

    public Worker findBySequence(int sequence) {
        return workersSequence.get(sequence % workersSequence.size());
    }

    public int size() {
        return workersSequence.size();
    }

    // 현재 순서 뒤 순서와 바꾸기
    public void changeSequence(int sequence) {
        int currentSequence = sequence % workersSequence.size();
        int nextSequence = sequence + 1 % workersSequence.size();

        Collections.swap(workersSequence, currentSequence, nextSequence);
    }
}
