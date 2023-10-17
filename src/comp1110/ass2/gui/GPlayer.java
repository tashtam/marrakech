package comp1110.ass2.gui;

import comp1110.ass2.Game;
import comp1110.ass2.Player;
import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class GPlayer extends Group {
    Player player;
    Game game;
    Text coinText = new Text(60, 20, "");
    Text remainRugNumText = new Text(60, 50, "");
    Text scoreText = new Text(60, 80, "");
    Rectangle mainRect = new Rectangle(180, 100);
    Rectangle faceRect = new Rectangle(50, 100);
    Circle circle = new Circle(10, 10, 4);

    void setPlayer(Player player) {
        this.player = player;
        var javaFXColor = player == null ? Color.GRAY : Utils.getJavaFxColor(player.color);

        mainRect.setStroke(javaFXColor);
        faceRect.setFill(javaFXColor);

        coinText.setText("coin: ");
        remainRugNumText.setText("rug: ");
        scoreText.setText("score: ");
    }

    void update() {
        if (player != null) {
            coinText.setText("coin: " + player.coins);
            remainRugNumText.setText("rug: " + player.remainingRugNumber);
            scoreText.setText("score: " + game.getPlayerScore(player));
            if (player.out) mainRect.setStroke(Color.GRAY);
        }

        var currentPlayer = game.getCurrentPlayer();
        circle.setVisible(currentPlayer == player);
    }

    GPlayer(Game game) {
        this.game = game;

        mainRect.setArcHeight(10);
        mainRect.setArcWidth(10);
        mainRect.setStrokeWidth(5);
        mainRect.setFill(Color.WHITE);

        faceRect.setArcHeight(10);
        faceRect.setArcWidth(10);

        circle.setFill(Color.BLACK);

        coinText.setFont(Utils.font);
        remainRugNumText.setFont(Utils.font);
        scoreText.setFont(Utils.font);
        this.getChildren().addAll(mainRect, faceRect, coinText, remainRugNumText, scoreText, circle);
    }
}
