package de.netschach.fen;

import de.netschach.chess2.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FenParser {

    private static Set<Character> PIECE_CHARS = Set.of('K', 'Q', 'R', 'B', 'N', 'P', 'k', 'q', 'r', 'b', 'n', 'p');

    private FenToGameboardParser fenToGameboardParser = new FenToGameboardParser();
    private GameBoardToFenParser gameBoardToFenParser = new GameBoardToFenParser();

    public GameBoard parseFen(String fen) throws IllegalFenException {
        return fenToGameboardParser.parse(fen);
    }

    public String parseGameBoard(GameBoard gameBoard) {
        return gameBoardToFenParser.parse(gameBoard);
    }


    static class GameBoardToFenParser {

        String parse(GameBoard gameBoard) {
            StringBuilder fen = new StringBuilder();
            fen.append(parsePiecesBlock(gameBoard));
            fen.append(" ");
            fen.append(colorOnMoveBlock(gameBoard));
            fen.append(" ");
            fen.append(castlingBlock(gameBoard));
            fen.append(" ");
            fen.append(enPassantBlock(gameBoard));
            fen.append(" ");
            fen.append(draw50Block(gameBoard));
            fen.append(" ");
            fen.append(moveCountBlock(gameBoard));
            return fen.toString();
        }

        private String moveCountBlock(GameBoard gameBoard) {
            if (gameBoard.getMoveNumber() == 0)
                return Integer.toString(1);
            if (gameBoard.getMoveNumber() % 2 == 0)

                return Integer.toString(gameBoard.getMoveNumber() / 2);
            return Integer.toString((gameBoard.getMoveNumber() + 1) / 2);
        }

        private String draw50Block(GameBoard gameBoard) {
            return Integer.toString(gameBoard.getDraw50Counter().getCount());
        }

        private String enPassantBlock(GameBoard gameBoard) {
            Position position = gameBoard.getPotentialEnPassantCapturePosition();
            return position != null ? position.toString() : "-";
        }

        private String castlingBlock(GameBoard gameBoard) {
            StringBuilder s = new StringBuilder();
            if (!gameBoard.getCastlingMetaDataWhite().getNoShortCastling()) {
                s.append("K");
            }
            if (!gameBoard.getCastlingMetaDataWhite().getNoLongCastling()) {
                s.append("Q");
            }
            if (!gameBoard.getCastlingMetaDataBlack().getNoShortCastling()) {
                s.append("k");
            }
            if (!gameBoard.getCastlingMetaDataBlack().getNoLongCastling()) {
                s.append("q");
            }
            if (s.toString().isEmpty()) {
                s.append("-");
            }
            return s.toString();
        }

        private String parsePiecesBlock(GameBoard gameBoard) {
            List<String> rows = new ArrayList<>();
            for (int y = 8; y > 0; y--) {
                rows.add(parseRow(gameBoard, y));
            }
            return rows.stream().collect(Collectors.joining("/"));
        }

        private String parseRow(GameBoard gameBoard, int y) {
            StringBuilder row = new StringBuilder();
            int emptyFields = 0;
            for (char x = 'a'; x <= 'h'; x++) {
                Position position = new Position(x, y);
                Piece piece = gameBoard.getPieces().get(position);
                if (piece == null) {
                    emptyFields++;
                } else {
                    if (emptyFields != 0) {
                        row.append(emptyFields);
                    }
                    emptyFields = 0;
                    row.append(piece.getPieceChar());
                }
            }
            if (emptyFields != 0) {
                row.append(emptyFields);
            }
            return row.toString();
        }

        private char colorOnMoveBlock(GameBoard gameBoard) {
            return gameBoard.getColorOnMove() == Color.WHITE ? 'w' : 'b';
        }


    }

    static class FenToGameboardParser {
        GameBoard parse(String fen) throws IllegalFenException {
            List<String> blocks = Arrays.asList(fen.split(" "));
            if (blocks.size() < 4 || blocks.size() > 6)
                throw new IllegalFenException("unexpected block-size: " + blocks.size(), fen);

            Map<Position, Piece> pieces = pieces(blocks.get(0));
            Color onMove = onMove(blocks.get(1));
            String castlingBlock = validateCastlingBlock(blocks.get(2));
            Position enPassant = validateEnPassantBlock(blocks.get(3), pieces, onMove);
            return GameBoard.builder()
                    .addPieces(pieces)
                    .setColorOnMove(onMove)
                    .setShortCastlingWhite(castlingBlock.contains("K"))
                    .setLongCastlingWhite(castlingBlock.contains("Q"))
                    .setShortCastlingBlack(castlingBlock.contains("k"))
                    .setLongCastlingBlack(castlingBlock.contains("q"))
                    .setPotentialEnPassantCapturePosition(enPassant)
                    .moveCountDraw50(moveCountDraw50(blocks))
                    .moveIndex(moveNumber(blocks, onMove))
                    .build();
        }

        Integer moveCountDraw50(List<String> blocks) {
            if (blocks.size() < 5) return null;
            String s = blocks.get(4);
            if (s.trim().equals("-")) return null;
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new IllegalFenException("move count must be an integer", blocks.get(4));
            }
        }

        int moveNumber(List<String> blocks, Color onMove) {
            if (blocks.size() < 6) return 0;
            String s = blocks.get(5);
            if (s.trim().equals("-")) return 0;
            try {
                int i2 = Integer.parseInt(s) * 2;
                return onMove == Color.WHITE ? i2 - 1 : i2;

            } catch (NumberFormatException e) {
                throw new IllegalFenException("move count must be an integer", blocks.get(4));
            }
        }


        String validateCastlingBlock(String s) {
            doAssert(!s.matches("[^K^Q^k^q^-]+"), s, "castling flag does not match KQkq");
            return s;
        }

        Position validateEnPassantBlock(String s, Map<Position, Piece> pieces, Color onMove) {
            if (s.trim().equals("-")) return null;
            Position position = new Position(s);
            doAssert(pieces.get(position) instanceof Pawn, s, "en-passant flag does not match. no pawn at " + position);
            doAssert(pieces.get(position).getColor() != onMove, s, "en-passant flag does not match. pawn must not be of color " + onMove);
            return position;
        }

        private Map<Position, Piece> pieces(String block) {
            FenBoard fenBoard = new FenBoard(block);
            fenBoard.read();
            return fenBoard.getPieces();
        }

        private Color onMove(String block) {
            if (block.equals("w")) {
                return Color.WHITE;
            }
            if (block.equals("b")) {
                return Color.BLACK;
            }
            throw new IllegalFenException("second block must be \"w\" or \"b\"", block);
        }

        static class FenBoard {

            @Getter
            private Map<Position, Piece> pieces = new HashMap<>();
            private final String[] rows;

            FenBoard(String block) {
                rows = block.split("/");
                doAssert(rows.length == 8, block, "board representation must have 8 rows instead of " + rows.length);
            }

            void read() {
                for (int i = 0; i < 8; i++) {
                    readRow(8 - i, rows[i]);
                }
            }

            private void readRow(int y, String row) {
                FenRow fenRow = new FenRow(row, y, pieces);
                fenRow.read();
            }

        }

        static class FenRow {

            private final String orig;
            private final char[] chars;
            private final int y;
            private final Map<Position, Piece> pieces;
            private int index;
            private char x;

            FenRow(String orig, int y, Map<Position, Piece> pieces) {
                this.orig = orig;
                this.chars = orig.toCharArray();
                this.x = 'a';
                this.y = y;
                this.pieces = pieces;
                this.index = -1;
            }

            void read() {
                while (next()) {
                    if (digit()) {
                        readDigit();
                    } else if (pieceChar()) {
                        readPieceChar();
                    } else {
                        throw new IllegalFenException("unexpected char " + chars[index], orig);
                    }
                }
            }

            void readDigit() {
                int digit = asInt(chars[index]);
                x = add(x, digit);
            }


            void readPieceChar() {
                char c = chars[index];
                Piece piece = createPiece(c);
                Position position = currentPosition();
                pieces.put(position, piece);
                x = add(x, 1);
            }

            private boolean next() {
                if (index < chars.length - 1) {
                    index++;
                    return true;
                }
                return false;
            }

            private boolean pieceChar() {
                return PIECE_CHARS.contains(chars[index]);
            }

            private Position currentPosition() {
                return new Position(x, y);
            }

            private boolean digit() {
                return Character.isDigit(chars[index]);
            }

            static char add(char c, int value) {
                return (char) (c + value);
            }

            static int asInt(char c) {
                return c - 48;
            }

            static Piece createPiece(char c) {
                Class<? extends Piece> pieceType = Piece.pieceFor(c);
                return Piece.createInstance(pieceType, Character.isUpperCase(c) ? Color.WHITE : Color.BLACK);
            }
        }


        static void doAssert(boolean b, String expression, String message) {
            if (!b) throw new IllegalFenException(message, expression);
        }
    }
}

