package oncall.domain.model;

public class WeekDayWorker {

    private final Worker worker;
    private final int sequence;

    public WeekDayWorker(Worker worker, int sequence) {
        this.worker = worker;
        this.sequence = sequence;
    }

    public static WeekDayWorker create(Worker worker, int sequence) {
        return new WeekDayWorker(worker, sequence);
    }
}
