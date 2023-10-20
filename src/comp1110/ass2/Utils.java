package comp1110.ass2;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Random;

/**
 * @author Xin Yang Li (u7760022)
 * this class defines initializations of parameters: MAP_SIZE,GRID_SIZE,GRID_GAP,UNIT_SIZE,font,bigFont
 * method 'createRugPositions' to list all possible positions for assam
 * method 'getJavaFxColor' to transform char into javaFX color
 */
public class Utils {
    public static final int MAP_SIZE = 7;
    public static final int GRID_SIZE = 70;
    public static final int GRID_GAP = 10;
    public static final int UNIT_SIZE = GRID_SIZE + GRID_GAP;
    public static final Font font = Font.font("Consolas", FontWeight.EXTRA_BOLD, 16);
    public static final Font bigFont = Font.font("Consolas", FontWeight.EXTRA_BOLD, 32);
    public static final Random ranDie = new Random();

    /**
     * @param color char color stands for player color
     * @return JavaFX color that can be used in JavaFX
     */
    public static Color getJavaFxColor(char color) {
        if (color == 'y') return Color.web("#ffd700");
        if (color == 'c') return Color.web("#00cccc");
        if (color == 'r') return Color.RED;
        if (color == 'p') return Color.MEDIUMPURPLE;
        return Color.LIGHTGRAY;
    }

    /**
     * @param array a list of object needed to be shuffled
     * @param <T> the type of the object
     */
    public static <T> void shuffle(T[] array) {
        var n = array.length;
        for (int i = 0; i < n; i++) {
            var j = Utils.randInt(n - i) + i;
            if (i == j) continue;
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    /**
     * get a random int from 0 to bound
     * @param bound
     * @return
     */
    public static int randInt(int bound) {
        return ranDie.nextInt(bound);
    }

    /**
     * @param mode all possible combinations of place rug with two pieces
     * @param center present position of Assam
     * @return all Intpair position stored in a list that could be used to place rugs
     */
    public static IntPair[] createRugPositions(int mode, IntPair center) {
        // mode = 11,0,1,  2,3,4,  5,6,7,   8,9,10
        // x1 = 0,0,0,     1,1,1,  0,0,0,  -1,-1,-1
        // y1 = -1,-1,-1,  0,0,0,  1,1,1,   0,0,0
        // x2 = -1,0,1,    1,2,1,  1,0,-1, -1,-2,-1
        // y2 = -1,-2,-1, -1,0,1,  1,2,1,   1,0,-1

        var vs1 = new int[]{0, 0, 0, 1, 1, 1, 0, 0, 0, -1, -1, -1};
        var vs2 = new int[]{0, 1, 1, 2, 1, 1, 0, -1, -1, -2, -1, -1};
        var x1 = vs1[(mode + 1) % 12];
        var y1 = vs1[(mode + 10) % 12];
        var x2 = vs2[mode];
        var y2 = vs2[(mode + 9) % 12];

        x1 += center.x;
        x2 += center.x;
        y1 += center.y;
        y2 += center.y;

        var positions = new IntPair[2];
        positions[0] = new IntPair(x1, y1);
        positions[1] = new IntPair(x2, y2);
        return positions;
    }
}
