package comp1110.ass2.gui;

import comp1110.ass2.Player;
import javafx.scene.Group;
import javafx.scene.text.Text;

public class GPlayerInfo extends Group {
    Player player;
    Text text;

    GPlayerInfo(Player player) {
        this.player = player;
        text = new Text();
        text.setFont(GUtils.font);
        this.getChildren().add(text);
    }

    void update(Player currentPlayer) {
        text.setUnderline(this.player == currentPlayer);
        text.setFill(GUtils.getJavaFxColor(player.color));
        text.setText(String.format("%c, coins %03d, rugs %02d, %b", player.color, player.coins, player.remainingRugNumber, player.out));
    }
}
