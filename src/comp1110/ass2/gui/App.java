package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.Player;
import comp1110.ass2.Rug;
import comp1110.ass2.Game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {
    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    Game game;
    GGame gGame;

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        stage.setTitle("Marrakech");
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        game = new Game(4);
        gGame = new GGame(game);
        gGame.update();
        root.getChildren().add(gGame);

        gGame.gPanel.gConsole.print("illegal direction!!");

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
                    gGame.gBoard.gAssam.update();
                    var c = game.players[game.currentPlayerIndex].color;
                    gGame.gPanel.gConsole.print("player " + c + " set assam direction to " + t);
                } else {
                    gGame.gPanel.gConsole.print("illegal direction!!");
                }
            } else if (game.phase == 2) {
                gGame.gBoard.rotateMarkDegree(90);
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
                    gGame.gPanel.gConsole.print(info);
                    return;
                }
                game.assam.confirmDegree();
                game.phase = 1;
                gGame.gPanel.gDie.displayDie(0);
                var c = game.players[game.currentPlayerIndex].color;
                gGame.gPanel.gConsole.print("player " + c + " confirm assam direction");

            } else if (game.phase == 1) {
                var step = game.rollDie();
                gGame.gPanel.gDie.displayDie(step);
                var posPath = game.assam.move(step);
                var player = game.players[game.currentPlayerIndex];
                var color = game.board.getTile(game.assam.position).getColor();
                var player2 = game.getPlayer(color);
                if (player2 != null && player2 != player) {
                    if (!player2.out) {
                        var payment = game.getPaymentAmount();
                        var c1 = player.color;
                        var c2 = player2.color;
                        gGame.gPanel.gConsole.print("player " + c1 + "need to pay " + payment + "coins to player " + c2);
                        player.payTo(player2, payment);
                    }
                }
                if (player.out) {
                    gGame.gPanel.gConsole.print("player " + player.color + " out!!");
                    game.phase = 0;
                    game.turnNext();
                    var c = game.getCurrentPlayer().color;
                    gGame.gPanel.gConsole.print("turn to player " + c);
                } else {
                    game.phase = 2;
                    gGame.gPanel.gConsole.print("please put a rug");
                }
            } else if (game.phase == 2) {
                var gTiles = gGame.gBoard.getMarkGTiles();
                if (gTiles.length == 2) {
                    var player = game.getCurrentPlayer();
                    var positions = new IntPair[]{gTiles[0].tile.position, gTiles[1].tile.position};
                    var rug = player.createRug(positions);
                    if (game.isPlacementValid(rug)) {
                        game.makePlacement(rug);
                        game.phase = 0;
                        game.turnNext();
                        var c = game.players[game.currentPlayerIndex].color;
                        gGame.gPanel.gConsole.print("turn to player " + c);
                    }
                }
            } else {
            }
            gGame.update();
        });

        stage.setScene(scene);
        stage.show();
    }
}
