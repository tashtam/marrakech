package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import javafx.scene.Group;

public class GBoard extends Group {
    final int TILE_GAP = 6;

    IntPair markPosition;
    int markDegree = 0;

    GGame gGame;
    GTile[] gTiles;
    GAssam gAssam;

    GBoard(GGame gGame) {
        this.gGame = gGame;

        gTiles = new GTile[gGame.game.board.tiles.length];
        for (int i = 0; i < gTiles.length; i++) {
            var gTile = new GTile(gGame, gGame.game.board.tiles[i]);
            int x = i / gGame.game.board.RowMax;
            int y = i % gGame.game.board.RowMax;
            gTile.setLayoutX(x * (GTile.SIZE + TILE_GAP));
            gTile.setLayoutY(y * (GTile.SIZE + TILE_GAP));
            gTiles[i] = gTile;
            this.getChildren().add(gTile);
        }

        gAssam = new GAssam(gGame, gGame.game.assam);
        this.getChildren().add(gAssam);
    }

    void update() {
        for (GTile gTile : gTiles) gTile.update();
        gAssam.setLayoutX(gGame.game.assam.position.x * (GTile.SIZE + TILE_GAP));
        gAssam.setLayoutY(gGame.game.assam.position.y * (GTile.SIZE + TILE_GAP));
        gAssam.update();
    }

    GTile getGTile(IntPair pos) {
        if (pos == null || pos.x < 0 || pos.x >= gGame.game.board.ColumnMax || pos.y < 0 || pos.y >= gGame.game.board.RowMax)
            return null;
        return gTiles[pos.x * gGame.game.board.RowMax + pos.y];
    }

    GTile[] getMarkGTiles() {
        if (markPosition == null) return new GTile[0];

        int dx, dy;
        if (markDegree == 0) {
            dx = 0;
            dy = -1;
        } else {
            dx = 1;
            dy = 0;
        }

        var pos1 = markPosition;
        var pos2 = new IntPair(markPosition.x + dx, markPosition.y + dy);

        var gTile1 = this.getGTile(pos1);
        var gTile2 = this.getGTile(pos2);

        if (gTile2 == null) return new GTile[]{gTile1};
        return new GTile[]{gTile1, gTile2};
    }

    void setMarkPosition(IntPair pos) {
        for (GTile gTile : this.getMarkGTiles()) {
            gTile.setMark(false);
        }
        markPosition = pos;
        for (GTile gTile : this.getMarkGTiles()) {
            gTile.setMark(true);
        }
    }

    void rotateMarkDegree(int degree) {
        degree = (markDegree + degree) % 180;
        this.setMarkDegree(degree);
    }

    void setMarkDegree(int degree) {
        for (GTile gTile : this.getMarkGTiles()) {
            gTile.setMark(false);
        }
        markDegree = degree;
        for (GTile gTile : this.getMarkGTiles()) {
            gTile.setMark(true);
        }
    }
}
