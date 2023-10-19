package comp1110.ass2.gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class GMain extends Group {
    Scene scene;
    Group root;
    GGame gGame;
    RadioButton[] playerRbs = new RadioButton[5];
    RadioButton[] aiPlayerRbs = new RadioButton[5];
    RadioButton[] hardAIPlayerRbs = new RadioButton[5];
    ToggleGroup playerTG = new ToggleGroup();
    ToggleGroup aiPlayerTG = new ToggleGroup();
    ToggleGroup hardAIPlayerTG = new ToggleGroup();
    Button beginBtn = new Button("begin game");

    GMain(Scene scene, Group root) {
        this.scene = scene;
        this.root = root;

        var texts = new String[]{"zero", "one", "two", "three", "four"};
        for (int i = 0; i < playerRbs.length; i++) {
            var playerRb = new RadioButton(texts[i] + " players");
            playerRb.setLayoutY(50 + 50 * i);
            playerRb.setToggleGroup(playerTG);
            playerRbs[i] = playerRb;
        }
        this.getChildren().addAll(playerRbs);

        for (int i = 0; i < aiPlayerRbs.length; i++) {
            var playerRb = new RadioButton(texts[i] + " ai players");
            playerRb.setLayoutX(100);
            playerRb.setLayoutY(50 + 50 * i);
            playerRb.setToggleGroup(aiPlayerTG);
            aiPlayerRbs[i] = playerRb;
        }
        this.getChildren().addAll(aiPlayerRbs);

        for (int i = 0; i < hardAIPlayerRbs.length; i++) {
            var playerRb = new RadioButton(texts[i] + " hard ai players");
            playerRb.setLayoutX(230);
            playerRb.setLayoutY(50 + 50 * i);
            playerRb.setToggleGroup(hardAIPlayerTG);
            hardAIPlayerRbs[i] = playerRb;
        }
        this.getChildren().addAll(hardAIPlayerRbs);

        beginBtn.setOnMouseClicked(event -> {
            int i, j, k;

            var playerRb = (RadioButton) playerTG.getSelectedToggle();
            for (i = 0; i < playerRbs.length; i++) {
                if (playerRbs[i] == playerRb) break;
            }

            playerRb = (RadioButton) aiPlayerTG.getSelectedToggle();
            for (j = 0; j < aiPlayerRbs.length; j++) {
                if (aiPlayerRbs[j] == playerRb) break;
            }

            playerRb = (RadioButton) hardAIPlayerTG.getSelectedToggle();
            for (k = 0; k < hardAIPlayerRbs.length; k++) {
                if (hardAIPlayerRbs[k] == playerRb) break;
            }

            var d = i + j + k;
            System.out.println(""+i+" "+j+" "+k+" = "+d);
            if (d >= 2 && d < 5) {
                this.createNewGame(i, j, k);
            }
        });
        this.getChildren().add(beginBtn);

        this.setLayoutX(100);
        this.setLayoutY(100);
    }

    void createNewGame(int playerAmount, int aiPlayerAmount, int hardAIPlayerAmount) {
        gGame = new GGame(playerAmount, aiPlayerAmount, hardAIPlayerAmount);
        gGame.setGMain(this);
        gGame.setCallback(scene);
        root.getChildren().add(gGame);
        root.getChildren().remove(this);
    }

    void backToTitle() {
        gGame.clearCallback(scene);
        root.getChildren().remove(gGame);
        root.getChildren().add(this);
    }
}
