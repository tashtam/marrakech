package comp1110.ass2.gui;

import comp1110.ass2.Assam;
import comp1110.ass2.Player;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GAssam extends Group {
    Assam assam;
    ImageView view;
    Circle circle;

    GAssam(Assam assam, int size) {
        this.assam = assam;

        var image = new Image("file:assets/game/assam.png");
        view = new ImageView();
        view.setImage(image);
        view.setFitWidth(size - 10);
        view.setFitHeight(size - 10);
        view.setLayoutX(5);
        view.setLayoutY(5);
        this.getChildren().add(view);

        circle = new Circle();
        circle.setCenterX(size - 12);
        circle.setCenterY(12);
        circle.setRadius(5);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);
        this.getChildren().add(circle);
    }

    void update(Player player) {
        view.setRotate(assam.degree + 90);
        var javaFxColor = GUtils.getJavaFxColor(player.color);
        circle.setFill(javaFxColor);
    }
}
