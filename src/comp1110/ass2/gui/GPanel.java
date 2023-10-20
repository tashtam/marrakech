package comp1110.ass2.gui;

import comp1110.ass2.Player;
import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * GUI part for panel
 * @author Xin Yang Li (u7760022)
 */
public class GPanel extends Group {
    GPlayer[] gPlayers;
    GConsole gConsole;
    GDie gDie;

    GPanel() {
        // player
        gPlayers = new GPlayer[4];
        for (int i = 0; i < 4; i++) {
            var gPlayer = new GPlayer();
            gPlayer.setLayoutX((i % 2) * 200);
            gPlayer.setLayoutY((i / 2) * 110);
            gPlayers[i] = gPlayer;
        }
        this.getChildren().addAll(gPlayers);

        gConsole = new GConsole();
        gConsole.setLayoutY(230);
        this.getChildren().add(gConsole);

        gDie = new GDie();
        gDie.setLayoutY(450);
        this.getChildren().add(gDie);

        this.setLayoutX(700);
        this.setLayoutY(50);
    }

    void setCurrentPlayer(Player currentPlayer, int currentPlayerIndex) {
        gPlayers[currentPlayerIndex].update(currentPlayer);
        for (int i = 0; i < gPlayers.length; i++) {
            gPlayers[i].setCurrent(i == currentPlayerIndex);
        }
    }

    void updatePlayers(Player[] players) {
        int n = players.length;
        for (int i = 0; i < 4; i++) {
            if (i < n) gPlayers[i].setPlayer(players[i]);
            else gPlayers[i].clearPlayer();
        }
    }
}

/**
 * GUI part for die
 * @author Xin Yang Li (u7760022)
 */
class GDie extends Group {
    Rectangle rect = new Rectangle(60, 60);
    Text text = new Text();

    GDie() {
        rect.setFill(Color.LIGHTGRAY);
        this.getChildren().add(rect);

        text.setLayoutX(20);
        text.setLayoutY(50);
        text.setFont(Utils.bigFont);
        this.getChildren().add(text);
    }

    void displayDie(int value) {
        if (value == 0) text.setText("");
        else text.setText("" + value);
    }

}

/**
 * GUI part for console
 * @author Xin Yang Li (u7760022)
 */
class GConsole extends Group {
    TextArea area = new TextArea();

    GConsole() {
        area.setMaxWidth(380);
        area.setDisable(true);
        this.getChildren().add(area);
    }

    void print(String content) {
        area.appendText("\n" + content);
        area.setScrollTop(500);
    }
}

/**
 * GUI part for player information
 * @author Xin Yang Li (u7760022)
 */
class GPlayer extends Group {
    Label coinText;
    Label remainRugNumText;
    Label scoreText;
    Label aiText;
    Rectangle mainRect;
    Rectangle faceRect;
    Circle circle;

    void clearPlayer() {
        mainRect.setStroke(Color.GRAY);
        faceRect.setFill(Color.GRAY);
        aiText.setText("");
        coinText.setText("coin: ");
        remainRugNumText.setText("rug: ");
        scoreText.setText("score: ");
    }

    void setPlayer(Player player) {
        mainRect.setStroke(Utils.getJavaFxColor(player.color));
        faceRect.setFill(Utils.getJavaFxColor(player.color));

        if (player.ai) {
            if (player.hardAI) aiText.setText("hard");
            else aiText.setText("easy");
        } else {
            aiText.setText("");
        }

        this.update(player);
    }

    void setCurrent(boolean isCurrent) {
        circle.setVisible(isCurrent);
    }

    void update(Player player) {
        coinText.setText("coin: " + player.coins);
        remainRugNumText.setText("rug: " + player.remainingRugNumber);
        scoreText.setText("score: " + player.calculateScore());
        if (player.out) mainRect.setStroke(Color.GRAY);
    }

    GPlayer() {
        mainRect = new Rectangle(180, 100);
        mainRect.setArcHeight(10);
        mainRect.setArcWidth(10);
        mainRect.setStrokeWidth(5);
        mainRect.setFill(Color.WHITE);
        this.getChildren().add(mainRect);

        faceRect = new Rectangle(50, 100);
        faceRect.setArcHeight(10);
        faceRect.setArcWidth(10);
        this.getChildren().add(faceRect);

        circle = new Circle(10, 10, 4);
        circle.setFill(Color.BLACK);
        circle.setVisible(false);
        this.getChildren().add(circle);

        coinText = new Label("coin: ");
        coinText.setLayoutX(60);
        coinText.setLayoutY(10);
        coinText.setFont(Utils.font);
        this.getChildren().add(coinText);

        remainRugNumText = new Label("rug: ");
        remainRugNumText.setLayoutX(60);
        remainRugNumText.setLayoutY(40);
        remainRugNumText.setFont(Utils.font);
        this.getChildren().add(remainRugNumText);

        scoreText = new Label("score: ");
        scoreText.setLayoutX(60);
        scoreText.setLayoutY(70);
        scoreText.setFont(Utils.font);
        this.getChildren().add(scoreText);

        aiText = new Label("");
        aiText.setLayoutX(5);
        aiText.setLayoutY(40);
        aiText.setFont(Utils.font);
        this.getChildren().add(aiText);
    }
}
