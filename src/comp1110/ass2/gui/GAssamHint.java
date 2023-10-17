package comp1110.ass2.gui;

import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GAssamHint extends Group {
    Rectangle rect = new Rectangle(Utils.GRID_SIZE + Utils.GRID_GAP, Utils.GRID_SIZE + Utils.GRID_GAP);
    boolean value = true;

    GAssamHint() {
        rect.setFill(Color.web("#ffffff00"));
        rect.setStrokeWidth(2);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setLayoutX(-Utils.GRID_GAP / 2);
        rect.setLayoutY(-Utils.GRID_GAP / 2);
        rect.setStroke(Color.GREEN);
        this.getChildren().add(rect);
    }

    void setValue(boolean value) {
        this.value = value;
        this.update();
    }

    void update() {
        if (value) rect.setStroke(Color.GREEN);
        else rect.setStroke(Color.RED);
    }
}
