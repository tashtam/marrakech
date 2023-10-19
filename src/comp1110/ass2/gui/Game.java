package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Xin Yang Li (u7760022)
 */
public class Game extends Application {
    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        stage.setTitle("Marrakech");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        GMain gMain = new GMain(scene, root);
        root.getChildren().add(gMain);
        stage.setScene(scene);
        stage.show();
    }
}
