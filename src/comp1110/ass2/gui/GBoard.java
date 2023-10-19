package comp1110.ass2.gui;

import comp1110.ass2.Board;
import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
/**
 * @author Xin Yang Li (u7760022)
 */
public class GBoard extends Group {
    GTile[] gTiles;

    GBoard(Board board) {
        var d = Utils.GRID_SIZE * 0.7;
        for (int i = 0; i < 4; i++) {
            var c = new Circle(Utils.UNIT_SIZE * (i * 2 + 1), 0, d);
            c.setFill(Color.ORANGE);
            this.getChildren().add(c);
        }
        for (int i = 0; i < 4; i++) {
            var c = new Circle(i * Utils.UNIT_SIZE * 2, Utils.UNIT_SIZE * Utils.MAP_SIZE, d);
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

        d = Utils.UNIT_SIZE * Utils.MAP_SIZE + Utils.GRID_GAP;
        var rect = new Rectangle(d, d, Color.WHITE);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setLayoutX(-Utils.GRID_GAP);
        rect.setLayoutY(-Utils.GRID_GAP);
        this.getChildren().add(rect);

        gTiles = new GTile[board.tiles.length];
        for (int i = 0; i < gTiles.length; i++) {
            var gTile = new GTile(board.tiles[i]);
            int x = i / Utils.MAP_SIZE;
            int y = i % Utils.MAP_SIZE;
            gTile.setLayoutX(x * (Utils.UNIT_SIZE));
            gTile.setLayoutY(y * (Utils.UNIT_SIZE));
            gTiles[i] = gTile;
            this.getChildren().add(gTile);
        }
    }

    void update() {
        for (GTile gTile : gTiles) gTile.update();
    }
}
