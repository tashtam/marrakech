package comp1110.ass2;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameCtrl {
    Group root;
    Marrakech game;
    Image assamImage = new Image("file:assets/game/assam.png");
    Image cyanImage = new Image("file:assets/game/cyan.png");
    Image purpleImage = new Image("file:assets/game/purple.png");
    Image redImage = new Image("file:assets/game/red.png");
    Image yellowImage = new Image("file:assets/game/yellow.png");
    Font font = Font.font("Consolas", FontWeight.EXTRA_BOLD, 16);

    public GameCtrl(Group root) {
        this.root = root;
    }

    public GameCtrl(Group root, Scene scene) {
        this.root = root;
        scene.setOnMousePressed(this::onClick);
        scene.setOnScroll(this::onScroll);
    }

    public void setState(String gameString) {
        this.game = new Marrakech(gameString);
    }

    public void setPlayerAmount(int playerAmount) {
        var playersString = "Pp03015iPr03015iPc03015iPy03015i".substring(0, playerAmount * 8);
        var gameString = playersString + "A33WBn00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00n00";
        this.game = new Marrakech(gameString);
    }

    void onClick(MouseEvent event) {
        MouseButton btn = event.getButton();
        if (btn == MouseButton.PRIMARY) this.onLeftClick(btn);
        else if (btn == MouseButton.SECONDARY) this.onRightClick(btn);
    }

    void onLeftClick(MouseButton btn) {
        System.out.println("左键");
    }

    void onRightClick(MouseButton btn) {
        System.out.println("右键");
        game.turnNext();
        this.display();
    }

    void onScroll(ScrollEvent event) {
        double se = event.getDeltaY();
        if (se > 0) this.onBackwardScroll();
        else this.onForwardScroll();
    }

    void onForwardScroll() {
        System.out.println("顺时针");
        game.assam.rotate(90);
        this.display();
    }

    void onBackwardScroll() {
        System.out.println("逆时针");
        game.assam.rotate(270);
        this.display();
    }

    void _displayBoard() {
        for (Tile tile : game.board.tiles) {
            var rug = tile.rug;
            int x, y;

            Rectangle r1;
            if (rug.positions[1] != null) {
                int w = game.TILE_SIZE;
                int h = game.TILE_SIZE;
                if (rug.positions[0].x != rug.positions[1].x) w = game.TILE_SIZE * 2 + game.TILE_GAP;
                if (rug.positions[0].y != rug.positions[1].y) h = game.TILE_SIZE * 2 + game.TILE_GAP;
                r1 = new Rectangle(w, h);
                x = Math.min(rug.positions[0].x, rug.positions[1].x);
                y = Math.min(rug.positions[0].y, rug.positions[1].y);
            } else {
                x = rug.positions[0].x;
                y = rug.positions[0].y;
                r1 = new Rectangle(game.TILE_SIZE, game.TILE_SIZE);
            }

            char color = rug.color;
            r1.setFill(game.getJavaFxColor(color));

            r1.setArcWidth(10);
            r1.setArcHeight(10);

            Text text = new Text();
            text.setFont(this.font);
            text.setText(String.format("%02d", rug.id));

            int layoutX = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * x;
            int layoutY = game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * y;
            r1.setLayoutX(layoutX);
            r1.setLayoutY(layoutY);
            text.setLayoutX(layoutX + game.TILE_SIZE / 2 + 10);
            text.setLayoutY(layoutY + game.TILE_SIZE / 2 + 20);
            this.root.getChildren().add(r1);
            this.root.getChildren().add(text);
        }
    }

    void displayBoard() {
        for (Tile tile : game.board.tiles) {
            if (tile.rug != null) continue;
            Rectangle r1 = new Rectangle(game.TILE_SIZE, game.TILE_SIZE);
            r1.setFill(game.getJavaFxColor(' '));
            r1.setArcWidth(10);
            r1.setArcHeight(10);
            int layoutX = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * tile.position.x;
            int layoutY = game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * tile.position.y;
            r1.setLayoutX(layoutX);
            r1.setLayoutY(layoutY);
            this.root.getChildren().add(r1);
        }

        for (Rug rug : game.board.rugs) {
            int x, y;

            var view = new ImageView();
            if (rug.color == 'c') view.setImage(cyanImage);
            else if (rug.color == 'y') view.setImage(yellowImage);
            else if (rug.color == 'r') view.setImage(redImage);
            else if (rug.color == 'p') view.setImage(purpleImage);

            view.setFitWidth(game.TILE_SIZE);
            view.setFitHeight(game.TILE_SIZE * 2 + game.TILE_GAP);

            if (rug.positions[1] != null) {
                x = Math.min(rug.positions[0].x, rug.positions[1].x);
                y = Math.min(rug.positions[0].y, rug.positions[1].y);
                x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * x;
                y = game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * y;
                if (rug.positions[0].x != rug.positions[1].x) {
                    x += game.TILE_SIZE / 2 + game.TILE_GAP / 2;
                    y -= game.TILE_SIZE / 2 + game.TILE_GAP / 2;
                    view.setRotate(90);
                }
            } else {
                view.setFitWidth(game.TILE_SIZE);
                view.setFitHeight(game.TILE_SIZE);
                x = rug.positions[0].x;
                y = rug.positions[0].y;
                x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * x;
                y = game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * y;
            }

            view.setLayoutX(x);
            view.setLayoutY(y);
            this.root.getChildren().add(view);

            Text text = new Text();
            text.setFont(this.font);
            text.setText(String.format("%02d", rug.id));
            this.root.getChildren().add(text);
        }
    }

    void displayAssam() {
        Assam assam = game.assam;
        IntPair pos = assam.position;
        var players = game.players;
        var index = game.currentPlayerIndex;
        var player = players[index];

        var view = new ImageView();
        view.setImage(assamImage);
        view.setFitWidth(game.TILE_SIZE - 10);
        view.setFitHeight(game.TILE_SIZE - 10);
        view.setRotate(assam.degree + 90);

        var x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * pos.x + 5;
        var y = game.OFFSET_Y + (game.TILE_SIZE + game.TILE_GAP) * pos.y + 5;
        view.setLayoutX(x);
        view.setLayoutY(y);
        this.root.getChildren().add(view);

        var circle = new Circle();
        circle.setCenterX(x + game.TILE_SIZE - 18);
        circle.setCenterY(y + 10);
        circle.setRadius(5);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.BLACK);

        var color = player.color;
        var javaFxColor = game.getJavaFxColor(color);
        circle.setFill(javaFxColor);
        this.root.getChildren().add(circle);
    }

    void displayInfo() {
        int x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * game.board.HEIGHT + 90;
        int y = game.OFFSET_Y;
        var rect = new Rectangle(x, y, 400, 200);
        rect.setFill(Color.web("#eee"));
        this.root.getChildren().add(rect);

        this.displayCurrentPlayerInfo();
        this.displayPlayersInfo();
    }

    void displayCurrentPlayerInfo() {
        var player = game.players[game.currentPlayerIndex];
        var color = player.color;
        var javaFxColor = game.getJavaFxColor(color);

        var text = new Text();
        text.setFont(font);
        text.setFill(javaFxColor);
        text.setText("current player: " + color);

        int x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * game.board.HEIGHT + 100;
        int y = game.OFFSET_Y + 20;
        text.setLayoutX(x);
        text.setLayoutY(y);
        this.root.getChildren().add(text);
    }

    void displayPlayersInfo() {
        int x = game.OFFSET_X + (game.TILE_SIZE + game.TILE_GAP) * game.board.HEIGHT + 100;
        int y = game.OFFSET_Y + 60;

        var text = new Text();
        text.setFont(font);
        text.setText("color / dirhams / rugs / out");
        text.setLayoutX(x);
        text.setLayoutY(y);
        this.root.getChildren().add(text);

        var players = game.players;
        for (int i = 0; i < players.length; i++) {
            var player = players[i];
            var color = player.color;
            var javaFxColor = game.getJavaFxColor(color);
            var content = String.format("%c / %02d / %02d / %b", player.color, player.coins, player.remainingRugNumber, player.out);

            text = new Text();
            text.setFont(font);
            text.setFill(javaFxColor);
            text.setText(content);

            y = game.OFFSET_Y + i * 30 + 90;
            text.setLayoutX(x);
            text.setLayoutY(y);
            this.root.getChildren().add(text);
        }
    }

    public void display() {
        this.displayBoard();
        this.displayAssam();
        this.displayInfo();
    }
}
