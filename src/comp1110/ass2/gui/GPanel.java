package comp1110.ass2.gui;

import javafx.scene.Group;

public class GPanel extends Group {
    GGame gGame;
    GPlayer[] gPlayers = new GPlayer[4];
    GDie gDie = new GDie();

    GPanel(GGame gGame) {
        this.gGame = gGame;

        gDie.setLayoutY(450);
        this.getChildren().add(gDie);

        for (int i = 0; i < 4; i++) {
            var gPlayer = new GPlayer(gGame);
            gPlayers[i] = gPlayer;
            gPlayer.setLayoutX((i % 2) * 200);
            gPlayer.setLayoutY((i / 2) * 110);
        }
        this.getChildren().addAll(gPlayers);
    }

    void setPlayers() {
        var len = gGame.game.players.length;
        for (int i = 0; i < 4; i++) {
            if (i < len) {
                gPlayers[i].setPlayer(gGame.game.players[i]);
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
