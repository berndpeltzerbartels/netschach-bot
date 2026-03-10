package de.netschach.game;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
class GameBestMoveRequest {

    @NotNull
    @Length(min = 8, max = 20)
    private String requestId;

    /**
     * Callbackadresse, die mit dem Ergebnis aufgerufen werden soll.
     */
    private GameResponseCallback callback;

    /**
     * Sie Level von Stockfish, die von 0 (schlechtester) bis 20 (bester) reichen.
     * Es muss entweder level oder elo angegeben werden. Nicht beide.
     */
    @Min(0)
    @Max(20)
    private Integer level;


    /**
     * ELO Wert kalibriert bei ca 60 Sekunden pro Zug. 1320 ist ungefähr Level 0, 3190 ist ungefähr Level 20.
     * Es muss entweder level oder elo angegeben werden. Nicht beide.
     */
    @Min(1320)
    @Max(3190)
    private Integer elo;

    /**
     * Zeitlimit in Millisekunden, die Stockfish für die Berechnung des besten Zuges verwenden soll.
     */
    @Min(100)
    @Max(3600000)
    private int timeLimitMillis;

    /**
     * FEN-Notation einer Partie, Falls vorhanden, werden ggf. Züge (Feld „moves") auf diese Position angewendet.
     */
    private String fen;
    /**
     * Züge in in beliebigem Format. Normales JSON-Array.
     */
    private List<String> moves = new ArrayList<>();

}
