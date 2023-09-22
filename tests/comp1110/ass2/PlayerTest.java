package comp1110.ass2;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {


    @Test
    public void testConstructor() {
        for (int i = 0; i < 99; i++) {
            var r = new Random();
            var coins = r.nextInt(1000);
            var remainRugs = r.nextInt(15);
            var color = new char[]{'r', 'p', 'y', 'c'};
            var realColor = color[r.nextInt(4)];
            var isOut = r.nextBoolean();
            var coinsString = String.format("%03d", coins);
            var remainRugsString = String.format("%02d", remainRugs);
            //initialized
            var s = new StringBuilder();
            s.append('P');
            s.append(realColor);
            s.append(coinsString);
            s.append(remainRugsString);
            if (isOut) s.append('o');
            else s.append('i');

            //lalalalala

            Player player1 = new Player(s.toString());//initiate player

            try {
                assertEquals(realColor, player1.getColor(), "Color does not match");
                assertEquals(coins, player1.getCoins(), "Coins do not match");
                assertEquals(remainRugs, player1.getRemainingRugNumber(), "Remaining rugs do not match");

                assertEquals(player1.out, isOut, "Expected player to not be out");
            } catch (AssertionError e) {
                throw new AssertionError("Test failed: \n" +
                        "Expected: " + e.getMessage() + "\n" +
                        "Actual: " + e.getMessage());
            }

        }

    }
}