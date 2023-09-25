package comp1110.ass2.gui;

import comp1110.ass2.GameCtrl;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {
    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCtrl game = new GameCtrl(root, scene);
        game.setPlayerAmount(4);
        game.display();
        stage.setScene(scene);
        stage.show();
    }
}
