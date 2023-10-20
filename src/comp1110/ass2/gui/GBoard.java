package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * @author Xin Yang Li (u7760022)
 * GUI part to display board
 */
public class GBoard extends Group {
    GTile[] gTiles;
    GAssam gAssam;
    GMark gMark;


    GBoard() {
        // mosaic track
        var d = Utils.GRID_SIZE * 0.7;
        for (int i = 0; i < 4; i++) {
            var c = new Circle(Utils.UNIT_SIZE * (i * 2 + 1), 0, d);
            c.setFill(Color.ORANGE);
            this.getChildren().add(c);
        }
        for (int i = 0; i < 4; i++) {
            var c = new Circle(Utils.UNIT_SIZE * (i * 2), Utils.UNIT_SIZE * Utils.MAP_SIZE, d);
            c.setFill(Color.ORANGE);
            this.getChildren().add(c);
        }
        for (int i = 0; i < 3; i++) {
            var c = new Circle(0, Utils.UNIT_SIZE * (i * 2 + 1), d);
            c.setFill(Color.ORANGE);
            this.getChildren().add(c);
        }
        for (int i = 0; i < 3; i++) {
            var c = new Circle(Utils.UNIT_SIZE * Utils.MAP_SIZE, Utils.UNIT_SIZE * (i + 1) * 2, d);
            c.setFill(Color.ORANGE);
            this.getChildren().add(c);
        }

        // background
        d = Utils.UNIT_SIZE * Utils.MAP_SIZE + Utils.GRID_GAP;
        var rect = new Rectangle(d, d, Color.WHITE);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setLayoutX(-Utils.GRID_GAP);
        rect.setLayoutY(-Utils.GRID_GAP);
        this.getChildren().add(rect);

        // tiles
        var n = Utils.MAP_SIZE * Utils.MAP_SIZE;
        gTiles = new GTile[n];
        for (int i = 0; i < n; i++) {
            var gTile = new GTile();
            int x = i / Utils.MAP_SIZE;
            int y = i % Utils.MAP_SIZE;
            gTile.setLayoutX(x * (Utils.UNIT_SIZE));
            gTile.setLayoutY(y * (Utils.UNIT_SIZE));
            gTiles[i] = gTile;
        }
        this.getChildren().addAll(gTiles);

        // assam
        gAssam = new GAssam();
        this.getChildren().add(gAssam);

        // mark
        gMark = new GMark();
        gMark.setVisible(false);
        this.getChildren().add(gMark);

        this.setLayoutX(50);
        this.setLayoutY(50);
    }

    void clearAll() {
        for (GTile gTile : gTiles) {
            gTile.clear();
        }
    }

    void updateRug(Board board, Rug rug) {
        for (IntPair pos : rug.positions) {
            var tile = board.getTile(pos);
            gTiles[tile.position.x * Utils.MAP_SIZE + tile.position.y].update(tile);
        }
    }
}

/**
 * GUI part for tile
 * @author Xin Yang Li (u7760022)
 */
class GTile extends Group {
    Rectangle rect;
    Text text;

    GTile() {
        rect = new Rectangle(Utils.GRID_SIZE, Utils.GRID_SIZE);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setFill(Color.LIGHTGRAY);

        text = new Text();
        text.setLayoutX(Utils.GRID_SIZE - 30);
        text.setLayoutY(Utils.GRID_SIZE - 10);
        text.setFont(Utils.font);

        this.getChildren().add(rect);
        this.getChildren().add(text);
    }

    void clear() {
        rect.setFill(Color.LIGHTGRAY);
        text.setText("");
    }

    void update(Tile tile) {
        if (tile.rug == null) {
            this.clear();
            return;
        }
        rect.setFill(Utils.getJavaFxColor(tile.rug.color));
        text.setText(String.format("%02d", tile.rug.id));
    }
}

/**
 * GUI part for mark
 * mark will display the rug positions
 * @author Xin Yang Li (u7760022)
 */
class GMark extends Group {
    IntPair[] positions = new IntPair[2];
    Rectangle[] rects = new Rectangle[2];
    int mode = 0;

    GMark() {
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

    //
    void quickChangeMode(int degree) {
        switch (degree) {
            case 0 -> mode = 0;
            case 90 -> mode = 3;
            case 180 -> mode = 6;
            case 270 -> mode = 9;
        }
    }

    void changeMode(int degree, boolean quick) {
        if (quick) {
            this.quickChangeMode(degree);
            return;
        }

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
    }

    void update(IntPair assamPos) {
        positions = Utils.createRugPositions(mode, assamPos);
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

