package de.netschach.event;

import de.netschach.chess2.GameBoard;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FindBestMoveEvent extends ApplicationEvent {

    private final GameBoard gameBoard;
    private final int level;
    private final String callbackUrl;

    public FindBestMoveEvent(String requestId, GameBoard gameBoard, int level, String callbackUrl) {
        super(requestId);
        this.gameBoard = gameBoard;
        this.level = level;
        this.callbackUrl = callbackUrl;
    }

    public String getRequestId() {
        return this.getSource();
    }

    @Override
    public String getSource() {
        return (String) super.getSource();
    }
}
