package de.netschach.event;

import de.netschach.chess2.GameBoard;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.context.ApplicationEvent;


@Getter
public class StockfishMovedEvent extends ApplicationEvent {

    private final String move;
    private final GameBoard gameBoard;
    private final String callback;
    private final Integer waitingTime;

    @Builder
    public StockfishMovedEvent(@NonNull String requestId, @NonNull String move, @NonNull GameBoard gameBoard, @NonNull String callback, Integer waitingTime) {
        super(requestId);
        this.move = move;
        this.gameBoard = gameBoard;
        this.callback = callback;
        this.waitingTime = waitingTime;
    }

    public String getRequestId() {
        return this.getSource();
    }

    @Override
    public String getSource() {
        return (String) super.getSource();
    }
}
