package de.netschach.event;

import de.netschach.chess2.GameBoard;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class GameOverEvent extends ApplicationEvent {

    private String requestId;
    private String callback;
    private GameBoard gameBoard;

    public GameOverEvent(String requestId, GameBoard gameBoard, String callback) {
        super(gameBoard);
        this.gameBoard = gameBoard;
        this.requestId = requestId;
        this.callback = callback;
    }

}
