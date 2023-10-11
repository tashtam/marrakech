package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.scene.Group;

public class GMarrakech extends Group {
    Marrakech game;
    GBoard gBoard;
    GPanel gPanel;

    GMarrakech(Marrakech game) {
        this.game = game;

        gBoard = new GBoard(game);
        gBoard.setLayoutX(20);
        gBoard.setLayoutY(20);
        this.getChildren().add(gBoard);

        gPanel = new GPanel(game);
        gPanel.setPlayers();
        gPanel.setLayoutX(600);
        gPanel.setLayoutY(20);
        this.getChildren().add(gPanel);
    }

    void update() {
        gBoard.update();
        gPanel.update();
    }
}
