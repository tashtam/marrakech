package comp1110.ass2.gui;

import comp1110.ass2.Tile;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GTile extends Group {
    static final int SIZE = 70;

    GGame gGame;
    Tile tile;
    Rectangle rect;
    Text text;
    boolean mark = false;

    GTile(GGame gGame, Tile tile) {
        this.gGame = gGame;
        this.tile = tile;

        rect = new Rectangle(SIZE, SIZE);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setOnMouseEntered(event -> {
            if (gGame.game.phase == 2) {
                gGame.gBoard.setMarkPosition(tile.position);
            }
        });
        rect.setStroke(Color.BLACK);
        this.getChildren().add(rect);

        text = new Text();
        text.setLayoutX(SIZE - 30);
        text.setLayoutY(SIZE - 10);
        text.setFont(GUtils.font);
        this.getChildren().add(text);
    }

    void setMark(boolean value) {
        mark = value;
        this.update();
    }

    void update() {
        var color = tile.getColor();
        var javaFxColor = GUtils.getJavaFxColor(color);
        rect.setFill(javaFxColor);
        if (tile.rug == null) text.setText("");
        else text.setText(String.format("%02d", tile.rug.id));
        rect.setStrokeWidth(mark && gGame.game.phase == 2 ? 5 : 0);
    }
}
