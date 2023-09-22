package comp1110.ass2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RugTest {
    @Test
    void testRug(){
        var rand = new Random();

        var colors = "cypr";
        var id1 = rand.nextInt(10); // Number of bound of random (0-9)
        var id2 = rand.nextInt(10); // Number of bound of random (0-9)
        var positions0x = rand.nextInt(7); // Number of bound of random (0-6)
        var positions0y = rand.nextInt(7);
        var positions1x = rand.nextInt(7);
        var positions1y = rand.nextInt(7);

        var color = colors.charAt( rand.nextInt(4));
        // Number of bound of random (0-3)
        // Each index number points to different letters in the String colors ("cypr")

        var rugString = "" + color + id1 + id2 + positions0x + positions0y + positions1x + positions1y;

        //char (like color) + int (id1, id2, etc.) = int rugString [Implicit typecasting]
        //However, Rug() needs String as its parameter
        //Therefore, we need to add "" before color in var rugString
        //String (like "") + char + int = String rugString (because String is the strongest)

        System.out.println(rugString);

        var rug = new Rug(rugString);
        Assertions.assertEquals(rug.color, color);
        Assertions.assertEquals(rug.id,  id1*10 + id2);
        Assertions.assertEquals(rug.positions[0].x, positions0x);
        Assertions.assertEquals(rug.positions[0].y, positions0y);
        Assertions.assertEquals(rug.positions[1].x, positions1x);
        Assertions.assertEquals(rug.positions[1].y, positions1y);
    }

}