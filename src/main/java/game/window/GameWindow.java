package game.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import options.window.OptionsWindow;
import options.window.OptionsWindowController;
import start.window.StartWindow;

import java.io.IOException;
import java.util.Objects;

public class GameWindow extends Application {

    public static int numberOfPlayers;

    public GameWindow(){
        numberOfPlayers = OptionsWindowController.playerValue;
    }

    public Parent createContent(Stage stage){
        FlowPane flowPane = new FlowPane(); // MAIN PANE WHICH HOLDS EACH PLAYER'S PANES
        flowPane.getStyleClass().add("GameWindow"); // FOR CSS STYLING

        flowPane.setPrefHeight(stage.getScene().getHeight());
        flowPane.setPrefWidth(stage.getScene().getHeight());

        //ADDING PLAYER PANES HERE
        for(int i = 0;i<numberOfPlayers;i++){
            PlayerPane playerPane = new PlayerPane(stage);
            playerPane.getStyleClass().add("PlayerPane"); //FOR CSS STYLING
            flowPane.getChildren().add(playerPane);
        }
        return flowPane;
    }

    public void handleKeys(Scene scene){
        scene.setOnKeyPressed(e ->{
            switch(e.getCode()){
                case Q:
                    StartWindow startWindow = new StartWindow();
                    try {
                        startWindow.start((Stage)scene.getWindow());
                    } catch (IOException ex) {
                        ex.printStackTrace();
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
