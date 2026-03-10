package de.netschach.chess2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TestUtil {
    /*
    white chess king	♔	U+2654	&#9812;
white chess queen	♕	U+2655	&#9813;
white chess rook	♖	U+2656	&#9814;
white chess bishop	♗	U+2657	&#9815;
white chess knight	♘	U+2658	&#9816;
white chess pawn	♙	U+2659	&#9817;
black chess king	♚	U+265A	&#9818;
black chess queen	♛	U+265B	&#9819;
black chess rook	♜	U+265C	&#9820;
black chess bishop	♝	U+265D	&#9821;
black chess knight	♞	U+265E	&#9822;
black chess pawn	♟	U+265F	&#9823;

     */

    static Map<Character, Character> pieces = new HashMap<>();

    static {
        pieces.put('K', '\u265A');
        pieces.put('Q', '\u265B');
        pieces.put('R', '\u265C');
        pieces.put('B', '\u265D');
        pieces.put('N', '\u265E');
        pieces.put('P', '\u265F');

        pieces.put('k', '\u2654');
        pieces.put('q', '\u2655');
        pieces.put('r', '\u2656');
        pieces.put('b', '\u2657');
        pieces.put('n', '\u2658');
        pieces.put('p', '\u2659');
    }


    public static Stream<String> moves(String path) throws IOException {
        List<String> list = new ArrayList<>();
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String line;
            while (((line = reader.readLine()) != null)) {
                list.add(line);
            }
            return list.stream();

        } finally {
            reader.close();
        }
    }


    public static Stream<String> lines(String path) throws IOException {
        List<String> list = new ArrayList<>();
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String line;
            while (((line = reader.readLine()) != null)) {
                list.add(line);
            }
            return list.stream();

        } finally {
            reader.close();
        }
    }

    public static String line(String path, int lineIndex) throws IOException {
        int index = 0;
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String line;
            while (((line = reader.readLine()) != null)) {
                if (index++ == lineIndex)
                    return line;
            }
            return null;


        } finally {
            reader.close();
        }
    }

    public static void dump(GameBoard board) {

        System.out.println(" +---+---+---+---+---+---+---+---+");
        for (int row = 8; row > 0; row--) {
            System.out.print(row);
            System.out.print("|");
            for (char col = 'a'; col < 'i'; col++) {
                System.out.print(" ");
                Position position = new Position(col, row);
                Piece piece = board.getPieces().get(position);
                if (piece == null)
                    System.out.print(" ");
                else {
                    System.out.print(toSymbol(piece));

                }
                System.out.print(" |");
            }
            System.out.println("\n +---+---+---+---+---+---+---+---+");
        }

        System.out.println("   a   b   c   d   e   f   g   h");
    }

    private static Character toSymbol(Piece piece) {
        char c;
        switch (piece.getClass().getSimpleName()) {
            case "King":
                c = 'k';
                break;
            case "Queen":
                c = 'q';
                break;
            case "Rook":
                c = 'r';
                break;
            case "Bishop":
                c = 'b';
                break;
            case "Knight":
                c = 'n';
                break;
            case "Pawn":
                c = 'p';
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (piece.getColor() == Color.WHITE)
            c = Character.toUpperCase(c);
        return pieces.get(c);
    }

}
