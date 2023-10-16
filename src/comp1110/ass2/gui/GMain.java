package comp1110.ass2.gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GMain extends Group {
    Scene scene;
    GGame gGame;
    Rectangle startRect = new Rectangle(100, 100, 100, 100);

    GMain(Scene scene) {
        this.scene = scene;
        startRect.setFill(Color.GREEN);
        startRect.setOnMouseClicked(event -> {
            this.createNewGame(4);
        });
        this.getChildren().add(startRect);
    }

    void createNewGame(int playerAmount) {
        gGame = new GGame(playerAmount);
        gGame.setGMain(this);
        this.getChildren().add(gGame);
    }

    void deleteOldGame() {
        this.getChildren().remove(gGame);
    }
}
