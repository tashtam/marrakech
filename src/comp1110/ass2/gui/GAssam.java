package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.Player;
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
    Rectangle rect = new Rectangle(60, 60);
    Circle circle = new Circle();
    static ImagePattern assamN = new ImagePattern(new Image("file:assets/game/assamN.png"));
    static ImagePattern assamE = new ImagePattern(new Image("file:assets/game/assamE.png"));
    static ImagePattern assamS = new ImagePattern(new Image("file:assets/game/assamS.png"));
    static ImagePattern assamW = new ImagePattern(new Image("file:assets/game/assamW.png"));
    GAssamHint gAssamHint;


    GAssam() {
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

        gAssamHint = new GAssamHint();
        this.getChildren().add(gAssamHint);
    }

    void updateCurrentPlayer(Player currentPlayer) {
        circle.setFill(Utils.getJavaFxColor(currentPlayer.color));
    }

    void updateAssamDegree(int degree) {
        if (degree == 0) rect.setFill(assamN);
        else if (degree == 90) rect.setFill(assamE);
        else if (degree == 180) rect.setFill(assamS);
        else rect.setFill(assamW);
    }

    void updateAssamPos(IntPair position) {
        this.setLayoutX(position.x * (Utils.UNIT_SIZE));
        this.setLayoutY(position.y * (Utils.UNIT_SIZE));
    }
}

/**
 * GUI part for assam hint
 * for more hint for user
 * @author Xin Yang Li (u7760022)
 */
class GAssamHint extends Group {
    Rectangle rect = new Rectangle(Utils.GRID_SIZE + Utils.GRID_GAP, Utils.GRID_SIZE + Utils.GRID_GAP);

    GAssamHint() {
        rect.setFill(Color.web("#00000000"));
        rect.setStrokeWidth(2);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setStroke(Color.GREEN);
        this.getChildren().add(rect);

        this.setLayoutX(-Utils.GRID_GAP / 2);
        this.setLayoutY(-Utils.GRID_GAP / 2);
    }

    void update(boolean value) {
        if (value) rect.setStroke(Color.GREEN);
        else rect.setStroke(Color.RED);
    }
}
