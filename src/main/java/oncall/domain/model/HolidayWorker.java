package oncall.domain.model;

public class HolidayWorker {

    private final Worker worker;
    private final int sequence;

    public HolidayWorker(Worker worker, int sequence) {
        this.worker = worker;
        this.sequence = sequence;
    }

    public static HolidayWorker create(Worker worker, int sequence) {
        return new HolidayWorker(worker, sequence);
    }
}
