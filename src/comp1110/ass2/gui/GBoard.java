package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.Marrakech;
import javafx.scene.Group;

public class GBoard extends Group {
    final int TILE_SIZE = 70;
    final int TILE_GAP = 6;

    int rugPlaceDegree = 0;
    IntPair highlightPos;

    Marrakech game;
    GTile[] gTiles;
    GAssam gAssam;

    GBoard(Marrakech game) {
        this.game = game;

        int unit = TILE_SIZE + TILE_GAP;

        gTiles = new GTile[game.board.tiles.length];
        for (int i = 0; i < gTiles.length; i++) {
            var gTile = new GTile(game.board.tiles[i], TILE_SIZE, this);
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

    GTile getGTile(IntPair pos) {
        if (pos == null || pos.x < 0 || pos.x >= game.board.WIDTH || pos.y < 0 || pos.y >= game.board.HEIGHT)
            return null;
        return gTiles[pos.x * game.board.HEIGHT + pos.y];
    }

    IntPair[] getHighlightPoses(IntPair pos) {
        var poses = new IntPair[2];
        poses[0] = pos;
        if (rugPlaceDegree == 0) {
            poses[1] = new IntPair(pos.x, pos.y - 1);
        } else if (rugPlaceDegree == 90) {
            poses[1] = new IntPair(pos.x + 1, pos.y);
        } else if (rugPlaceDegree == 180) {
            poses[1] = new IntPair(pos.x, pos.y + 1);
        } else {
            poses[1] = new IntPair(pos.x - 1, pos.y);
        }
        return poses;
    }

    void _setHighlight(IntPair pos, boolean value) {
        if (pos == null) return;
        var hPoses = this.getHighlightPoses(pos);
        for (IntPair hPos : hPoses) {
            var gTile = this.getGTile(hPos);
            if (gTile != null) {
                gTile.highlight = value;
            }
        }
    }

    void setHighlight(IntPair pos) {
        this._setHighlight(highlightPos, false);
        highlightPos = pos;
        this._setHighlight(highlightPos, true);
    }

    void rotateRugPlaceDegree(int degree) {
        this._setHighlight(highlightPos, false);
        rugPlaceDegree = (rugPlaceDegree + degree) % 360;
        this._setHighlight(highlightPos, true);
    }
}
