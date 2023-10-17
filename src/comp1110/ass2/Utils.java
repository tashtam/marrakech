package comp1110.ass2;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Utils {
    public static final int RowMax = 7;
    public static final int ColumnMax = 7;
    public static final int GRID_SIZE = 70;
    public static final int GRID_GAP = 6;
    public static final int UNIT_SIZE = GRID_SIZE + GRID_GAP;
    public static final Font font = Font.font("Consolas", FontWeight.EXTRA_BOLD, 16);
    public static final Font bigFont = Font.font("Consolas", FontWeight.EXTRA_BOLD, 32);

    public static Color getJavaFxColor(char color) {
        if (color == 'y') return Color.web("#ffd700");
        if (color == 'c') return Color.web("#00cccc");
        if (color == 'r') return Color.RED;
        if (color == 'p') return Color.MEDIUMPURPLE;
        return Color.LIGHTGRAY;
    }
}
