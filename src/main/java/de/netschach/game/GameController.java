package de.netschach.game;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/game")
class GameController {

    @Autowired
    private GameService gameService;

    @PutMapping("/v2/bestmove")
    ResponseEntity<Void> onGameRequest(@RequestBody @Valid GameBestMoveRequest bestMoveRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("{}", bestMoveRequest);
        String callback = callbackAddr(bestMoveRequest.getCallback(), request);
        String requestId = bestMoveRequest.getRequestId();
        List<String> moves = bestMoveRequest.getMoves() == null ? Collections.emptyList() : bestMoveRequest.getMoves();
        log.info("request-id: {}, callback: {}", requestId, callback);
        gameService.bestMove(requestId, bestMoveRequest.getLevel(), bestMoveRequest.getElo(), bestMoveRequest.getTimeLimitMillis(), bestMoveRequest.getFen(), moves, callback);
        return ResponseEntity.noContent()
                .header("Request-Id", requestId)
                .header("Callback", callback)
                .build();
    }


    @PostMapping("/v2/status")
    ResponseEntity<GameStatusTO> onStatusRequest(@RequestBody @Valid GameStatusRequest statusRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("{}", statusRequest);
        List<String> moves = statusRequest.getMoves() == null ? Collections.emptyList() : statusRequest.getMoves();
        if (StringUtils.isEmpty(statusRequest.getFen())) {
            return ResponseEntity.status(200).body(gameService.gameStatus(moves));
        } else {
            return ResponseEntity.status(200).body(gameService.gameStatus(moves, statusRequest.getFen()));
        }

    }

    private String callbackAddr(GameResponseCallback callback, HttpServletRequest httpRequest) {
        String host = callback.getHost();
        return callback.getProtocol().name() +
                "://" +
                host +
                ":" +
                callback.getPort() +
                callback.getUri();
    }
}
