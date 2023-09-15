package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField boardTextField;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        Marrakech game = new Marrakech(state);
        for (int i = 0; i < game.BOARD_WIDTH; i++) {
            for (int j = 0; j < game.BOARD_HEIGHT; j++) {
                Tile tile = game.getTile(new IntPair(i, j));
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
                Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 16);
                text.setFont(font);

                if (rug != null) text.setText(String.format("%02d", rug.getId()));

//                StackPane stack = new StackPane(r1, text);
                int layoutX = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * j;
                r1.setLayoutX(layoutX);
                int layoutY = game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * i;
                r1.setLayoutY(layoutY);
                text.setLayoutX(layoutX + game.TILE_SIZE / 2 + 10);
                text.setLayoutY(layoutY + game.TILE_SIZE / 2 + 20);
                this.root.getChildren().add(r1);
                this.root.getChildren().add(text);
            }
        }

        {
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
            Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 16);
            text.setFont(font);
            text.setText("" + assam.getDegree());

            StackPane stack = new StackPane(r1, text);
            stack.setLayoutX(game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * pos.getY());
            stack.setLayoutY(game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * pos.getX());
            this.root.getChildren().add(stack);
        }

        {
            var players = game.getPlayers();
            var index = game.getCurrentPlayerIndex();
            var player = players[index];
            Text text = new Text();
            Font font = Font.font("Consolas", FontWeight.EXTRA_BOLD, 16);
            text.setFont(font);
            text.setFill(game.getJavaFxColor(player.getColor()));
            text.setText("current player: " + player.getColor());
            int infoLayoutX = (game.TILE_SIZE + game.TILE_GAP) * game.BOARD_WIDTH;
            text.setLayoutX(game.OFFSET_X + infoLayoutX + 100);
            text.setLayoutY(game.OFFSET_Y + 20);
            this.root.getChildren().add(text);

            for (int i = 0; i < players.length; i++) {
                player = players[i];
                text = new Text();
                text.setFont(font);
                text.setText(String.format("player: %c|dirhams: %02d|rugs: %02d", player.getColor(), player.getCoins(), player.getRemainingRugNumber()));
                text.setFill(game.getJavaFxColor(player.getColor()));
                text.setLayoutX(game.OFFSET_X + infoLayoutX + 100);
                text.setLayoutY(game.OFFSET_Y + i * 30 + 60);
                this.root.getChildren().add(text);
            }
        }
        // FIXME Task 5: implement the simple state viewer [DONE]
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                displayState(boardTextField.getText());
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(boardLabel, boardTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Marrakech Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
