package result.window;

import game.window.GameWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import start.window.StartWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultWindowControllerOne implements Initializable{

    @FXML
    private Button menuButton;

    @FXML
    private Label pointsLabel;

    @FXML
    private Button restartButton;

    @FXML
    private Label titleLabel;

    @FXML
    void restartGame(ActionEvent event) throws IOException{
        GameWindow gameWindow = new GameWindow();
        gameWindow.start((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        StartWindow startWindow = new StartWindow();
        startWindow.start((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pointsLabel.setText("Player 1\nPoints: " + GameWindow.playerPoints[0]);
    }

}
