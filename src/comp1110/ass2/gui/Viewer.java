package comp1110.ass2.gui;

import comp1110.ass2.IntPair;
import comp1110.ass2.Marrakech;
import comp1110.ass2.Rug;
import comp1110.ass2.Tile;
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
import javafx.scene.text.TextAlignment;
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
        Marrakech marrakech = new Marrakech(state);
        for (int i = 0; i < marrakech.BOARD_WIDTH; i++) {
            for (int j = 0; j < marrakech.BOARD_HEIGHT; j++) {
                Tile tile = marrakech.getTile(new IntPair(i, j));
                Rectangle r1 = new Rectangle(marrakech.TILE_SIZE, marrakech.TILE_SIZE);
                r1.setFill(tile.getJavaFxColor());
                r1.setStrokeWidth(2);
                r1.setStroke(Color.BLACK);
                r1.setArcWidth(10);
                r1.setArcHeight(10);

                Text text = new Text();
                Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
                text.setFont(font);

                Rug rug = tile.getRug();
                if (rug != null) text.setText(String.format("%02d", rug.getId()));

                StackPane stack = new StackPane(r1, text);
                stack.setLayoutX(marrakech.OFFSET_X + (marrakech.TILE_SIZE + marrakech.TILE_GAP) * i);
                stack.setLayoutY(marrakech.OFFSET_Y + (marrakech.TILE_SIZE + marrakech.TILE_GAP) * j);
                this.root.getChildren().add(stack);
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
