package comp1110.ass2.gui;

import comp1110.ass2.Game;
import comp1110.ass2.IntPair;
import comp1110.ass2.Player;
import comp1110.ass2.Rug;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.ArrayList;

public class App {
    Scene scene;
    Group root;
    MainPage mainPage;
    GamePage gamePage;
    Page pausePage;
    Page winnerPage;
    Page currentPage;

    App(Scene _scene, Group _root) {
        scene = _scene;
        root = _root;

        // create pages
        mainPage = new MainPage(this);
        gamePage = new GamePage(this);

        // set main page as current page
        currentPage = mainPage;
        root.getChildren().add(currentPage.root);
    }

    void switchPage(Page page) {
        root.getChildren().remove(currentPage.root);
        currentPage = page;
        root.getChildren().add(currentPage.root);
    }
}

class Page {
    App app;
    Group root;

    Page(App _app) {
        app = _app;
        root = new Group();
    }

    <T extends Event> void addEventHandler(EventType<T> type, EventHandler<? super T> handler) {
        app.scene.addEventHandler(type, event -> {
            if (app.currentPage == this) {
                handler.handle(event);
            }
        });
    }
}

class MainPage extends Page {
    RadioGroup playerRG;
    RadioGroup aiPlayerRG;
    RadioGroup hardAIPlayerRG;
    RadioGroup glassModeRG;

    Button beginBtn;

    MainPage(App _app) {
        super(_app);

        var labels = new String[]{"0", "1", "2", "3", "4"};
        playerRG = new RadioGroup("player", labels);
        root.getChildren().add(playerRG);

        aiPlayerRG = new RadioGroup("ai player", labels);
        aiPlayerRG.setLayoutX(100);
        root.getChildren().add(aiPlayerRG);

        hardAIPlayerRG = new RadioGroup("hard ai player", labels);
        hardAIPlayerRG.setLayoutX(200);
        root.getChildren().add(hardAIPlayerRG);

        glassModeRG = new RadioGroup("glass mode", new String[]{"off", "on"});
        glassModeRG.setLayoutX(300);
        root.getChildren().add(glassModeRG);

        beginBtn = new Button("begin game");
        beginBtn.setLayoutY(200);
        beginBtn.setOnMouseClicked(event -> {
            var playerAmount = playerRG.getValue();
            var aiPlayerAmount = aiPlayerRG.getValue();
            var hardAIPlayerAmount = hardAIPlayerRG.getValue();
            var glassMode = glassModeRG.getValue() == 1;
            int n = playerAmount + aiPlayerAmount + hardAIPlayerAmount;
            if (n < 2 || n > 4) return;
            app.gamePage.setGame(playerAmount, aiPlayerAmount, hardAIPlayerAmount, glassMode);
            app.switchPage(app.gamePage);
        });
        root.getChildren().add(beginBtn);

        root.setLayoutX(400);
        root.setLayoutY(200);

    }
}

class RadioGroup extends Group {
    Label title;
    RadioButton[] btns;
    ToggleGroup tg;
    int[] values;
    String[] labels;

    RadioGroup(String _title, String[] _labels) {
        tg = new ToggleGroup();
        labels = _labels;

        var n = labels.length;
        values = new int[n];
        for (int i = 0; i < n; i++) values[i] = i;

        // title
        title = new Label(_title);

        // btns
        btns = new RadioButton[n];
        for (int i = 0; i < n; i++) {
            var btn = new RadioButton(labels[i]);
            btn.setLayoutY(i * 30 + 30);
            btn.setToggleGroup(tg);
            btns[i] = btn;
        }
        btns[0].setSelected(true);

        this.getChildren().add(title);
        this.getChildren().addAll(btns);
    }

    int getValue() {
        var btn = (RadioButton) tg.getSelectedToggle();
        for (int i = 0; i < btns.length; i++) {
            if (btn == btns[i]) return values[i];
        }
        return 0;
    }
}

class GamePage extends Page {
    Game game;
    GBoard gBoard;
    GPanel gPanel;
    boolean ctrlPressing = false;
    boolean banUserInput = false;

    GamePage(App _app) {
        super(_app);

        this.addEventHandler(KeyEvent.KEY_RELEASED, this::onKeyReleased);
        this.addEventHandler(KeyEvent.KEY_PRESSED, this::onKeyPressed);

        // panel
        gPanel = new GPanel();
        root.getChildren().add(gPanel);

        // board
        gBoard = new GBoard();
        root.getChildren().add(gBoard);
    }

    void setGame(int playerAmount, int aiPlayerAmount, int hardAIPlayerAmount, boolean glassMode) {
        int n = playerAmount + aiPlayerAmount + hardAIPlayerAmount;
        if (n < 2 || n > 4) return;

        var game = new Game(playerAmount, aiPlayerAmount, hardAIPlayerAmount);
        if (glassMode) {
            for (Player player : game.players) {
                player.coins = 1;
            }
        }
        this.setGame(game);
    }

    void setGame(Game game) {
        this.game = game;

        gPanel.updatePlayers(game.players);
        var currentPlayer = game.getCurrentPlayer();
        gPanel.setCurrentPlayer(currentPlayer, game.currentPlayerIndex);
        gBoard.clearAll();
        gBoard.gAssam.updateAssamDegree(game.assam.degree);
        gBoard.gAssam.updateAssamPos(game.assam.position);
        gBoard.gAssam.updateCurrentPlayer(currentPlayer);

        banUserInput = currentPlayer.ai;
        if (currentPlayer.ai) this.ai();
    }

    void print(Object... objects) {
        StringBuilder s = new StringBuilder();
        for (Object object : objects) s.append(object).append(" ");
        gPanel.gConsole.print(s.toString());
    }

    void setAssamDegree(int degree) {
        // logic
        game.assam.setDegree(degree);

        // gui
        gBoard.gAssam.updateAssamDegree(game.assam.degree);
        var value = game.assam.checkDegree();
        gBoard.gAssam.gAssamHint.update(value);
    }

    void changePutMode(int degree) {
        gBoard.gMark.changeMode(degree, ctrlPressing);
        var currentPlayer = game.getCurrentPlayer();
        gBoard.gMark.update(game.assam.position);
        var positions = gBoard.gMark.positions;
        var flag = currentPlayer.checkPositionsValid(positions);
        gBoard.gAssam.gAssamHint.update(flag);
    }

    boolean confirmAssamDegree() {
        if (!game.assam.confirmDegree()) {
            gBoard.gAssam.gAssamHint.update(true);
            gBoard.gAssam.updateAssamDegree(game.assam.degree);
            return false;
        }

        this.print("player", game.getCurrentPlayer().color, "set assam degree", game.assam.degree);
        return true;
    }

    void move() {
        // logic
        var step = game.rollDie();

        // gui
        gPanel.gDie.displayDie(step);

        // logic
        game.assam.move(step);

        // gui
        gBoard.gAssam.updateAssamDegree(game.assam.degree);
        gBoard.gAssam.updateAssamPos(game.assam.position);
    }

    boolean pay() {
        var currentPlayer = game.getCurrentPlayer();
        currentPlayer.pay();
        gPanel.updatePlayers(game.players);
        return currentPlayer.out;
    }

    void gameOver() {
        game.phase = 3;
        banUserInput = false;

        String info;
        ArrayList<Player> winner = game.getWinner();
        if (winner.size() > 1) info = "game over!! It is tie!!";
        else info = "game over!! Winner is " + winner.get(0).color;
        this.print(info);
    }

    void setTimeOut(double timeout, EventHandler e) {
        var aiTimeline = new Timeline(new KeyFrame(Duration.seconds(timeout), e));
        aiTimeline.playFromStart();
    }

    void ai() {
        var currentPlayer = game.getCurrentPlayer();
        Rug[] rugs = new Rug[1];
        this.setTimeOut(0.1, event -> {
            currentPlayer.aiPlayerSetDegree();
            gBoard.gAssam.updateAssamDegree(game.assam.degree);

            this.setTimeOut(0.1, event1 -> {
                this.move();
                rugs[0] = currentPlayer.aiPlayerPutRug();
                gBoard.gMark.positions = rugs[0].positions;
                gBoard.gMark.update();
                gBoard.gAssam.updateAssamDegree(game.assam.degree);
                gBoard.gAssam.updateAssamPos(game.assam.position);

                var out = this.pay();
                if (out) {
                    this.setTimeOut(0.1, event2 -> {
                        this.turnNext();
                    });
                } else {
                    this.setTimeOut(0.1, event2 -> {
                        game.makePlacement(rugs[0]);
                        gBoard.updateRug(game.board, rugs[0]);
                        gPanel.updatePlayers(game.players);
                        this.turnNext();
                    });
                }
            });
        });
    }

    void turnNext() {
        // logic
        game.phase = 0;
        game.turnNext();

        // gui
        var currentPlayer = game.getCurrentPlayer();
        gPanel.setCurrentPlayer(currentPlayer, game.currentPlayerIndex);
        gBoard.gAssam.updateCurrentPlayer(currentPlayer);

        if (game.isGameOver()) {
            this.gameOver();
            return;
        }

        // ban user inputs
        banUserInput = currentPlayer.ai;
        if (currentPlayer.ai) this.ai();
    }

    boolean putRug() {
        var positions = gBoard.gMark.positions;
        var currentPlayer = game.getCurrentPlayer();
        var rug = currentPlayer.createRug(positions);
        if (!game.isPlacementValid(rug)) return false;

        // logic
        game.makePlacement(rug);

        // gui
        gBoard.updateRug(game.board, rug);
        gPanel.updatePlayers(game.players);
        return true;
    }

    void onGamePhase0(KeyCode code) {
        switch (code) {
            case ENTER, SPACE -> {
                var flag = this.confirmAssamDegree();
                if (!flag) return;
                this.move();

                var out = this.pay();
                if (out) {
                    this.turnNext();
                    return;
                }

                game.phase = 2;
                gBoard.gMark.update(game.assam.position);
                gBoard.gMark.setVisible(true);
                var positions = gBoard.gMark.positions;
                var currentPlayer = game.getCurrentPlayer();
                var flag2 = currentPlayer.checkPositionsValid(positions);
                gBoard.gAssam.gAssamHint.update(flag2);
            }
            case W, UP -> this.setAssamDegree(0);
            case D, RIGHT -> this.setAssamDegree(90);
            case S, DOWN -> this.setAssamDegree(180);
            case A, LEFT -> this.setAssamDegree(270);
        }
    }

    void onGamePhase2(KeyCode code) {
        switch (code) {
            case ENTER, SPACE -> {
                var flag = this.putRug();
                if (!flag) return;
                gBoard.gMark.setVisible(false);
                this.turnNext();
            }
            case W, UP -> this.changePutMode(0);
            case D, RIGHT -> this.changePutMode(90);
            case S, DOWN -> this.changePutMode(180);
            case A, LEFT -> this.changePutMode(270);
        }
    }

    void onKeyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.CONTROL) ctrlPressing = false;
    }

    void onKeyPressed(KeyEvent event) {
        var code = event.getCode();
        if (code == KeyCode.CONTROL) ctrlPressing = true;
        if (banUserInput) return;

        if (game.phase == 0) this.onGamePhase0(code);
        else if (game.phase == 2) this.onGamePhase2(code);
        else if (game.phase == 3) app.switchPage(app.mainPage);
    }
}


