package comp1110.ass2.gui;

import comp1110.ass2.Game;
import comp1110.ass2.Player;
import comp1110.ass2.Rug;
import comp1110.ass2.Utils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GGame extends Group {
    Rectangle backgroundRect = new Rectangle(Utils.WINDOW_WIDTH, Utils.WINDOW_HEIGHT);
    Game game;
    GBoard gBoard;
    GPanel gPanel;
    GMain gMain;
    GMark gMark;
    GAssam gAssam;
    GAssamHint gAssamHint;
    GConsole gConsole = new GConsole();
    boolean ctrlPressing = false;

    void setGMain(GMain gMain) {
        this.gMain = gMain;
        this.setSceneCallback(gMain.scene);
    }

    void print(Object... objects) {
        String s = "";
        for (Object object : objects) s += object + " ";
        gConsole.print(s);
    }

    void gameOver() {
        this.setGamePhase(-1);
        String info;
        ArrayList<Player> winner = game.getWinner();
        if (winner.size() > 1) info = "game over!! It is tie!!";
        else info = "game over!! Winner is " + winner.get(0).color;
        this.print(info);
    }

    void confirmAssamDegreeAndMove() {
        if (!game.assam.confirmDegree()) {
            gAssamHint.setValue(true);
            this.update();
            return;
        }

        gPanel.gDie.displayDie(0);
        var c = game.players[game.currentPlayerIndex].color;
        this.print("player " + c + " confirm assam direction");

        var step = game.rollDie();
        gPanel.gDie.displayDie(step);
        var posPath = game.assam.move(step);
        var player = game.players[game.currentPlayerIndex];
        var color = game.board.getTile(game.assam.position).getColor();
        var player2 = game.getPlayer(color);
        if (player2 != null && player2 != player) {
            if (!player2.out) {
                var payment = game.getPaymentAmount();
                var c1 = player.color;
                var c2 = player2.color;
                this.print("player " + c1 + "need to pay " + payment + "coins to player " + c2);
                player.payTo(player2, payment);
            }
        }

        System.out.println("move!");
        if (player.out) {
            System.out.println("is outQ!!");
            this.print("player " + player.color + " out!!");
            game.turnNext();
            c = game.getCurrentPlayer().color;
            this.print("turn to player " + c);
            this.setGamePhase(0);
        } else {
            this.print("please put a rug");
            this.setGamePhase(2);
        }
        this.update();
    }

    void setGamePhase(int phase) {
        game.phase = phase;
        switch (phase) {
            case 0 -> this.beforePhase0();
            case 2 -> this.beforePhase2();
        }
    }

    void beforePhase2() {
        gAssamHint.setValue(true);
        System.out.println("reset mark");
        gMark.reset();
    }

    void beforePhase0() {
        gMark.hide();
        if (game.isGameOver()) this.gameOver();
    }

    void putRug() {
        var positions = gMark.positions;
        System.out.println("put rug on " + positions[0] + " " + positions[1]);
        var player = game.getCurrentPlayer();
        var rug = new Rug(player.color, 15 - player.remainingRugNumber, positions);
        if (game.isPlacementValid(rug)) {
            System.out.println("success");
            game.makePlacement(rug);
            game.turnNext();
            var c = game.players[game.currentPlayerIndex].color;
            this.print("turn to player " + c);
            this.setGamePhase(0);
        }
        this.update();
    }

    void setAssamDegree(int degree) {
        game.assam.setDegree(degree);
        var value = game.assam.checkDegree();
        gAssamHint.setValue(value);
        this.update();
    }

    void setSceneCallback(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.CONTROL) ctrlPressing = false;
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.CONTROL) ctrlPressing = true;
        });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            var code = event.getCode();

            if (game.phase == 0) {
                if (code == KeyCode.ENTER) this.confirmAssamDegreeAndMove();
                else {
                    switch (code) {
                        case W, UP -> setAssamDegree(0);
                        case D, RIGHT -> setAssamDegree(90);
                        case S, DOWN -> setAssamDegree(180);
                        case A, LEFT -> setAssamDegree(270);
                    }
                }
            } else if (game.phase == 2) {
                if (code == KeyCode.ENTER) this.putRug();
                else if (ctrlPressing) {
                    switch (code) {
                        case W, UP -> gMark.quickChangeMode(0);
                        case D, RIGHT -> gMark.quickChangeMode(90);
                        case S, DOWN -> gMark.quickChangeMode(180);
                        case A, LEFT -> gMark.quickChangeMode(270);
                    }
                } else {
                    switch (code) {
                        case W, UP -> gMark.changeMode(0);
                        case D, RIGHT -> gMark.changeMode(90);
                        case S, DOWN -> gMark.changeMode(180);
                        case A, LEFT -> gMark.changeMode(270);
                    }
                }
            }
        });
    }

    GGame(Game game) {
        this.game = game;

        backgroundRect.setFill(Color.WHITE);
        this.getChildren().add(backgroundRect);

        gBoard = new GBoard(game.board);
        gBoard.setLayoutX(50);
        gBoard.setLayoutY(50);
        this.getChildren().add(gBoard);

        gMark = new GMark(game.assam);
        gBoard.getChildren().add(gMark);

        gPanel = new GPanel(this);
        gPanel.setPlayers();
        gPanel.setLayoutX(600);
        gPanel.setLayoutY(50);
        this.getChildren().add(gPanel);

        gAssam = new GAssam(game);
        gBoard.getChildren().add(gAssam);

        gAssamHint = new GAssamHint();
        gAssam.getChildren().add(gAssamHint);

        gPanel.getChildren().add(gConsole);
        gConsole.setLayoutY(250);

        this.update();
        this.setGamePhase(0);
    }

    GGame(int playerAmount) {
        this(new Game(playerAmount));
    }

    GGame(String state) {
        this(new Game(state));
    }

    void update() {
        gBoard.update();
        gPanel.update();
        gAssam.update();
    }
}
