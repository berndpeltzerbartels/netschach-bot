package de.netschach.stockfish;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StockfishLevels {

    static void setStockfishLevelAttributes(int level, int moveIndex, StockfishTask task) {
        switch (level) {

            case 1:
                task.setLevel(0);
                task.setTimeLimit(1000);
                break;
            case 2:
                task.setLevel(2);
                task.setTimeLimit(1000);
                break;
            case 3:
                task.setLevel(5);
                task.setTimeLimit(1000);
                break;
            case 4:
                task.setLevel(10);
                task.setTimeLimit(1000);
                break;
            case 5:
                task.setLevel(15);
                task.setTimeLimit(1000);
                break;
            case 6:
                task.setLevel(20);
                task.setTimeLimit(1000);
                break;
            case 7:
                task.setLevel(20);
                if (moveIndex < 12) {
                    task.setTimeLimit(1500);
                } else {
                    task.setTimeLimit(5000);
                }
                break;
            default:
                task.setLevel(20);
                if (moveIndex < 12) {
                    task.setTimeLimit(4000);
                } else {
                    task.setTimeLimit(15000);
                }
        }
    }

}
