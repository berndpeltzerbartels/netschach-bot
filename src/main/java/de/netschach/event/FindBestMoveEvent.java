package de.netschach.event;

import de.netschach.chess2.GameBoard;
import de.netschach.stockfish.Elo;
import de.netschach.stockfish.Level;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class FindBestMoveEvent extends ApplicationEvent {

    private final GameBoard gameBoard;
    private Level level;
    private Elo elo;
    private final int timeLimit;
    private final String callbackUrl;

    public FindBestMoveEvent(String requestId, GameBoard gameBoard, int timeLimit, String callbackUrl) {
        super(requestId);
        this.gameBoard = gameBoard;
        this.timeLimit = timeLimit;
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
