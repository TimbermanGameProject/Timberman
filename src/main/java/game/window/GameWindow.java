package game.window;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import options.window.OptionsWindow;
import options.window.OptionsWindowController;
import result.window.ResultWindow;
import start.window.StartWindow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameWindow extends Application {

    public static int numberOfPlayers;
    private ArrayList<PlayerPane> players;
    public static int[] playerPoints;
    private int startTime = OptionsWindowController.timeValue * 60;
    private int delay = 1000;
    private Timeline timer;

    public GameWindow(){
        numberOfPlayers = OptionsWindowController.playerValue;
        players = new ArrayList<>();
        playerPoints = new int[numberOfPlayers];
    }

    public Parent createContent(Stage stage){
        HBox playersContainer = new HBox(); // MAIN PANE WHICH HOLDS EACH PLAYER'S PANES
        playersContainer.getStyleClass().add("GameWindow"); // FOR CSS STYLING
        playersContainer.setSpacing(10);
        playersContainer.setAlignment(Pos.CENTER);
        playersContainer.setPrefWidth(stage.getScene().getWidth());

        //ADDING PLAYER PANES HERE
        for(int i = 0;i<numberOfPlayers;i++){
            PlayerPane playerPane = new PlayerPane(stage, i);
            players.add(playerPane);
            playersContainer.getChildren().add(playerPane);
        }
        timerInterval(stage);

        return playersContainer;
    }

    private void timerInterval(Stage stage) {
         timer = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        startTime--;
                        for (PlayerPane player : players) {
                            player.changeTime(startTime);
                        }
                        if(startTime == 0){
                            timer.stop();
                            for(int i = 0;i<numberOfPlayers;i++){
                                playerPoints[i] = players.get(i).getPoints();
                            }
                            stage.getScene().setOnKeyReleased(null);
                            ResultWindow resultWindow = new ResultWindow(numberOfPlayers);
                            try {
                                resultWindow.start(stage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
        );
        timer.setCycleCount(startTime);
        timer.play();
    }

    public void handleKeys(Scene scene) {
        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case Q:
                    timer.stop();
                    for (int i = 0; i < numberOfPlayers; i++) {
                        playerPoints[i] = players.get(i).getPoints();
                    }
                    scene.setOnKeyReleased(null);
                    ResultWindow resultWindow = new ResultWindow(numberOfPlayers);
                    try {
                        startTime = 0;
                        resultWindow.start((Stage) scene.getWindow());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case A:
                    System.out.println("essa KLINALES A");
                    players.get(0).placeLumberjack(PlayerPane.LEFT_SIDE);
                    players.get(0).lowerBranches();
                    players.get(0).checkForCollision();
                    players.get(0).addBranch();
                    break;
                case D:
                    players.get(0).placeLumberjack(PlayerPane.RIGHT_SIDE);
                    players.get(0).lowerBranches();
                    players.get(0).checkForCollision();
                    players.get(0).addBranch();
                    break;
                case J:
                    if (numberOfPlayers >= 2) {
                        players.get(1).placeLumberjack(PlayerPane.LEFT_SIDE);
                        players.get(1).lowerBranches();
                        players.get(1).checkForCollision();
                        players.get(1).addBranch();
                    }
                    break;
                case L:
                    if (numberOfPlayers >= 2) {
                        players.get(1).placeLumberjack(PlayerPane.RIGHT_SIDE);
                        players.get(1).lowerBranches();
                        players.get(1).checkForCollision();
                        players.get(1).addBranch();
                    }
                    break;
                case NUMPAD4:
                    if (numberOfPlayers == 3) {
                        players.get(2).placeLumberjack(PlayerPane.LEFT_SIDE);
                        players.get(2).lowerBranches();
                        players.get(2).checkForCollision();
                        players.get(2).addBranch();
                    }
                    break;
                case NUMPAD6:
                    if (numberOfPlayers == 3) {
                        players.get(2).placeLumberjack(PlayerPane.RIGHT_SIDE);
                        players.get(2).lowerBranches();
                        players.get(2).checkForCollision();
                        players.get(2).addBranch();
                    }
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void start(Stage stage) throws IOException {
        String css = Objects.requireNonNull(this.getClass().getResource("/GameWindow/style.css")).toExternalForm();
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(css);
        stage.getScene().setRoot(createContent(stage));
        handleKeys(stage.getScene());
    }
}
