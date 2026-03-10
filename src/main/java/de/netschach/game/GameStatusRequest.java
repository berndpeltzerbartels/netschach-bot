package de.netschach.game;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
class GameStatusRequest {

    private String fen;
    /**
     * Züge in in beliebigem Format. Normales JSON-Array.
     */
    @NotNull
    private List<String> moves;


}
