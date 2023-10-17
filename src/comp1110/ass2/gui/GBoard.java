package comp1110.ass2.gui;

import comp1110.ass2.Board;
import comp1110.ass2.Utils;
import javafx.scene.Group;

public class GBoard extends Group {
    GTile[] gTiles;

    GBoard(Board board) {
        gTiles = new GTile[board.tiles.length];
        for (int i = 0; i < gTiles.length; i++) {
            var gTile = new GTile(board.tiles[i]);
            int x = i / Utils.RowMax;
            int y = i % Utils.RowMax;
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
