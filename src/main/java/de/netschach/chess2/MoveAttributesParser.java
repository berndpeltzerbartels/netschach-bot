package de.netschach.chess2;

import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
class MoveAttributesParser {

    private static final List<AttributeExtractor> ATTRIBUTE_EXTRACTORS = List.of(
            new PawnMoveVeryShortAttributeExtractor(),
            new StockfishAttributeExtractor(),
            new LongNotationAttributeExtractor(),
            new NoFromAttributeExtractorUppercase(),
            new PawnMoveShortAttributeExtractor(),
            new NoFromAttributeExtractorLowercase(),
            new FromOnlyXAttributeExtractor(),
            new EnPassantAttributeExtractor());

    Set<MoveAttributes> parse(String move, Color color) {
        Optional<MoveAttributes> castling = parseCastling(move, color);
        if (castling.isPresent()) {
            return Collections.singleton(castling.get());
        }
        return moveAttributes(move);
    }

    private Set<MoveAttributes> moveAttributes(String move) {
        return ATTRIBUTE_EXTRACTORS.stream()
                .map(extractor -> extractor.getAttributes(move))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .peek(attr -> attr.setOrig(move))
                .collect(Collectors.toSet());
    }

    private Optional<MoveAttributes> parseCastling(String move, Color color) {
        if (move.equals("0-0") || move.equals("o-o") || move.equals("O-O")) {
            return Optional.of(MoveAttributes.builder()
                    .orig(move)
                    .color(color)
                    .moveType(MoveType.CASTLING_SHORT)
                    .fromX(Character.valueOf('e'))
                    .fromY(color == Color.WHITE ? 1 : 8)
                    .toX('g')
                    .toY(color == Color.WHITE ? 1 : 8)
                    .build());
        }
        if (move.equals("0-0-0") || move.equals("o-o-o") || move.equals("O-O-O")) {
            return Optional.of(MoveAttributes.builder()
                    .orig(move)
                    .color(color)
                    .moveType(MoveType.CASTLING_LONG)
                    .fromX(Character.valueOf('e'))
                    .fromY(color == Color.WHITE ? 1 : 8)
                    .toX('c')
                    .toY(color == Color.WHITE ? 1 : 8)
                    .build());
        }
        return Optional.empty();
    }

    static abstract class AttributeExtractor {

        abstract Pattern getPattern();

        Optional<MoveAttributes> getAttributes(String move) {
            Matcher matcher = getPattern().matcher(move);
            if (!matcher.find()) {
                return Optional.empty();
            }
            MoveAttributes attributes = new MoveAttributes();
            setDefaultValues(attributes);
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String value = matcher.group(i);
                if (value != null && !value.isEmpty()) {
                    update(i, attributes, value);
                }
            }
            return Optional.of(attributes);
        }

        abstract void update(int group, MoveAttributes attributes, String value);

        void setDefaultValues(MoveAttributes attributes) {
        }

    }


    static class StockfishAttributeExtractor extends AttributeExtractor {

        private static final Pattern PATTERN = Pattern.compile("([a-z]{1})([1-8]{1})([a-z]{1})([1-8]{1})/?([DLSTdlstQRBNqrbn]?)");

        @Override
        Pattern getPattern() {
            return PATTERN;
        }

        @Override
        void update(int group, MoveAttributes attributes, @NonNull String value) {
            switch (group) {
                case 1:
                    attributes.setFromX(value.charAt(0));
                    break;
                case 2:
                    attributes.setFromY(Integer.parseInt(value));
                    break;
                case 3:
                    attributes.setToX(value.charAt(0));
                    break;
                case 4:
                    attributes.setToY(Integer.parseInt(value));
                    break;
                case 5:
                    attributes.setSwapPieceType(Piece.pieceFor(value.charAt(0)));
                    attributes.setPieceType(Pawn.class);
                    break;
            }
        }

    }

    static class NoFromAttributeExtractorUppercase extends AttributeExtractor {
        private static final Pattern PATTERN = Pattern.compile("^([KDLST]?)([xX-]?)([a-h]{1})([1-8]{1})/?([DLSTdlst]?)");

        @Override
        Pattern getPattern() {
            return PATTERN;
        }

        @Override
        void setDefaultValues(MoveAttributes attributes) {
            attributes.setPieceType(Pawn.class);
        }

        @Override
        void update(int group, MoveAttributes attributes, String value) {
            switch (group) {
                case 1:
                    attributes.setPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
                case 2:
                    attributes.setCapturing(value.equalsIgnoreCase("x"));
                    break;
                case 3:
                    attributes.setToX(value.charAt(0));
                    break;
                case 4:
                    attributes.setToY(Integer.parseInt(value));
                    break;
                case 5:
                    attributes.setSwapPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
            }
        }

    }

    static class NoFromAttributeExtractorLowercase extends AttributeExtractor {
        private final Pattern PATTERN = Pattern.compile("^([lst]?)([xX-]?)([a-h]{1})([1-8]{1})/?([DLSTdlst]?)");

        @Override
        Pattern getPattern() {
            return PATTERN;
        }

        @Override
        void setDefaultValues(MoveAttributes attributes) {
            attributes.setPieceType(Pawn.class);
        }

        @Override
        void update(int group, MoveAttributes attributes, String value) {
            switch (group) {
                case 1:
                    attributes.setPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
                case 2:
                    attributes.setCapturing(value.equalsIgnoreCase("x"));
                    break;
                case 3:
                    attributes.setToX(value.charAt(0));
                    break;
                case 4:
                    attributes.setToY(Integer.parseInt(value));
                    break;
                case 5:
                    attributes.setSwapPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
            }
        }
    }

    static class PawnMoveShortAttributeExtractor extends AttributeExtractor {
        private final Pattern PATTERN = Pattern.compile("^([a-h]{1})([xX-]?)([a-h]{1})([1-8]{1})/?([DLSTdlst]?)");

        @Override
        Pattern getPattern() {
            return PATTERN;
        }

        @Override
        void setDefaultValues(MoveAttributes attributes) {
            attributes.setPieceType(Pawn.class);
        }

        @Override
        void update(int group, MoveAttributes attributes, String value) {
            switch (group) {
                case 1:
                    attributes.setFromX(value.charAt(0));
                    break;
                case 2:
                    attributes.setCapturing(value.equalsIgnoreCase("x"));
                    break;
                case 3:
                    attributes.setToX(value.charAt(0));
                    break;
                case 4:
                    attributes.setToY(Integer.parseInt(value));
                    break;
                case 5:
                    attributes.setSwapPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

    }

    static class PawnMoveVeryShortAttributeExtractor extends AttributeExtractor {
        private final Pattern PATTERN = Pattern.compile("^([a-h]{1})([1-8]{1})/?([DLSTdlst]?)");

        @Override
        Pattern getPattern() {
            return PATTERN;
        }

        @Override
        void setDefaultValues(MoveAttributes attributes) {
            attributes.setPieceType(Pawn.class);
        }

        @Override
        void update(int group, MoveAttributes attributes, String value) {
            switch (group) {
                case 1:
                    attributes.setToX(value.charAt(0));
                    break;
                case 2:
                    attributes.setToY(Integer.parseInt(value));
                    break;
                case 3:
                    attributes.setSwapPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    static class FromOnlyXAttributeExtractor extends AttributeExtractor {
        private final Pattern PATTERN = Pattern.compile("^([KDLST]?)([a-h]{1})([xX-]?)([a-h]{1})([1-8]?)/?([DLSTdlst]?)");

        @Override
        Pattern getPattern() {
            return PATTERN;
        }

        @Override
        void setDefaultValues(MoveAttributes attributes) {
            attributes.setPieceType(Pawn.class);
        }

        @Override
        void update(int group, MoveAttributes attributes, String value) {
            switch (group) {
                case 1:
                    attributes.setPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
                case 2:
                    attributes.setFromX(value.charAt(0));
                    break;
                case 3:
                    attributes.setCapturing(value.equalsIgnoreCase("x"));
                    break;
                case 4:
                    attributes.setToX(value.charAt(0));
                    break;
                case 5:
                    attributes.setToY(Integer.parseInt(value));
                    break;
                case 6:
                    attributes.setSwapPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
            }
        }
    }

    static class LongNotationAttributeExtractor extends AttributeExtractor {
        private final Pattern PATTERN = Pattern.compile("^([KDLSTkdlst]?)([a-h]{1})([1-8]{1})([xX-])([a-h]{1})([1-8]{1})/?([DLSTdlst]?)");


        @Override
        Pattern getPattern() {
            return PATTERN;
        }

        @Override
        void setDefaultValues(MoveAttributes attributes) {
            attributes.setPieceType(Pawn.class);
        }

        @Override
        void update(int group, MoveAttributes attributes, String value) {
            switch (group) {
                case 1:
                    attributes.setPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
                case 2:
                    attributes.setFromX(value.charAt(0));
                    break;
                case 3:
                    attributes.setFromY(Integer.parseInt(value));
                    break;
                case 4:
                    attributes.setCapturing(value.equalsIgnoreCase("x"));
                    break;
                case 5:
                    attributes.setToX(value.charAt(0));
                    break;
                case 6:
                    attributes.setToY(Integer.parseInt(value));
                    break;
                case 7:
                    attributes.setSwapPieceType(Piece.pieceFor(value.charAt(0)));
                    break;
            }
        }
    }

    static class EnPassantAttributeExtractor extends AttributeExtractor {
        private final Pattern PATTERN = Pattern.compile("^e\\.p\\.([a-h]{1})([1-8]?)([xX])?([a-h]{1})([1-8]?)");

        @Override
        Pattern getPattern() {
            return PATTERN;
        }

        @Override
        void setDefaultValues(MoveAttributes attributes) {
            attributes.setPieceType(Pawn.class);
        }

        @Override
        void update(int group, MoveAttributes attributes, String value) {
            switch (group) {
                case 1:
                    attributes.setFromX(value.charAt(0));
                    break;
                case 2:
                    attributes.setFromY(Integer.parseInt(value));
                    break;
                case 3:
                    attributes.setCapturing(true);
                    break;
                case 4:
                    attributes.setToX(value.charAt(0));
                    break;
                case 5:
                    attributes.setToY(Integer.parseInt(value));
                    break;
            }
        }
    }

}
