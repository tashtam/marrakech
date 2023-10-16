package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application {
    private final Group root = new Group();

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        stage.setTitle("Marrakech");
        Scene scene = new Scene(root, GUtils.WINDOW_WIDTH, GUtils.WINDOW_HEIGHT);
        GMain gMain = new GMain(scene);
        root.getChildren().add(gMain);
        stage.setScene(scene);
        stage.show();
    }
}
