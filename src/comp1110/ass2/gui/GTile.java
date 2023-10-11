package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import comp1110.ass2.Tile;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GTile extends Group {
    GBoard gBoard;
    Marrakech game;
    Tile tile;
    Rectangle rect;
    Text text;
    boolean highlight = false;

    GTile(Marrakech game, Tile tile, int size, GBoard gBoard) {
        this.game = game;
        this.tile = tile;
        this.gBoard = gBoard;

        rect = new Rectangle(size, size);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setOnMouseEntered(event -> {
            if (game.phase == 2) {
                gBoard.setHighlightPosition(tile.position);
            }
        });
        rect.setStroke(Color.BLACK);
        this.getChildren().add(rect);

        text = new Text();
        text.setLayoutX(size - 30);
        text.setLayoutY(size - 10);
        text.setFont(GUtils.font);
        this.getChildren().add(text);
    }

    void setHighlight(boolean value) {
        highlight = value;
        this.update();
    }

    void update() {
        var color = tile.getColor();
        var javaFxColor = GUtils.getJavaFxColor(color);
        rect.setFill(javaFxColor);
        if (tile.rug == null) text.setText("");
        else text.setText(String.format("%02d", tile.rug.id));
        rect.setStrokeWidth(highlight && gBoard.game.phase == 2 ? 5 : 0);
    }
}
