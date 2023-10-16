package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GAssam extends Group {
    static final int SIZE = 60;
    GGame gGame;
    Assam assam;
    Rectangle rect;
    Circle circle;
    static ImagePattern assamN = new ImagePattern(new Image("file:assets/game/assamN.png"));
    static ImagePattern assamE = new ImagePattern(new Image("file:assets/game/assamE.png"));
    static ImagePattern assamS = new ImagePattern(new Image("file:assets/game/assamS.png"));
    static ImagePattern assamW = new ImagePattern(new Image("file:assets/game/assamW.png"));

    GAssam(GGame gGame, Assam assam) {
        this.gGame = gGame;
        this.assam = assam;

        rect = new Rectangle(SIZE, SIZE);
        rect.setFill(assamN);
        rect.setArcWidth(100);
        rect.setArcHeight(100);
        rect.setLayoutX(5);
        rect.setLayoutY(5);
        this.getChildren().add(rect);

        circle = new Circle();
        circle.setCenterX(SIZE - 2);
        circle.setCenterY(12);
        circle.setRadius(5);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        this.getChildren().add(circle);
    }

    void update() {
        if (assam.degree == 0) rect.setFill(assamN);
        else if (assam.degree == 90) rect.setFill(assamE);
        else if (assam.degree == 180) rect.setFill(assamS);
        else rect.setFill(assamW);
        var player = gGame.game.getCurrentPlayer();
        var javaFxColor = GUtils.getJavaFxColor(player.color);
        circle.setFill(javaFxColor);
    }
}
