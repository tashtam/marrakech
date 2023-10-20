package comp1110.ass2.gui;

import comp1110.ass2.Game;
import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
/**
 * GUI part for assam
 * @author Xin Yang Li (u7760022)
 */
public class GAssam extends Group {
    Game game;
    Rectangle rect = new Rectangle(60, 60);
    Circle circle = new Circle();
    static ImagePattern assamN = new ImagePattern(new Image("file:assets/game/assamN.png"));
    static ImagePattern assamE = new ImagePattern(new Image("file:assets/game/assamE.png"));
    static ImagePattern assamS = new ImagePattern(new Image("file:assets/game/assamS.png"));
    static ImagePattern assamW = new ImagePattern(new Image("file:assets/game/assamW.png"));

    /**
     * @param game the game instance
     *             this will create Assam GUI
     */
    GAssam(Game game) {
        this.game = game;

        rect.setFill(assamN);
        rect.setArcWidth(100);
        rect.setArcHeight(100);
        rect.setLayoutX(5);
        rect.setLayoutY(5);
        this.getChildren().add(rect);

        circle.setCenterX(58);
        circle.setCenterY(12);
        circle.setRadius(5);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        this.getChildren().add(circle);
    }

    /**
     * this will update the Assam GUI
     */
    void update() {
        if (game.assam.degree == 0) rect.setFill(assamN);
        else if (game.assam.degree == 90) rect.setFill(assamE);
        else if (game.assam.degree == 180) rect.setFill(assamS);
        else rect.setFill(assamW);
        var player = game.getCurrentPlayer();
        var javaFxColor = Utils.getJavaFxColor(player.color);
        circle.setFill(javaFxColor);

        this.setLayoutX(game.assam.position.x * (Utils.UNIT_SIZE));
        this.setLayoutY(game.assam.position.y * (Utils.UNIT_SIZE));
    }
}
