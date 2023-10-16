package comp1110.ass2.gui;

import comp1110.ass2.Game;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Viewer extends Application {
    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;
    private final Group root = new Group();
    private final Group controls = new Group();
    private TextField boardTextField;
    GGame gGame;

    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state an array of two strings, representing the current game state
     */
    void displayState(String state) {
        if (gGame != null) root.getChildren().remove(gGame);
        gGame = new GGame(state);
        gGame.game.phase = -1;
        root.getChildren().add(gGame);
        root.getChildren().remove(controls);
        root.getChildren().add(controls);
        // FIXME Task 5: implement the simple state viewer [DONE]
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label boardLabel = new Label("Game State:");
        boardTextField = new TextField();
        boardTextField.setPrefWidth(800);
        boardTextField.setOnAction(event -> {
            displayState(boardTextField.getText());
        });
        Button button = new Button("Refresh");
        button.setOnAction(event -> {
            displayState(boardTextField.getText());
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
        var state = "Pc03214iPy02814iPp03014iPr03015iA02WBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00y00c00p01n00n00n00n00y00c00p01n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
        this.displayState(state);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
