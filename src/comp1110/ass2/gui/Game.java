package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    @Override
    public void start(Stage stage) throws Exception {
        // FIXME Task 7 and 15
        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.setOnMousePressed(event -> {
            MouseButton btn = event.getButton();
            if (btn == MouseButton.PRIMARY) {
                System.out.println("左键");
            } else if (btn == MouseButton.SECONDARY) {
                System.out.println("右键");
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
