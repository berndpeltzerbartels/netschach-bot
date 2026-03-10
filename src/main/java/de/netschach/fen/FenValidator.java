package de.netschach.fen;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class FenValidator implements ConstraintValidator<Fen, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        List<String> blocks = Arrays.asList(value.split(" "));
        if (blocks.size() < 4 || blocks.size() > 6)
            return false;
        if (!this.isValidBoard(blocks.get(0)))
            return false;
        if (!this.isValidColor(blocks.get(1)))
            return false;
        if (!this.isValidCastlingFlag(blocks.get(2)))
            return false;
        if (!this.isValidEnPassantFlag(blocks.get(3)))
            return false;
        if (blocks.size() > 4) {
            if (!this.isValidCounter(blocks.get(4)))
                return false;
        }
        if (blocks.size() > 5) {
            if (!this.isValidCounter(blocks.get(5)))
                return false;
        }

        return true;
    }

    boolean isValidBoard(String board) {
        String[] lines = board.split("/");
        if (lines.length != 8)
            return false;
        for (String line : lines) {
            if (!this.isValidLine(line))
                return false;
        }
        return true;
    }

    boolean isValidLine(String line) {
        if (!line.matches("[1-8KQRBNPkqrbnp]+"))
            return false;
        int n = 0;
        for (char c : line.toCharArray()) {
            if (c > '0' && c < '9')
                n += c - '0';
            else
                n++;
        }
        return n == 8;
    }

    boolean isValidColor(String board) {
        return board.equals("w") || board.equals("b");
    }

    boolean isValidCastlingFlag(String flag) {
        return flag.matches("[qkQK]{1,4}") || flag.equals("-");
    }


    boolean isValidEnPassantFlag(String flag) {
        return flag.matches("[a-h][36]") || flag.equals("-");
    }

    boolean isValidCounter(String s) {
        try {
            int n = Integer.parseInt(s);
            return n > -1 && n < 9999;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
