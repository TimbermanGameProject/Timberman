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

public class ResultWindowControllerTwo implements Initializable {

    @FXML
    private Button menuButton;

    @FXML
    private Label pointsLabelGold;

    @FXML
    private Label pointsLabelSilver;

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
        int playerOnePoints = GameWindow.playerPoints[0];
        int playerTwoPoints = GameWindow.playerPoints[1];

        if(playerOnePoints >= playerTwoPoints){
            pointsLabelGold.setText("Player 1\nPoints: " + Integer.toString(playerOnePoints));
            pointsLabelSilver.setText("Player 2\nPoints: " + Integer.toString(playerTwoPoints));
        }
        else{
            pointsLabelGold.setText("Player 2\nPoints: " + Integer.toString(playerTwoPoints));
            pointsLabelSilver.setText("Player 1\nPoints: " + Integer.toString(playerOnePoints));
        }
    }
}

