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

    Marrakech game = null;

    void display() {
        this.root.getChildren().clear();

        for (int i = 0; i < this.game.BOARD_WIDTH; i++) {
            for (int j = 0; j < this.game.BOARD_HEIGHT; j++) {
                Tile tile = this.game.getTile(new IntPair(i, j));
                Rectangle r1 = new Rectangle(this.game.TILE_SIZE, this.game.TILE_SIZE);
                Rug rug = tile.getRug();
                char color = ' ';
                if (rug != null) color = rug.getColor();
                r1.setFill(this.game.getJavaFxColor(color));
                r1.setStrokeWidth(2);
                r1.setStroke(Color.BLACK);
                r1.setArcWidth(10);
                r1.setArcHeight(10);

                Text text = new Text();
                Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 16);
                text.setFont(font);

                if (rug != null) text.setText(String.format("%02d", rug.getId()));

//                StackPane stack = new StackPane(r1, text);
                int layoutX = this.game.OFFSET_X + (this.game.TILE_SIZE + this.game.TILE_GAP) * i;
                r1.setLayoutX(layoutX);
                int layoutY = this.game.OFFSET_Y + (this.game.TILE_SIZE + this.game.TILE_GAP) * j;
                r1.setLayoutY(layoutY);
                text.setLayoutX(layoutX + this.game.TILE_SIZE / 2);
                text.setLayoutY(layoutY + this.game.TILE_SIZE / 2 + 20);
                this.root.getChildren().add(r1);
                this.root.getChildren().add(text);
            }
        }

        {
            Assam assam = this.game.getAssam();
            IntPair pos = assam.getPosition();
            var players = this.game.getPlayers();
            var index = this.game.getCurrentPlayerIndex();
            var player = players[index];

            Rectangle r1 = new Rectangle(this.game.TILE_SIZE / 2 + 5, this.game.TILE_SIZE / 2 + 5);
            r1.setFill(game.getJavaFxColor(player.getColor()));
            r1.setStrokeWidth(2);
            r1.setStroke(Color.BLACK);
            r1.setArcWidth(10);
            r1.setArcHeight(10);

            Text text = new Text();
            Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 16);
            text.setFont(font);
            text.setText("" + assam.getDegree());

            StackPane stack = new StackPane(r1, text);
            stack.setLayoutX(this.game.OFFSET_X + (this.game.TILE_SIZE + this.game.TILE_GAP) * pos.getX());
            stack.setLayoutY(this.game.OFFSET_Y + (this.game.TILE_SIZE + this.game.TILE_GAP) * pos.getY());
            this.root.getChildren().add(stack);
        }

        {
            var players = this.game.getPlayers();
            var index = this.game.getCurrentPlayerIndex();
            var player = players[index];
            Text text = new Text();
            Font font = Font.font("Consolas", FontWeight.EXTRA_BOLD, 16);
            text.setFont(font);
            text.setFill(this.game.getJavaFxColor(player.getColor()));
            text.setText("current player: " + player.getColor());
            int infoLayoutX = (this.game.TILE_SIZE + this.game.TILE_GAP) * this.game.BOARD_WIDTH;
            text.setLayoutX(this.game.OFFSET_X + infoLayoutX + 100);
            text.setLayoutY(this.game.OFFSET_Y + 20);
            this.root.getChildren().add(text);

            for (int i = 0; i < players.length; i++) {
                player = players[i];
                text = new Text();
                text.setFont(font);
                text.setText(String.format("player: %c|dirhams: %02d|rugs: %02d", player.getColor(), player.getCoins(), player.getRemainingRugNumber()));
                text.setFill(this.game.getJavaFxColor(player.getColor()));
                text.setLayoutX(this.game.OFFSET_X + infoLayoutX + 100);
                text.setLayoutY(this.game.OFFSET_Y + i * 30 + 60);
                this.root.getChildren().add(text);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15

        // FIXME Task 7
        this.game = new Marrakech("Pp03015iPr03015iPc03015iPy03015iA33SBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00");
        this.display();

        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnMousePressed(event -> {
            MouseButton btn = event.getButton();
            if (btn == MouseButton.PRIMARY) {
                System.out.println("左键");
            } else if (btn == MouseButton.SECONDARY) {
                System.out.println("右键");
                game.turnNext();
                this.display();
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
