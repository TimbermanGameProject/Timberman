package start.window;

import game.window.GameWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StartWindowController implements Initializable {
    public static boolean checkboxValue = false;
    public static int playerValue = 1;
    public static int timeValue = 1;


    @FXML
    private Button exitButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button startButton;

    @FXML
    private Label titleLabel;

    @FXML
    private VBox optionsContainer;
    @FXML
    private VBox mainButtonsContainer;
    @FXML
    private Button hideMenuButton;

    @FXML
    private CheckBox music;
    @FXML
    private TextField players;
    @FXML
    private TextField roundTime;


    @FXML
    protected void onReleaseShowOptionsContainer() {
        optionsContainer.setVisible(true);
        optionsContainer.managedProperty().bind(optionsContainer.visibleProperty());

        mainButtonsContainer.setVisible(false);
        mainButtonsContainer.managedProperty().bind(optionsContainer.visibleProperty());

    }

    @FXML
    protected void onReleaseHideOptionsContainer() {
        int playerValueTemp;
        int timeValueTemp;
        try {
            playerValueTemp = Integer.parseInt(players.getCharacters().toString());
        } catch (Exception e ){
            playerValueTemp = 1;
        }
        try {
            timeValueTemp = Integer.parseInt(roundTime.getCharacters().toString());
        } catch (Exception e){
            timeValueTemp = 1;
        }

        if(playerValueTemp < 1 || playerValueTemp > 3) playerValueTemp = 1;
        if(timeValueTemp < 1 || timeValueTemp > 10) timeValueTemp = 1;

        playerValue = playerValueTemp;
        timeValue = timeValueTemp;


        optionsContainer.setVisible(false);
        optionsContainer.managedProperty().bind(optionsContainer.visibleProperty());

        mainButtonsContainer.setVisible(true);
        mainButtonsContainer.managedProperty().bind(optionsContainer.visibleProperty());
    }

    @FXML
    protected void onReleaseExitGame() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    protected void onActionToggleMusic() {
        checkboxValue = !checkboxValue;
    }

    public void startButtonClicked(ActionEvent event) throws IOException {
        GameWindow gameWindow = new GameWindow();
        gameWindow.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        music.setSelected(checkboxValue);
        players.setText(Integer.toString(playerValue));
        roundTime.setText(Integer.toString(timeValue));
    }
}
