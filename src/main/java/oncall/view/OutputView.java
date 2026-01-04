package oncall.view;

import oncall.dto.WorkerResultDto;

import java.util.List;

public class OutputView {
    public void outputWorkersResult(List<WorkerResultDto> workerResultDtos) {
        for (WorkerResultDto workerResultDto : workerResultDtos) {
            System.out.println(workerResultDto);
        }
    }
}
