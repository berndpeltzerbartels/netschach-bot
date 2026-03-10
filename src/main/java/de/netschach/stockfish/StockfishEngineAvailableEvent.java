package de.netschach.stockfish;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class StockfishEngineAvailableEvent extends ApplicationEvent {

    private int engine;

    StockfishEngineAvailableEvent(StockfishEngine stockfishEngine, int engine) {
        super(stockfishEngine);
        this.engine = engine;
    }

    @Override
    public StockfishEngine getSource() {
        return (StockfishEngine) super.getSource();
    }
}
