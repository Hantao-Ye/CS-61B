import static org.junit.Assert.*;

import org.junit.Test;

public class HorribleSteve {
    public static void main(String [] args) {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                break; // break exits the for loop!
            }
        }
        System.out.println("i is " + i);
    }

    @Test
    public void test123() {
        assertTrue(Flik.isSameNumber(127, 127));
        assertFalse(Flik.isSameNumber(129, 128));
        assertTrue(Flik.isSameNumber(130, 130));
        assertTrue(Flik.isSameNumber(256, 256));
        assertTrue(Flik.isSameNumber(128, 128));
    }
}
