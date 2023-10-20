package comp1110.ass2.gui;

import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
/**
 * GUI for the die
 * @author Xin Yang Li (u7760022)
 */
public class GDie extends Group {
    Rectangle rect = new Rectangle(60, 60);
    Text text = new Text();

    GDie() {
        rect.setFill(Utils.getJavaFxColor(' '));
        this.getChildren().add(rect);
        rect.setArcHeight(30);
        rect.setArcWidth(30);
        text.setLayoutX(21);
        text.setLayoutY(41);
        text.setFont(Utils.bigFont);
        this.getChildren().add(text);
    }

    void displayDie(int value) {
        if (value == 0) text.setText("");
        else text.setText("" + value);
    }

}
