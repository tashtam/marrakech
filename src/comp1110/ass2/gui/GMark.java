package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.Rug;
import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GMark extends Group {
    IntPair[] positions = new IntPair[2];
    Rectangle[] rects = new Rectangle[2];
    int mode = 0;
    GGame gGame;

    GMark(GGame gGame) {
        this.gGame = gGame;
        for (int i = 0; i < rects.length; i++) {
            var rect = new Rectangle(Utils.GRID_SIZE + Utils.GRID_GAP, Utils.GRID_SIZE + Utils.GRID_GAP);
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
        positions = Utils.createRugPositions(mode, gGame.game.assam.position);
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
        this.setMode(mode);
    }

    void setMode(int mode) {
        this.mode = mode;
        positions = Utils.createRugPositions(mode, gGame.game.assam.position);
        var rugs = gGame.game.getPossibleRugs();
        gGame.gAssamHint.setValue(false);
        for (Rug rug : rugs) {
            var d = 0;
            for (IntPair p1 : rug.positions) {
                for (IntPair p2 : positions) {
                    if (p1.x == p2.x && p1.y == p2.y) {
                        d += 1;
                        break;
                    }
                }
            }
            if (d == 2) {
                gGame.gAssamHint.setValue(true);
                break;
            }
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
        this.setMode(mode);
    }

    void update() {
        for (int i = 0; i < positions.length; i++) {
            var x = positions[i].x;
            var y = positions[i].y;
            rects[i].setVisible(x >= 0 && x < 7 && y >= 0 && y < 7);
            rects[i].setLayoutX(x * Utils.UNIT_SIZE - Utils.GRID_GAP / 2);
            rects[i].setLayoutY(y * Utils.UNIT_SIZE - Utils.GRID_GAP / 2);
        }
    }
}
