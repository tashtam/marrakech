package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AssamTest {
    @Test
    void testAssam() {
        for (int d = 0; d < 100; d++) {
            var rand = new Random();

            var directions = "NESW";
            var i = rand.nextInt(7);
            var j = rand.nextInt(7);
            var k = rand.nextInt(4);
            var degree = k * 90;
            var direction = directions.charAt(k);

            var s = "A" + i + j + direction;
            System.out.println(s);

            var assam = new Assam(s);
            Assertions.assertEquals(assam.degree, degree);
            Assertions.assertEquals(assam.position.x, i);
            Assertions.assertEquals(assam.position.y, j);
        }
    }
}