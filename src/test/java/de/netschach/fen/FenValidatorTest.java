package de.netschach.fen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FenValidatorTest {

    @Test
    public void test1() {
        assertTrue(new FenValidator().isValid("b1rb4/3pp3/3rk3/p1pp4/3p3N/1B1N4/4KR1B/8 w - -", null));
    }

    @Test
    public void test2() {
        assertTrue(new FenValidator().isValid("rnbnkqrb/pppppppp/8/8/8/8/PPPPPPPP/RNBNKQRB w KQkq - 0 1", null));
    }


    @Test
    public void test3() {
        assertTrue(new FenValidator().isValid("rnbnkqrb/pppppppp/8/8/8/8/PPPPPPPP/RNBNKQRB w KQkq e3 0 1", null));
    }


    @Test
    public void test4() {
        assertFalse(new FenValidator().isValid("b1rb4/3pp3/3rk3/p1pp4/3p3N/1B1N4/4KR1Z/8 w - -", null));
    }

    @Test
    public void test5() {
        assertFalse(new FenValidator().isValid("rnbnkqrb/pppppppp/8/8/8/8/PPPPPPPP/RNBNKQRB w KQkq a 0 1", null));
    }


    @Test
    public void test6() {
        assertFalse(new FenValidator().isValid("rnbnkqrb/pppppppp/8/8/8/8/PPPPPPPP/RNBNKQRB w KQkq e3 C 1", null));
    }
}
