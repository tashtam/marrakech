package comp1110.ass2.gui;

import comp1110.ass2.Tile;
import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GTile extends Group {
    Tile tile;
    Rectangle rect = new Rectangle(Utils.GRID_SIZE, Utils.GRID_SIZE);
    Text text;

    GTile(Tile tile) {
        this.tile = tile;
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        this.getChildren().add(rect);

        text = new Text();
        text.setLayoutX(Utils.GRID_SIZE - 30);
        text.setLayoutY(Utils.GRID_SIZE - 10);
        text.setFont(Utils.font);
        this.getChildren().add(text);
    }

    void update() {
        var color = tile.getColor();
        var javaFxColor = Utils.getJavaFxColor(color);
        rect.setFill(javaFxColor);
        if (tile.rug == null) text.setText("");
        else text.setText(String.format("%02d", tile.rug.id));
    }
}
