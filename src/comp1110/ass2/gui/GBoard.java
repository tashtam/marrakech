package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.scene.Group;

public class GBoard extends Group {
    final int TILE_SIZE = 70;
    final int TILE_GAP = 6;

    Marrakech game;
    GTile[] gTiles;
    GAssam gAssam;

    GBoard(Marrakech game) {
        this.game = game;

        int unit = TILE_SIZE + TILE_GAP;

        gTiles = new GTile[game.board.tiles.length];
        for (int i = 0; i < gTiles.length; i++) {
            var gTile = new GTile(game.board.tiles[i], TILE_SIZE);
            int x = i / game.board.HEIGHT;
            int y = i % game.board.HEIGHT;
            gTile.setLayoutX(x * unit);
            gTile.setLayoutY(y * unit);
            gTiles[i] = gTile;
            this.getChildren().add(gTile);
        }

        gAssam = new GAssam(game.assam, TILE_SIZE);
        gAssam.setLayoutX(game.assam.position.x * unit);
        gAssam.setLayoutY(game.assam.position.y * unit);
        this.getChildren().add(gAssam);
    }

    void update() {
        for (GTile gTile : gTiles) gTile.update();
        gAssam.update(game.players[game.currentPlayerIndex]);
    }
}
