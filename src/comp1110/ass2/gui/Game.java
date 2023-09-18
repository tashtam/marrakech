package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    // constant
    private Font font = Font.font("Consolas", FontWeight.EXTRA_BOLD, 16);

    void displayBoard(Marrakech game) {
        for (Tile tile : game.getBoard().getTiles()) {
            Rectangle r1 = new Rectangle(game.TILE_SIZE, game.TILE_SIZE);
            Rug rug = tile.getRug();
            char color = ' ';
            if (rug != null) color = rug.getColor();
            r1.setFill(game.getJavaFxColor(color));
            r1.setStrokeWidth(2);
            r1.setStroke(Color.BLACK);
            r1.setArcWidth(10);
            r1.setArcHeight(10);

            Text text = new Text();
            text.setFont(this.font);

            if (rug != null) text.setText(String.format("%02d", rug.getId()));

            int layoutX = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * tile.getPosition().getX();
            int layoutY = game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * tile.getPosition().getY();
            r1.setLayoutX(layoutX);
            r1.setLayoutY(layoutY);
            text.setLayoutX(layoutX + game.TILE_SIZE / 2 + 10);
            text.setLayoutY(layoutY + game.TILE_SIZE / 2 + 20);
            this.root.getChildren().add(r1);
            this.root.getChildren().add(text);
        }
    }

    void displayAssam(Marrakech game) {
        Assam assam = game.getAssam();
        IntPair pos = assam.getPosition();
        var players = game.getPlayers();
        var index = game.getCurrentPlayerIndex();
        var player = players[index];

        Rectangle r1 = new Rectangle(game.TILE_SIZE / 2 + 5, game.TILE_SIZE / 2 + 5);
        r1.setFill(game.getJavaFxColor(player.getColor()));
        r1.setStrokeWidth(2);
        r1.setStroke(Color.BLACK);
        r1.setArcWidth(10);
        r1.setArcHeight(10);

        Text text = new Text();
        text.setFont(font);
        text.setText("" + assam.getDegree());

        StackPane stack = new StackPane(r1, text);
        stack.setLayoutX(game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * pos.getX());
        stack.setLayoutY(game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * pos.getY());
        this.root.getChildren().add(stack);
    }

    void displayInfo(Marrakech game) {
        int x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * game.getBoard().HEIGHT + 90;
        int y = game.OFFSET_Y;
        var rect = new Rectangle(x, y, 400, 200);
        rect.setFill(Color.web("#eee"));
        this.root.getChildren().add(rect);

        this.displayCurrentPlayerInfo(game);
        this.displayPlayersInfo(game);
    }

    void displayCurrentPlayerInfo(Marrakech game) {
        var player = game.getPlayers()[game.getCurrentPlayerIndex()];
        var color = player.getColor();
        var javaFxColor = game.getJavaFxColor(color);

        var text = new Text();
        text.setFont(font);
        text.setFill(javaFxColor);
        text.setText("current player: " + color);

        int x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * game.getBoard().HEIGHT + 100;
        int y = game.OFFSET_Y + 20;
        text.setLayoutX(x);
        text.setLayoutY(y);
        this.root.getChildren().add(text);
    }

    void displayPlayersInfo(Marrakech game) {
        int x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * game.getBoard().HEIGHT + 100;
        int y = game.OFFSET_Y + 60;

        var text = new Text();
        text.setFont(font);
        text.setText("color / dirhams / rugs / out");
        text.setLayoutX(x);
        text.setLayoutY(y);
        this.root.getChildren().add(text);

        var players = game.getPlayers();
        for (int i = 0; i < players.length; i++) {
            var player = players[i];
            var color = player.getColor();
            var javaFxColor = game.getJavaFxColor(color);
            var content = String.format(
                    "%c / %02d / %02d / %b",
                    player.getColor(),
                    player.getCoins(),
                    player.getRemainingRugNumber(),
                    player.isOut()
            );

            text = new Text();
            text.setFont(font);
            text.setFill(javaFxColor);
            text.setText(content);

            y = game.OFFSET_Y + i * 30 + 90;
            text.setLayoutX(x);
            text.setLayoutY(y);
            this.root.getChildren().add(text);
        }
    }

    void displayGame(Marrakech game) {
        this.displayBoard(game);
        this.displayAssam(game);
        this.displayInfo(game);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15

        // FIXME Task 7
        var game = new Marrakech("Pp03015iPr03015iPc03015iPy03015iA33SBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
        this.displayGame(game);

        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);

        scene.setOnMousePressed(event -> {
            MouseButton btn = event.getButton();
            if (btn == MouseButton.PRIMARY) {
                System.out.println("左键");

            } else if (btn == MouseButton.SECONDARY) {
                System.out.println("右键");

                game.turnNext();
                this.displayGame(game);
            }
        });

        scene.setOnScroll(event -> {
            double se = event.getDeltaY();
            if (se > 0) {
                System.out.println("逆时针");

            } else {
                System.out.println("顺时针");

            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
