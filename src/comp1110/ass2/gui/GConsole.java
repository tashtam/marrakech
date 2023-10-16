package comp1110.ass2.gui;

import javafx.scene.Group;
import javafx.scene.control.TextArea;

public class GConsole extends Group {
    TextArea area = new TextArea();

    GConsole() {
        area.setMaxWidth(380);
        area.setDisable(true);
        this.getChildren().add(area);
    }

    void print(String content) {
        area.appendText("\n" + content);
        area.setScrollTop(Double.MAX_VALUE);
    }
}
