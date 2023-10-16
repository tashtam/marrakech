package comp1110.ass2.gui;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GDie extends Group {
    Rectangle rect = new Rectangle(60, 60);
    Text text = new Text();

    GDie() {
        rect.setFill(GUtils.getJavaFxColor(' '));
        this.getChildren().add(rect);

        text.setLayoutX(20);
        text.setLayoutY(50);
        text.setFont(GUtils.bigFont);
        this.getChildren().add(text);
    }

    void displayDie(int value) {
        if (value == 0) text.setText("");
        else text.setText("" + value);
    }

}
