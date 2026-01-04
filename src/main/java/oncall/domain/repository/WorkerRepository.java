package oncall.domain.repository;

import oncall.domain.model.Worker;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class WorkerRepository {

    private Map<String, Worker> workers = new LinkedHashMap<>();

    public void save(Worker worker) {
        workers.put(worker.getName(), worker);
    }

    public Optional<Worker> findByName(String workerName) {
        return Optional.ofNullable(workers.get(workerName));
    }
}
