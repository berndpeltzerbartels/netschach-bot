package de.netschach.stockfish;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
class StockfishDispatcher {

    @Autowired
    private StockfishRunner runner1;

    @Autowired
    private StockfishRunner runner2;

    @Autowired
    private StockfishRunner runner3;

    @PostConstruct
    void setEngineIds() {
        this.runner1.setIndex(1);
        this.runner2.setIndex(2);
        this.runner3.setIndex(3);
    }

    void dispatch(StockfishTask task) {
        this.findProcessingRunnerByWaitingTime().addToQueue(task);
    }

    private StockfishRunner findProcessingRunnerByWaitingTime() {
        List<StockfishRunner> runners = Stream.of(runner1, runner2, runner3).sorted(this::compareByWaitingTime).collect(Collectors.toList());
        log.info("waiting-time runner1: " + runner1.getWaitingTime());
        log.info("waiting-time runner2: " + runner2.getWaitingTime());
        log.info("waiting-time runner3: " + runner3.getWaitingTime());
        StockfishRunner selectedRunner = runners.get(0);
        log.info("selected runner: runner" + selectedRunner.getIndex());
        return selectedRunner;

    }

    private int compareByWaitingTime(StockfishRunner runner1, StockfishRunner runner2) {
        return runner1.getWaitingTime().compareTo(runner2.getWaitingTime());
    }

}
