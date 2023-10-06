package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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

        stage.setScene(scene);
        stage.show();
    }
}
