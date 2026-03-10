package de.netschach.chess2;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

import static de.netschach.chess2.ChessUtils.kingPosition;


@Data
public class GameBoard {

    private final Map<Position, Piece> pieces;
    private final CastlingMetaData castlingMetaDataWhite;
    private final CastlingMetaData castlingMetaDataBlack;
    private final Draw3Counter draw3Counter;
    private final Draw50Counter draw50Counter;
    private final Color colorOnMove;

    private Position positionKingWhite;
    private Position positionKingBlack;
    private Move move;
    private Position potentialEnPassantCapturePosition;

    private List<? extends Move> allLegalMoves;
    private int moveNumber; // Halbzuege
    private GameStatus status = GameStatus.DEFAULT;

    private GameBoard(GameBoard board, Move move) {
        colorOnMove = move.getPiece().getColor().opposite();
        positionKingWhite = board.positionKingWhite;
        positionKingBlack = board.positionKingBlack;
        castlingMetaDataWhite = new CastlingMetaData(board.castlingMetaDataWhite);
        castlingMetaDataBlack = new CastlingMetaData(board.castlingMetaDataBlack);
        draw3Counter = new Draw3Counter(board.draw3Counter);
        draw50Counter = new Draw50Counter(board.draw50Counter);
        pieces = new HashMap<>(board.pieces);
        moveNumber = board.getMoveNumber() + 1;
        this.move = move;
        this.move.setPrevious(board.getMove());
        this.move.setIndex(moveNumber);
        this.move.doMove(this);
        castlingMetaDataWhite.update(move);
        castlingMetaDataBlack.update(move);
        draw3Counter.update(this);
        draw50Counter.update(this.move);
        if (King.class.isInstance(move.getPiece())) {
            if (move.getPiece().getColor() == Color.WHITE) {
                positionKingWhite = move.getTo();
            } else {
                positionKingBlack = move.getTo();
            }
        }
        checkKingPositions();
    }

    GameBoard(Map<Position, Piece> pieces, Color colorOnMove, boolean shortCastlingWhite, boolean longCastlingWhite, boolean shortCastlingBlack, boolean longCastlingBlack, Position potentialEnPassantCapturePosition, Integer moveCountDraw50, Integer moveNumber) {
        this.pieces = pieces;
        this.colorOnMove = colorOnMove;
        this.moveNumber = moveNumber != null ? moveNumber : 0;
        this.potentialEnPassantCapturePosition = potentialEnPassantCapturePosition;
        positionKingWhite = kingPosition(pieces, Color.WHITE);
        positionKingBlack = kingPosition(pieces, Color.BLACK);
        castlingMetaDataWhite = new CastlingMetaData(Color.WHITE, !longCastlingWhite, !shortCastlingWhite);
        castlingMetaDataBlack = new CastlingMetaData(Color.BLACK, !longCastlingBlack, !shortCastlingBlack);
        draw3Counter = new Draw3Counter();
        draw50Counter = moveCountDraw50 == null ? new Draw50Counter() : new Draw50Counter(moveCountDraw50);
        draw3Counter.update(this);
    }


    private GameBoard() {
        this.pieces = new HashMap<>();
        this.moveNumber = 0;
        colorOnMove = Color.WHITE;
        positionKingWhite = Position.E1;
        positionKingBlack = Position.E8;
        castlingMetaDataWhite = new CastlingMetaData(Color.WHITE, false, false);
        castlingMetaDataBlack = new CastlingMetaData(Color.BLACK, false, false);
        draw3Counter = new Draw3Counter();
        draw50Counter = new Draw50Counter();
        draw3Counter.update(this);
    }

    private void checkKingPositions() {
        if (pieces.get(positionKingWhite) == null)
            throw new IllegalStateException();
        if (!King.class.isInstance(pieces.get(positionKingWhite)))
            throw new IllegalStateException();
        if (pieces.get(positionKingWhite).getColor() != Color.WHITE)
            throw new IllegalStateException();
        if (pieces.get(positionKingBlack) == null)
            throw new IllegalStateException();
        if (!King.class.isInstance(pieces.get(positionKingBlack)))
            throw new IllegalStateException();
        if (pieces.get(positionKingBlack).getColor() != Color.BLACK)
            throw new IllegalStateException();
    }

    public static GameBoard emptyBoard() {
        return new GameBoard();
    }

    public static GameBoard startPosition() {
        GameBoard board = new GameBoard();
        board.pieces.put(new Position("a2"), new Pawn(Color.WHITE));
        board.pieces.put(new Position("b2"), new Pawn(Color.WHITE));
        board.pieces.put(new Position("c2"), new Pawn(Color.WHITE));
        board.pieces.put(new Position("d2"), new Pawn(Color.WHITE));
        board.pieces.put(new Position("e2"), new Pawn(Color.WHITE));
        board.pieces.put(new Position("f2"), new Pawn(Color.WHITE));
        board.pieces.put(new Position("g2"), new Pawn(Color.WHITE));
        board.pieces.put(new Position("h2"), new Pawn(Color.WHITE));

        board.pieces.put(new Position("a1"), new Rook(Color.WHITE));
        board.pieces.put(new Position("b1"), new Knight(Color.WHITE));
        board.pieces.put(new Position("c1"), new Bishop(Color.WHITE));
        board.pieces.put(new Position("d1"), new Queen(Color.WHITE));
        board.pieces.put(new Position("e1"), new King(Color.WHITE));
        board.pieces.put(new Position("f1"), new Bishop(Color.WHITE));
        board.pieces.put(new Position("g1"), new Knight(Color.WHITE));
        board.pieces.put(new Position("h1"), new Rook(Color.WHITE));


        board.pieces.put(new Position("a7"), new Pawn(Color.BLACK));
        board.pieces.put(new Position("b7"), new Pawn(Color.BLACK));
        board.pieces.put(new Position("c7"), new Pawn(Color.BLACK));
        board.pieces.put(new Position("d7"), new Pawn(Color.BLACK));
        board.pieces.put(new Position("e7"), new Pawn(Color.BLACK));
        board.pieces.put(new Position("f7"), new Pawn(Color.BLACK));
        board.pieces.put(new Position("g7"), new Pawn(Color.BLACK));
        board.pieces.put(new Position("h7"), new Pawn(Color.BLACK));

        board.pieces.put(new Position("a8"), new Rook(Color.BLACK));
        board.pieces.put(new Position("b8"), new Knight(Color.BLACK));
        board.pieces.put(new Position("c8"), new Bishop(Color.BLACK));
        board.pieces.put(new Position("d8"), new Queen(Color.BLACK));
        board.pieces.put(new Position("e8"), new King(Color.BLACK));
        board.pieces.put(new Position("f8"), new Bishop(Color.BLACK));
        board.pieces.put(new Position("g8"), new Knight(Color.BLACK));
        board.pieces.put(new Position("h8"), new Rook(Color.BLACK));
        return board;
    }

    public static GameBoardBuilder builder() {
        return new GameBoardBuilder();
    }

    Piece getPiece(Position position) {
        if (position == null) return null;// Spart Prüfung der Grenzen
        return pieces.get(position);
    }

    List<? extends Move> allLegalMoves() {
        if (allLegalMoves == null) {
            allLegalMoves = pieces.entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .filter(e -> e.getValue().getColor() == colorOnMove)
                    .flatMap(entry -> entry.getValue().getMoves(entry.getKey(), this))
                    .filter(this::isNoIllegalSelfCheck)
                    .collect(Collectors.toList());
        }
        return allLegalMoves;
    }


    private boolean isNoIllegalSelfCheck(Move move) {
        return !doMove(move).isCheckAgainst(move.getPiece().getColor());
    }

    boolean isCheckAgainst(Color color) {
        Position kingPosition = color == Color.WHITE ? positionKingWhite : positionKingBlack;
        return pieces.entrySet().stream()
                .filter(e -> e.getValue() != null)
                .filter(e -> e.getValue().getColor() == color.opposite())
                .flatMap(entry -> entry.getValue().getMoves(entry.getKey(), this))
                .anyMatch(move -> move.getTo().equals(kingPosition));
    }

    public GameBoard doMove(Move move) {
        return new GameBoard(this, move);
    }

    boolean longCastlingAllowed(Color color) {
        if (color == Color.WHITE) {
            return !castlingMetaDataWhite.getNoLongCastling();
        } else {
            return !castlingMetaDataBlack.getNoLongCastling();
        }
    }

    boolean shortCastlingAllowed(Color color) {
        if (color == Color.WHITE) {
            return !castlingMetaDataWhite.getNoShortCastling();
        } else {
            return !castlingMetaDataBlack.getNoShortCastling();
        }
    }

    Piece getPiece(String position) {
        return getPiece(new Position(position));
    }

    public List<Move> getMoves() {
        List<Move> moves = new ArrayList<>();
        Move m = move;
        while (m != null) {
            moves.add(m);
            m = m.getPrevious();
        }
        Collections.reverse(moves);
        return moves;
    }


    @Getter
    public class CastlingMetaData {
        private final Color color;
        private Boolean noLongCastling;
        private Boolean noShortCastling;

        CastlingMetaData(CastlingMetaData castlingMetaData) {
            this.color = castlingMetaData.color;
            this.noShortCastling = castlingMetaData.noShortCastling;
            this.noLongCastling = castlingMetaData.noLongCastling;
        }

        CastlingMetaData(Color color, @NonNull Boolean noLongCastling, @NonNull Boolean noShortCastling) {
            this.color = color;
            this.noLongCastling = Boolean.valueOf(noLongCastling);
            this.noShortCastling = Boolean.valueOf(noShortCastling);
        }

        void update(Move move) {
            if (move.getPiece().getColor() == color) {

                if (move.getPiece() instanceof King) {
                    noLongCastling = true;
                    noShortCastling = true;
                }
                if (move.getPiece() instanceof Rook) {
                    if (color == Color.WHITE) {
                        if (move.getFrom().equals(Position.A1)) {
                            noLongCastling = true;
                        } else if (move.getFrom().equals(Position.H1)) {
                            noShortCastling = true;
                        }
                    } else {
                        if (move.getFrom().equals(Position.A8)) {
                            noLongCastling = true;
                        } else if (move.getFrom().equals(Position.H8)) {
                            noShortCastling = true;
                        }
                    }
                }
            } else {
                if (color == Color.WHITE) {
                    if (move.getTo().equals(Position.A1)) {
                        noLongCastling = true;
                    } else if (move.getTo().equals(Position.H1)) {
                        noShortCastling = true;
                    }
                } else {
                    if (move.getTo().equals(Position.A8)) {
                        noLongCastling = true;
                    } else if (move.getTo().equals(Position.H8)) {
                        noShortCastling = true;
                    }
                }
            }
        }
    }

    @NoArgsConstructor
    static class Draw3Counter {
        private final Map<Integer, Integer> positionOccurences = new HashMap<>();

        @Getter
        private boolean draw;

        Draw3Counter(Draw3Counter counter) {
            this.positionOccurences.putAll(counter.positionOccurences);
            if (counter.draw)
                positionOccurences.clear();
        }

        void update(GameBoard gameBoard) {
            Integer key = hash(gameBoard);
            int count = positionOccurences.getOrDefault(key, 0);
            positionOccurences.put(key, ++count);
            if (count >= 3) draw = true;
        }

        static int hash(GameBoard gameBoard) {
            TreeMap<Position, Character> m = new TreeMap<>();
            gameBoard.getPieces().entrySet().forEach(e -> m.put(e.getKey(), e.getValue().getPieceChar()));
            return m.entrySet().stream().map(e -> e.getKey() + ":" + e.getValue()).collect(Collectors.joining(",")).hashCode();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Draw50Counter {
        private int count;
        private boolean draw;

        Draw50Counter(int count) {
            this.count = count;
        }

        Draw50Counter(Draw50Counter counter) {
            this.count = counter.draw ? 0 : counter.count;
        }

        void update(Move move) {
            if (move.getCapturedPiece() == null && !Pawn.class.isInstance(move.getPiece())) {
                count++;
                if (count >= 50) draw = true;
            } else {
                count = 0;
            }
        }
    }
}


