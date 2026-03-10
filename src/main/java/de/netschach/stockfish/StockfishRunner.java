package de.netschach.stockfish;

import de.netschach.event.StockfishMovedEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
class StockfishRunner {

    private StockfishEngine engine;

    private StockfishWorker stockfishWorker;

    @Setter
    @Getter
    private int index;

    @Autowired
    private ApplicationEventPublisher publisher;

    private BlockingQueue<StockfishTask> queue = new LinkedBlockingDeque<>();

    @PostConstruct
    void init() {
        this.engine = new StockfishEngine();
        this.stockfishWorker = new StockfishWorker();
        this.stockfishWorker.start();
    }

    @PreDestroy
    void closeEngine() {
        this.engine.close();
    }

    @PreDestroy
    void closeWorker() {
        this.stockfishWorker.doStop();
        this.stockfishWorker.interrupt();
    }

    Integer getWaitingTime() {
        return this.queue.stream()//
                .map(StockfishTask::getTimeLimit)//
                .collect(Collectors.summingInt(Integer::intValue));
    }

    void addToQueue(StockfishTask task) {
        task.setWaitingTime(getWaitingTime());
        new Thread(() -> {
            this.queue.add(task);
        }).start();
    }


    class StockfishWorker extends Thread {

        private boolean run = true;

        void doStop() {
            this.run = false;
        }

        @Override
        public void run() {
            while (this.run) {
                try {
                    this.openEngine();
                    StockfishTask task = queue.take();
                    String move = this.process(task);
                    publisher.publishEvent(StockfishMovedEvent.builder()
                            .requestId(task.getGameId())
                            .move(move)
                            .gameBoard(task.getGameBoard())
                            .callback(task.getCallback())
                            .waitingTime(task.getWaitingTime()).build());
                } catch (Exception e) {
                    e.printStackTrace();
                    log.warn(e.toString());
                }
            }
        }

        private void openEngine() throws IOException {
            if (!engine.isAlive()) {
                engine.open();
            }
        }

        private String process(StockfishTask queueElement) throws Exception {
            log.info("Engine {}: processing {}, level: {}, timelimit: {}ms", index, queueElement.getGameId(), queueElement.getLevel(), queueElement.getTimeLimit());
            engine.setLevel(queueElement.getLevel());
            engine.setGameByFen(queueElement.getFen());
            StockfishBestMoveResult result = engine.bestMoveWithTimelimit(queueElement.getTimeLimit());
            log.info("Engine " + index + ": processing " + queueElement.getGameId() + " done, best-move: " + result.getBestMove());
            return result.getBestMove();
        }

    }


}
