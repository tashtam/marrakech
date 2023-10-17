package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.IntPair;
import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GMark extends Group {
    IntPair[] positions = new IntPair[2];
    Rectangle[] rects = new Rectangle[2];
    int mode = 0;
    Assam assam;

    GMark(Assam assam) {
        this.assam = assam;
        for (int i = 0; i < rects.length; i++) {
            var rect = new Rectangle(Utils.GRID_SIZE, Utils.GRID_SIZE);
            rect.setFill(Color.web("#00000000"));
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(2);
            rect.setArcHeight(10);
            rect.setArcWidth(10);
            rects[i] = rect;
            positions[i] = new IntPair(0, 0);
        }
        this.getChildren().addAll(rects);
    }

    void reset() {
        mode = 0;
        this.update();
    }

    void hide() {
        for (Rectangle rect : rects) {
            rect.setVisible(false);
        }
    }

    boolean checkValid() {
        return true;
    }

    void quickChangeMode(int degree) {
        switch (degree) {
            case 0 -> mode = 0;
            case 90 -> mode = 3;
            case 180 -> mode = 6;
            case 270 -> mode = 9;
        }
        this.update();
    }

    void changeMode(int degree) {
        if (degree == 0) {
            if (mode == 0) return;
            if (mode < 6) mode -= 1;
            else mode += 1;
        } else if (degree == 90) {
            if (mode == 3) return;
            if (mode > 3 && mode < 9) mode -= 1;
            else mode += 1;
        } else if (degree == 180) {
            if (mode == 6) return;
            if (mode > 6) mode -= 1;
            else mode += 1;
        } else if (degree == 270) {
            if (mode == 9) return;
            if (mode < 3 || mode > 9) mode -= 1;
            else mode += 1;
        }
        mode = (mode + 12) % 12;
        this.update();
    }

    void calculatePositions() {
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

        var assamPos = assam.position;

        x1 += assamPos.x;
        x2 += assamPos.x;
        y1 += assamPos.y;
        y2 += assamPos.y;

        positions[0].x = x1;
        positions[0].y = y1;
        positions[1].x = x2;
        positions[1].y = y2;
    }

    void update() {
        this.calculatePositions();
        for (int i = 0; i < positions.length; i++) {
            var x = positions[i].x;
            var y = positions[i].y;
            rects[i].setVisible(x >= 0 && x < 7 && y >= 0 && y < 7);
            rects[i].setLayoutX(x * Utils.UNIT_SIZE);
            rects[i].setLayoutY(y * Utils.UNIT_SIZE);
        }
    }
}
