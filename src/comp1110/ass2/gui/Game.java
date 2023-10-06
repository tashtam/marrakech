package comp1110.ass2.gui;

import comp1110.ass2.Marrakech;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
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

        scene.setOnMouseClicked(event -> {
            var btn = event.getButton();
            var ord = btn.ordinal();
            if (ord == 1) {
                if (game.phase == 0) {
                    System.out.println(game.assam.confirmDegree());
                    if (game.assam.confirmDegree()) game.phase = 1;
                } else if (game.phase == 1) {
                    game.phase = 2;
                } else if (game.phase == 2) {
                    game.phase = 0;
                    game.turnNext();
                }
            } else if (ord == 3) {

            }
            gMarrakech.update();
        });

        scene.setOnScroll(event -> {
            if (game.phase == 0) {
                game.assam.rotate(90);
            } else if (game.phase == 2) {
                gMarrakech.gBoard.rotateRugPlaceDegree(90);
            }
            gMarrakech.update();
        });

        // method 1: move the assam to the edge but not pass the edge
        // method 2: assume assam is on the edge and he want to step forward, then you need return the next position he will be

        stage.setScene(scene);
        stage.show();
    }
}
