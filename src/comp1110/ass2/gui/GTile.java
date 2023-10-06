package comp1110.ass2.gui;

import comp1110.ass2.Tile;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GTile extends Group {
    Tile tile;
    Rectangle rect;
    Text text;

    GTile(Tile tile, int size) {
        this.tile = tile;

        rect = new Rectangle(size, size);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        this.getChildren().add(rect);

        text = new Text();
        text.setLayoutX(size - 30);
        text.setLayoutY(size - 10);
        text.setFont(GUtils.font);
        this.getChildren().add(text);
    }

    void update() {
        var color = tile.getColor();
        var javaFxColor = GUtils.getJavaFxColor(color);
        rect.setFill(javaFxColor);
        if (tile.rug == null) text.setText("");
        else text.setText(String.format("%02d", tile.rug.id));
    }
}
