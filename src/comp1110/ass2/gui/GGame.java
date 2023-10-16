package comp1110.ass2.gui;

import comp1110.ass2.Game;
import comp1110.ass2.IntPair;
import comp1110.ass2.Player;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GGame extends Group {
    Rectangle backgroundRect = new Rectangle(GUtils.WINDOW_WIDTH, GUtils.WINDOW_HEIGHT);
    Game game;
    GBoard gBoard;
    GPanel gPanel;
    GMain gMain;

    void setGMain(GMain gMain) {
        this.gMain = gMain;
        this.setSceneCallback(gMain.scene);
    }

    void setSceneCallback(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (game.phase == 0) {
                var t = event.getText();
                var degree = 0;
                switch (t) {
                    case "w" -> degree = 0;
                    case "d" -> degree = 90;
                    case "s" -> degree = 180;
                    case "a" -> degree = 270;
                    default -> {
                        return;
                    }
                }
                var flag = game.assam.setDegree(degree);
                if (flag) {
                    gBoard.gAssam.update();
                    var c = game.players[game.currentPlayerIndex].color;
                    gPanel.gConsole.print("player " + c + " set assam direction to " + t);
                } else {
                    gPanel.gConsole.print("illegal direction!!");
                }
            } else if (game.phase == 2) {
                gBoard.rotateMarkDegree(90);
            }
        });

        scene.setOnMouseClicked(event -> {
            if (game.phase == 0) {
                if (game.isGameOver()) {
                    game.phase = -1;
                    String info;
                    ArrayList<Player> winner = game.getWinner();
                    if (winner.size() > 1) info = "game over!! It is tie!!";
                    info = "game over!! Winner is " + winner.get(0).color;
                    gPanel.gConsole.print(info);
                    return;
                }
                game.assam.confirmDegree();
                game.phase = 1;
                gPanel.gDie.displayDie(0);
                var c = game.players[game.currentPlayerIndex].color;
                gPanel.gConsole.print("player " + c + " confirm assam direction");

            } else if (game.phase == 1) {
                var step = game.rollDie();
                gPanel.gDie.displayDie(step);
                var posPath = game.assam.move(step);
                var player = game.players[game.currentPlayerIndex];
                var color = game.board.getTile(game.assam.position).getColor();
                var player2 = game.getPlayer(color);
                if (player2 != null && player2 != player) {
                    if (!player2.out) {
                        var payment = game.getPaymentAmount();
                        var c1 = player.color;
                        var c2 = player2.color;
                        gPanel.gConsole.print("player " + c1 + "need to pay " + payment + "coins to player " + c2);
                        player.payTo(player2, payment);
                    }
                }
                if (player.out) {
                    gPanel.gConsole.print("player " + player.color + " out!!");
                    game.phase = 0;
                    game.turnNext();
                    var c = game.getCurrentPlayer().color;
                    gPanel.gConsole.print("turn to player " + c);
                } else {
                    game.phase = 2;
                    gPanel.gConsole.print("please put a rug");
                }
            } else if (game.phase == 2) {
                var gTiles = gBoard.getMarkGTiles();
                if (gTiles.length == 2) {
                    var player = game.getCurrentPlayer();
                    var positions = new IntPair[]{gTiles[0].tile.position, gTiles[1].tile.position};
                    var rug = player.createRug(positions);
                    if (game.isPlacementValid(rug)) {
                        game.makePlacement(rug);
                        game.phase = 0;
                        game.turnNext();
                        var c = game.players[game.currentPlayerIndex].color;
                        gPanel.gConsole.print("turn to player " + c);
                    }
                }
            } else {
            }
            this.update();
        });
    }

    GGame(Game game) {
        this.game = game;

        backgroundRect.setFill(Color.WHITE);
        this.getChildren().add(backgroundRect);

        gBoard = new GBoard(this);
        gBoard.setLayoutX(50);
        gBoard.setLayoutY(50);
        this.getChildren().add(gBoard);

        gPanel = new GPanel(this);
        gPanel.setPlayers();
        gPanel.setLayoutX(600);
        gPanel.setLayoutY(50);
        this.getChildren().add(gPanel);

        this.update();
    }

    GGame(int playerAmount) {
        this(new Game(playerAmount));
    }

    GGame(String state) {
        this(new Game(state));
    }

    void update() {
        gBoard.update();
        gPanel.update();
    }
}
