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
        positions = Utils.createRugPositions(mode, assam.position);
        this.update();
    }

    void hide() {
        for (Rectangle rect : rects) {
            rect.setVisible(false);
        }
    }

    void quickChangeMode(int degree) {
        switch (degree) {
            case 0 -> mode = 0;
            case 90 -> mode = 3;
            case 180 -> mode = 6;
            case 270 -> mode = 9;
        }
        positions = Utils.createRugPositions(mode, assam.position);
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
        positions = Utils.createRugPositions(mode, assam.position);
        this.update();
    }

    void update() {
        for (int i = 0; i < positions.length; i++) {
            var x = positions[i].x;
            var y = positions[i].y;
            rects[i].setVisible(x >= 0 && x < 7 && y >= 0 && y < 7);
            rects[i].setLayoutX(x * Utils.UNIT_SIZE);
            rects[i].setLayoutY(y * Utils.UNIT_SIZE);
        }
    }
}
