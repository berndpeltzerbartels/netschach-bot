package de.netschach.stockfish;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class StockfishRunnerTest {

    @InjectMocks
    private StockfishRunner runner;

    @Test
    void getWaitingTime() throws InterruptedException {
        this.runner.addToQueue(StockfishTask.builder().gameId("1").level(20).timeLimit(500).build());
        this.runner.addToQueue(StockfishTask.builder().gameId("2").level(20).timeLimit(600).build());
        this.runner.addToQueue(StockfishTask.builder().gameId("5").level(20).timeLimit(400).build());
        Thread.sleep(200);
        assertThat(this.runner.getWaitingTime()).isEqualTo(1500);
    }
}
