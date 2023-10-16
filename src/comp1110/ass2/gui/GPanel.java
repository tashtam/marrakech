package comp1110.ass2.gui;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GPanel extends Group {
    GGame gGame;
    GConsole gConsole = new GConsole();
    GPlayer[] gPlayers = new GPlayer[4];
    GDie gDie = new GDie();

    GPanel(GGame gGame) {
        this.gGame = gGame;

        gDie.setLayoutY(450);
        this.getChildren().add(gDie);

        gConsole.setLayoutY(250);
        this.getChildren().add(gConsole);

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
