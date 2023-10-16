package comp1110.ass2.gui;

import comp1110.ass2.Game;
import javafx.scene.Group;

public class GGame extends Group {
    Game game;
    GBoard gBoard;
    GPanel gPanel;

    GGame(Game game) {
        this.game = game;

        gBoard = new GBoard(this);
        gBoard.setLayoutX(20);
        gBoard.setLayoutY(100);
        this.getChildren().add(gBoard);

        gPanel = new GPanel(this);
        gPanel.setPlayers();
        gPanel.setLayoutX(600);
        gPanel.setLayoutY(100);
        this.getChildren().add(gPanel);
    }

    void update() {
        gBoard.update();
        gPanel.update();
    }
}
