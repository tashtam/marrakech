package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GPanel extends Group {
    Marrakech game;
    GPlayerInfo[] gPlayerInfos;

    GPanel(Marrakech game) {
        this.game = game;

        var rect = new Rectangle(400, 100);
        rect.setFill(Color.web("#333"));
        this.getChildren().add(rect);

        gPlayerInfos = new GPlayerInfo[game.players.length];
        for (int i = 0; i < game.players.length; i++) {
            var gPlayerInfo = new GPlayerInfo(game.players[i]);
            gPlayerInfo.setLayoutX(20);
            gPlayerInfo.setLayoutY(i * 20 + 20);
            gPlayerInfos[i] = gPlayerInfo;
            this.getChildren().add(gPlayerInfo);
        }
    }

    void update() {
        for (GPlayerInfo gPlayerInfo : gPlayerInfos) {
            gPlayerInfo.update(game.players[game.currentPlayerIndex]);
        }
    }
}
