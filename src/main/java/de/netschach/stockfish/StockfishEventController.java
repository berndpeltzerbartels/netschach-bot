package de.netschach.stockfish;

import de.netschach.event.FindBestMoveEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StockfishEventController {

    @Autowired
    private StockfishDispatcher dispatcher;

    @Autowired
    private StockfishTaskBuilder taskBuilder;

    @EventListener
    public void onPlayerMoved(FindBestMoveEvent e) {
        this.dispatcher.dispatch(taskBuilder.create(e));
    }
}
