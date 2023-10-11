package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.scene.Group;

public class GPanel extends Group {
    Marrakech game;
    GPlayer[] gPlayers = new GPlayer[4];

    GPanel(Marrakech game) {
        this.game = game;
        for (int i = 0; i < 4; i++) {
            var gPlayer = new GPlayer(game);
            gPlayers[i] = gPlayer;
            gPlayer.setLayoutX((i % 2) * 160);
            gPlayer.setLayoutY((i / 2) * 110);
        }
        this.getChildren().addAll(gPlayers);
    }

    void setPlayers() {
        var len = game.players.length;
        for (int i = 0; i < 4; i++) {
            if (i < len) {
                gPlayers[i].setPlayer(game.players[i]);
            } else {
                gPlayers[i].setPlayer(null);
            }
        }
    }

    void update() {
        for (GPlayer gPlayer : gPlayers) {
            gPlayer.update();
        }
    }
}
