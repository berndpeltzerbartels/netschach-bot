package de.netschach.event;

import de.netschach.chess2.GameBoard;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class FindBestMoveByFenEvent extends ApplicationEvent {

    private final GameBoard gameBoard;
    private final String fen;
    private final int level;
    private final String callbackUrl;

    public FindBestMoveByFenEvent(String requestId, GameBoard gameBoard, String fen, int level, String callbackUrl) {
        super(requestId);
        this.gameBoard = gameBoard;
        this.fen = fen;
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
