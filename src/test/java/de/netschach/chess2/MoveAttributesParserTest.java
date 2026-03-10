package de.netschach.chess2;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Collection;

class MoveAttributesParserTest {

    private MoveAttributesParser moveAttributesParser = new MoveAttributesParser();

    @Test
    void e4() {
        assertHasMatch("B__-e4", moveAttributesParser.parse("e4", Color.WHITE));
    }


    @Test
    void sf3() {
        assertHasMatch("S__-f3", moveAttributesParser.parse("Sf3", Color.WHITE));
    }


    @Test
    void sxf3() {
        assertHasMatch("S__xf3", moveAttributesParser.parse("Sxf3", Color.WHITE));
    }

    @Test
    void sG1F3() {
        assertHasMatch("Sg1-f3", moveAttributesParser.parse("Sg1-f3", Color.WHITE));
    }

    @Test
    void exC3() {
        assertHasMatch("Be_xf3", moveAttributesParser.parse("exf3", Color.WHITE));
    }

    @Test
    void e2e4() {
        assertHasMatch("_e2-e4", moveAttributesParser.parse("e2e4", Color.WHITE));
    }

    @Test
    void d7d5() {
        assertHasMatch("_d7-d5", moveAttributesParser.parse("d7d5", Color.BLACK));
    }


    @Test
    void e2Dashe4() {
        assertHasMatch("Be2-e4", moveAttributesParser.parse("e2-e4", Color.WHITE));
    }

    @Test
    void d7Dashd5() {
        assertHasMatch("Bd7-d5", moveAttributesParser.parse("d7-d5", Color.BLACK));
    }


    @Test
    void e2xf3() {
        assertHasMatch("Be2xf3", moveAttributesParser.parse("e2xf3", Color.WHITE));
    }

    @Test
    void d7xe6() {
        assertHasMatch("Bd7xe6", moveAttributesParser.parse("d7xe6", Color.BLACK));
    }

    @Test
    void e8D() {
        assertHasMatch("B__-e8D", moveAttributesParser.parse("e8/D", Color.WHITE));
    }


    @Test
    void e8d() {
        assertHasMatch("B__-e8D", moveAttributesParser.parse("e8d", Color.WHITE));
    }

    @Test
    void epdxc3() {
        //assertHasMatch("", moveAttributesParser.parse("e.p.dxc3", Color.WHITE)); // TODO
    }

    @Test
    void sc3() {
        assertHasMatch("S__-c3", moveAttributesParser.parse("sc3", Color.WHITE));
    }

    @Test
    void dxe5() {
        assertHasMatch("Bd_xe5", moveAttributesParser.parse("dxe5", Color.WHITE));
    }


    @Test
    void c7c8q() {
        assertHasMatch("Bc7-c8D", moveAttributesParser.parse("c7c8q", Color.WHITE));
    }


    private void assertHasMatch(String moveLong, Collection<MoveAttributes> moveAttributes) {
        if (moveAttributes.stream().noneMatch(attr -> matches(moveLong, attr))) {
            throw new AssertionFailedError("no match for " + moveLong);
        }
    }

    private boolean matches(String moveLong, MoveAttributes attributes) {
        char pieceChar = moveLong.charAt(0);
        char fromX = moveLong.charAt(1);
        int fromY = Character.getNumericValue(moveLong.charAt(2));
        char delimiter = moveLong.charAt(3);
        char toX = moveLong.charAt(4);
        int toY = Character.getNumericValue(moveLong.charAt(5));
        Character swap = moveLong.length() > 6 ? moveLong.charAt(6) : null;
        if (pieceChar == 'B') {
            if (attributes.getPieceType() != Pawn.class)
                return false;

        } else if (pieceChar != '_') {
            if (Piece.pieceFor(pieceChar) != attributes.getPieceType())
                return false;
        }
        if (fromX != '_' && (attributes.getFromX() == null || fromX != attributes.getFromX())) {
            return false;
        }
        if (fromY != -1 && (attributes.getFromY() == null || fromY != attributes.getFromY())) {
            return false;
        }
        if (toX != '_' && (attributes.getToX() == null || toX != attributes.getToX())) {
            return false;
        }
        if (toY != -1 && (attributes.getToY() == null || toY != attributes.getToY())) {
            return false;
        }
        if (delimiter == 'x' && true != attributes.getCapturing()) {
            return false;
        }
        if (swap != null) {
            if (Piece.pieceFor(swap) != attributes.getSwapPieceType()) {
                return false;
            }
        }
        return true;
    }
}