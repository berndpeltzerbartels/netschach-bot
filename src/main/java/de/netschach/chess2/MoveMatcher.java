package de.netschach.chess2;

import de.netschach.StreamUtil;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
class MoveMatcher {

    Move findMatchingMove(Collection<MoveAttributes> attributes, GameBoard board, String orig) {
        return attributes.stream()
                .map(attr -> findMatchingMove(attr, board))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(StreamUtil.findOne(() -> new MoveParserException("illegal move " + orig)));
    }

    Optional<? extends Move> findMatchingMove(MoveAttributes attributes, GameBoard board) {
        // Checking for captured piece will fail, because it's set later and always null
        // for instances of move
        try {
            return board.allLegalMoves().stream()
                    .filter(move -> matches(attributes.getFromX(), move.getFromX()))
                    .filter(move -> matches(attributes.getFromY(), move.getFromY()))
                    .filter(move -> matches(attributes.getToX(), move.getToX()))
                    .filter(move -> matches(attributes.getToY(), move.getToY()))
                    .filter(move -> matches(attributes.getColor(), move.getPiece().getColor()))
                    .filter(move -> matches(attributes.getPieceType(), move.getPiece().getClass()))
                    .filter(move -> matchingPawnPromotion(attributes, move))
                    .findAny();
        } catch (MoveParserException e) {
            throw new IllegalMoveException(attributes.getOrig(), e.getMessage());
        }
    }

    private boolean matches(Object valueAttribute, Object valueMove) {
        if (valueMove == null) {
            return valueAttribute == null;
        } else {
            if (valueAttribute == null) return true;
            return valueAttribute.equals(valueMove);
        }
    }


    private boolean matchingPawnPromotion(MoveAttributes attributes, Move move) {
        if (attributes.getSwapPieceType() == null) {
            return true;
        }
        if (!PawnPromotion.class.isInstance(move)) {
            return false;
        }
        PawnPromotion pawnPromotion = (PawnPromotion) move;
        return pawnPromotion.getSwapPieceType().equals(attributes.getSwapPieceType());

    }

}
