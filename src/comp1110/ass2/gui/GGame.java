package comp1110.ass2.gui;

import comp1110.ass2.Game;
import comp1110.ass2.Player;
import comp1110.ass2.Rug;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class GGame extends Group {
    Game game;
    GBoard gBoard;
    Group gPanel;
    GMain gMain;
    GMark gMark;
    GAssam gAssam;
    GAssamHint gAssamHint;
    GConsole gConsole;
    GPlayer[] gPlayers;
    GDie gDie;
    Timeline aiTimeline;
    EventHandler<KeyEvent> h1;
    EventHandler<KeyEvent> h2;
    Rug rug;

    boolean ctrlPressing = false;

    void setGMain(GMain gMain) {
        this.gMain = gMain;
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

    boolean pay() {
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

        if (player.out) {
            this.print("player " + player.color + " out!!");
        }

        return player.out;
    }

    void move() {
        var step = game.rollDie();
        gDie.displayDie(step);

        var posPath = game.assam.move(step);
        gAssam.update();

        var out = this.pay();
        if (out) {
            this.turnNext();
            this.setGamePhase(0);
        } else {
            this.print("please put a rug");
            this.setGamePhase(2);
        }
    }

    boolean confirmAssamDegree() {
        if (!game.assam.confirmDegree()) {
            gAssamHint.setValue(true);
            this.update();
            return false;
        }

        var c = game.players[game.currentPlayerIndex].color;
        this.print("player " + c + " confirm assam direction");
        return true;
    }

    void setGamePhase(int phase) {
        game.phase = phase;
        switch (phase) {
            case 0 -> this.beforePhase0();
            case 2 -> this.beforePhase2();
        }
    }

    void beforePhase2() {
        gMark.reset();
    }

    void turnNext() {
        game.turnNext();
        var player = game.getCurrentPlayer();
        this.print("turn to", "player", player.color);
    }

    void beforePhase0() {
        gMark.hide();
        gDie.displayDie(0);
        if (game.isGameOver()) {
            this.gameOver();
        } else if (game.getCurrentPlayer().ai) {
            aiTimeline.playFromStart();
        }
    }

    void putRug() {
        var positions = gMark.positions;
        var player = game.getCurrentPlayer();
        var rug = new Rug(player.color, 15 - player.remainingRugNumber, positions);
        if (game.isPlacementValid(rug)) {
            game.makePlacement(rug);
            this.turnNext();
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

    void onKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) ctrlPressing = false;
    }

    void OnKeyPressed(KeyEvent event) {
        var code = event.getCode();
        if (code == KeyCode.CONTROL) ctrlPressing = true;

        if (game.phase == 0) {
            switch (code) {
                case ENTER, SPACE -> {
                    if (this.confirmAssamDegree()) this.move();
                }
                case W, UP -> setAssamDegree(0);
                case D, RIGHT -> setAssamDegree(90);
                case S, DOWN -> setAssamDegree(180);
                case A, LEFT -> setAssamDegree(270);
            }
        } else if (game.phase == 2) {
            if (code == KeyCode.ENTER || code == KeyCode.SPACE) this.putRug();
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
        } else if (game.phase == -1) {
            this.gMain.backToTitle();
        }
    }

    void clearCallback(Scene scene) {
        scene.removeEventHandler(KeyEvent.KEY_RELEASED, h1);
        scene.removeEventHandler(KeyEvent.KEY_PRESSED, h2);
    }

    void setCallback(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, h1);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, h2);
    }

    void setGame(Game game) {
        h1 = this::onKeyReleased;
        h2 = this::OnKeyPressed;

        aiTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    this.setGamePhase(3);
                }),
                new KeyFrame(Duration.seconds(0.2), e -> {
                    game.aiPlayerSetDegree();
                    gAssam.update();
                }),
                new KeyFrame(Duration.seconds(0.4), e -> {
                    this.move();
                    this.update();
                }),
                new KeyFrame(Duration.seconds(0.6), e -> {
                    rug = game.aiPlayerPutRug();
                    gMark.positions = rug.positions;
                    gMark.update();
                }),
                new KeyFrame(Duration.seconds(0.8), e -> {
                    game.makePlacement(rug);
                    this.turnNext();
                    this.update();
                    this.setGamePhase(0);
                })
        );

        this.game = game;
        gPlayers = new GPlayer[4];

        for (int i = 0; i < 4; i++) {
            var gPlayer = new GPlayer(game);
            gPlayers[i] = gPlayer;
            gPlayer.setLayoutX((i % 2) * 200);
            gPlayer.setLayoutY((i / 2) * 110);
        }

        gDie = new GDie();
        gConsole = new GConsole();

        gBoard = new GBoard(game.board);
        gBoard.setLayoutX(50);
        gBoard.setLayoutY(50);
        this.getChildren().add(gBoard);

        gMark = new GMark(game.assam);
        gBoard.getChildren().add(gMark);

        gPanel = new Group();
        var len = game.players.length;
        for (int i = 0; i < 4; i++) {
            if (i < len) gPlayers[i].setPlayer(game.players[i]);
            else gPlayers[i].setPlayer(null);
        }

        gPanel.getChildren().addAll(gPlayers);
        gPanel.setLayoutX(600);
        gPanel.setLayoutY(50);
        this.getChildren().add(gPanel);

        gDie.setLayoutY(450);
        gPanel.getChildren().add(gDie);

        gAssam = new GAssam(game);
        gBoard.getChildren().add(gAssam);

        gAssamHint = new GAssamHint();
        gAssam.getChildren().add(gAssamHint);

        gPanel.getChildren().add(gConsole);
        gConsole.setLayoutY(250);

        this.update();
        this.setGamePhase(0);


    }

    GGame(int playerAmount, int aiPlayerAmount, int hardAIPlayerAmount) {
        var game = new Game(playerAmount, aiPlayerAmount, hardAIPlayerAmount);
        this.setGame(game);
    }

    GGame(String state) {
        var game = new Game(state);
        this.setGame(game);
    }

    void update() {
        gBoard.update();
        gAssam.update();
        for (GPlayer gPlayer : gPlayers) gPlayer.update();
    }
}
