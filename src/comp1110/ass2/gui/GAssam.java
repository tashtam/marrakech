package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Marrakech;
import comp1110.ass2.Player;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GAssam extends Group {
    Marrakech game;
    Assam assam;
    Rectangle rect;
    Circle circle;

    GAssam(Marrakech game, Assam assam, int size) {
        this.game = game;
        this.assam = assam;

        var imgPat = new ImagePattern(new Image("file:assets/game/assam.png"));
        rect = new Rectangle(size - 10, size - 10);
        rect.setFill(imgPat);
        rect.setArcWidth(100);
        rect.setArcHeight(100);
        rect.setLayoutX(5);
        rect.setLayoutY(5);
        this.getChildren().add(rect);

        circle = new Circle();
        circle.setCenterX(size - 12);
        circle.setCenterY(12);
        circle.setRadius(5);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        this.getChildren().add(circle);
    }

    void update() {
        rect.setRotate(assam.degree + 90);
        var player = game.players[game.currentPlayerIndex];
        var javaFxColor = GUtils.getJavaFxColor(player.color);
        circle.setFill(javaFxColor);
    }
}
