package de.netschach.game;

import de.netschach.security.HostsAllowed;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/game")
class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private HostsAllowed hostsAllowed;

    @PutMapping("/v2/bestmove")
    ResponseEntity<Void> onGameRequest(@RequestBody @Valid GameBestMoveRequest bestMoveRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("{}", bestMoveRequest);
        String callback = callbackAddr(bestMoveRequest.getCallback(), request);
        String requestId = bestMoveRequest.getRequestId();
        List<String> moves = bestMoveRequest.getMoves() == null ? Collections.emptyList() : bestMoveRequest.getMoves();
        log.info("request-id: {}, callback: {}", requestId, callback);
        if (StringUtils.isEmpty(bestMoveRequest.getFen())) {
            gameService.bestMove(requestId, bestMoveRequest.getLevel(), moves, callback);
        } else {
            gameService.bestMove(requestId, bestMoveRequest.getLevel(), bestMoveRequest.getFen(), moves, callback);
        }
        return ResponseEntity.noContent()
                .header("Request-Id", requestId)
                .header("Callback", callback)
                .build();
    }

    @PostMapping("/v2/status")
    ResponseEntity<GameStatusTO> onStatusRequest(@RequestBody @Valid GameStatusRequest statusRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("{}", statusRequest);
        hostsAllowed.check(request.getRemoteHost());
        List<String> moves = statusRequest.getMoves() == null ? Collections.emptyList() : statusRequest.getMoves();
        if (StringUtils.isEmpty(statusRequest.getFen())) {
            return ResponseEntity.status(200).body(gameService.gameStatus(moves));
        } else {
            return ResponseEntity.status(200).body(gameService.gameStatus(moves, statusRequest.getFen()));
        }

    }

    private String callbackAddr(GameResponseCallback callback, HttpServletRequest httpRequest) {
        String host = callbackServer(callback, httpRequest);
        return new StringBuilder(callback.getProtocol().name())
                .append("://")
                .append(host)
                .append(":")
                .append(callback.getPort())
                .append(callback.getUri()).toString();
    }

    private String callbackServer(GameResponseCallback callback, HttpServletRequest httpRequest) {
        hostsAllowed.check(httpRequest.getRemoteHost(), callback.getHost());
        return callback.getHost();
    }


    private String requestId() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
