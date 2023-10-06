package comp1110.ass2.gui;

import comp1110.ass2.Player;
import comp1110.ass2.Rug;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Objects;

public class GPlayerInfo extends Group {
    Player player;
    Text text;
    Rectangle playerRectanlge=new Rectangle(146,70);
    Image cyanRug=new
            Image("E:/Coding/Projects/comp1110-ass2/assets/game/cyan.png");
    Image purpleRug=new
            Image("E:/Coding/Projects/comp1110-ass2/assets/game/purple.png");
    Image redRug=new
            Image("E:/Coding/Projects/comp1110-ass2/assets/game/red.png");
    Image yellowRug=new
            Image("E:/Coding/Projects/comp1110-ass2/assets/game/yellow.png");
    double xOffset = 0;
    double yOffset = 0;

    GPlayerInfo(Player player) {
        this.player = player;
        text = new Text();
        text.setFont(GUtils.font);
        Text rugNums=new Text();
        this.getChildren().add(rugNums);
        this.getChildren().add(text);
        playerRectanlge.setLayoutX(60);
        playerRectanlge.setLayoutY(15);
        if (this.player.color=='c'){playerRectanlge.setFill(new ImagePattern(cyanRug));
        }
        else if (this.player.color=='p'){playerRectanlge.setFill(new ImagePattern(purpleRug));}
        else if (this.player.color=='r'){playerRectanlge.setFill(new ImagePattern(redRug));}
        else if (this.player.color=='y'){playerRectanlge.setFill(new ImagePattern(yellowRug));}
        this.getChildren().add(playerRectanlge);
    }

    void update(Player currentPlayer) {

        playerRectanlge.setOnMousePressed(event -> {
            xOffset = event.getSceneX() - playerRectanlge.getLayoutX();
            yOffset = event.getSceneY() - playerRectanlge.getLayoutY();
        });

        playerRectanlge.setOnMouseDragged(event -> {
            playerRectanlge.setLayoutX(event.getSceneX() - xOffset);
            playerRectanlge.setLayoutY(event.getSceneY() - yOffset);
        });


        text.setUnderline(this.player == currentPlayer);
        text.setFill(GUtils.getJavaFxColor(player.color));
        text.setText(String.format("%c, coins %03d, rugs %02d, %b", player.color, player.coins, player.remainingRugNumber, player.out));
    }
}
