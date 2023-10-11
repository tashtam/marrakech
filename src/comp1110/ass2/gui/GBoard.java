package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.Marrakech;
import javafx.scene.Group;

public class GBoard extends Group {
    final int TILE_SIZE = 70;
    final int TILE_GAP = 6;

    IntPair highlightPosition;
    int highlightDegree = 0;

    Marrakech game;
    GTile[] gTiles;
    GAssam gAssam;

    GBoard(Marrakech game) {
        this.game = game;

        gTiles = new GTile[game.board.tiles.length];
        for (int i = 0; i < gTiles.length; i++) {
            var gTile = new GTile(game, game.board.tiles[i], TILE_SIZE, this);
            int x = i / game.board.HEIGHT;
            int y = i % game.board.HEIGHT;
            gTile.setLayoutX(x * (TILE_SIZE + TILE_GAP));
            gTile.setLayoutY(y * (TILE_SIZE + TILE_GAP));
            gTiles[i] = gTile;
            this.getChildren().add(gTile);
        }

        gAssam = new GAssam(game, game.assam, TILE_SIZE);
        this.getChildren().add(gAssam);
    }

    void update() {
        for (GTile gTile : gTiles) gTile.update();
        gAssam.setLayoutX(game.assam.position.x * (TILE_SIZE + TILE_GAP));
        gAssam.setLayoutY(game.assam.position.y * (TILE_SIZE + TILE_GAP));
        gAssam.update();
    }

    GTile getGTile(IntPair pos) {
        if (pos == null || pos.x < 0 || pos.x >= game.board.WIDTH || pos.y < 0 || pos.y >= game.board.HEIGHT)
            return null;
        return gTiles[pos.x * game.board.HEIGHT + pos.y];
    }

    GTile[] getHighlightGTiles() {
        if (highlightPosition == null) return new GTile[0];

        int dx, dy;
        if (highlightDegree == 0) {
            dx = 0;
            dy = -1;
        } else if (highlightDegree == 90) {
            dx = 1;
            dy = 0;
        } else if (highlightDegree == 180) {
            dx = 0;
            dy = 1;
        } else {
            dx = -1;
            dy = 0;
        }

        var pos1 = highlightPosition;
        var pos2 = new IntPair(highlightPosition.x + dx, highlightPosition.y + dy);

        var gTile1 = this.getGTile(pos1);
        var gTile2 = this.getGTile(pos2);

        if (gTile2 == null) return new GTile[]{gTile1};
        return new GTile[]{gTile1, gTile2};
    }

    void setHighlightPosition(IntPair pos) {
        for (GTile gTile : this.getHighlightGTiles()) {
            gTile.setHighlight(false);
        }
        highlightPosition = pos;
        for (GTile gTile : this.getHighlightGTiles()) {
            gTile.setHighlight(true);
        }
    }

    void rotateHighlightDegree(int degree) {
        degree = (highlightDegree + degree) % 360;
        this.setHighlightDegree(degree);
    }

    void setHighlightDegree(int degree) {
        for (GTile gTile : this.getHighlightGTiles()) {
            gTile.setHighlight(false);
        }
        highlightDegree = degree;
        for (GTile gTile : this.getHighlightGTiles()) {
            gTile.setHighlight(true);
        }
    }
}
