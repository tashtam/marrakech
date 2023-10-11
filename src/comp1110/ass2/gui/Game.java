package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.Marrakech;
import comp1110.ass2.Rug;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class Game extends Application {
    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    Marrakech game;
    GMarrakech gMarrakech;

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        game = new Marrakech(4);
        gMarrakech = new GMarrakech(game);
        gMarrakech.update();
        root.getChildren().add(gMarrakech);

        scene.setOnMouseClicked(event -> {
            if (game.phase == 0) {
                if (game.isGameOver()) {
                    game.phase = -1;
                    return;
                }
                System.out.println("phase 0 click");
                if (game.assam.confirmDegree()) {
                    game.phase = 1;
                }
            } else if (game.phase == 1) {
                System.out.println("phase 1 click");
                var step = Marrakech.rollDie();
                game.assam.move(step);
                var player = game.players[game.currentPlayerIndex];
                var color = game.board.getTile(game.assam.position).getColor();
                var player2 = game.getPlayer(color);
                if (player2 != null && player2 != player) {
                    System.out.println("pay!");
                    player.payTo(player2, game.getPaymentAmount());
                }
                gMarrakech.gBoard.update();
                game.phase = 2;
            } else if (game.phase == 2) {
                System.out.println("phase 2 click");
                var gTiles = gMarrakech.gBoard.getHighlightGTiles();
                if (gTiles.length == 2) {
                    var player = game.players[game.currentPlayerIndex];
                    var positions = new IntPair[]{gTiles[0].tile.position, gTiles[1].tile.position};
                    var rug = new Rug(player.color, 15 - player.remainingRugNumber, positions);
                    if (game.isPlacementValid(rug)) {
                        game.makePlacement(rug);
                        game.phase = 0;
                        game.turnNext();
                    }
                }
            } else {
                System.out.println("game over!!!");
            }
            gMarrakech.update();
        });

        scene.setOnScroll(event -> {
            if (game.phase == 0) {
                game.assam.rotate(90);
                gMarrakech.gBoard.update();
            } else if (game.phase == 2) {
                gMarrakech.gBoard.rotateHighlightDegree(90);
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
