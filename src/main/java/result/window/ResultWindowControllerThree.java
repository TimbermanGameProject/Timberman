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

public class ResultWindowControllerThree implements Initializable {

    @FXML
    private Button menuButton;

    @FXML
    private Label pointsLabelBronze;

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
        int playerThreePoints = GameWindow.playerPoints[2];

        if(playerOnePoints >= playerTwoPoints && playerOnePoints >= playerThreePoints){
            pointsLabelGold.setText("Player 1\nPoints: " + Integer.toString(playerOnePoints));
            if(playerTwoPoints >= playerThreePoints){
                pointsLabelSilver.setText("Player 2\nPoints: " + Integer.toString(playerTwoPoints));
                pointsLabelBronze.setText("Player 3\nPoints: " + Integer.toString(playerThreePoints));
            }
            else{
                pointsLabelSilver.setText("Player 3\nPoints: " + Integer.toString(playerThreePoints));
                pointsLabelBronze.setText("Player 2\nPoints: " + Integer.toString(playerTwoPoints));
            }
        }
        else if(playerTwoPoints >= playerOnePoints && playerTwoPoints >= playerThreePoints){
            pointsLabelGold.setText("Player 2\nPoints: " + Integer.toString(playerTwoPoints));
            if(playerOnePoints >= playerThreePoints){
                pointsLabelSilver.setText("Player 1\nPoints: " + Integer.toString(playerOnePoints));
                pointsLabelBronze.setText("Player 3\nPoints: " + Integer.toString(playerThreePoints));
            }
            else{
                pointsLabelSilver.setText("Player 3\nPoints: " + Integer.toString(playerThreePoints));
                pointsLabelBronze.setText("Player 1\nPoints: " + Integer.toString(playerOnePoints));
            }
        }
        else {
            pointsLabelGold.setText("Player 3\nPoints: " + Integer.toString(playerThreePoints));
            if(playerOnePoints >= playerTwoPoints){
                pointsLabelSilver.setText("Player 1\nPoints: " + Integer.toString(playerOnePoints));
                pointsLabelBronze.setText("Player 2\nPoints: " + Integer.toString(playerTwoPoints));
            }
            else{
                pointsLabelSilver.setText("Player 2\nPoints: " + Integer.toString(playerTwoPoints));
                pointsLabelBronze.setText("Player 1\nPoints: " + Integer.toString(playerOnePoints));
            }
        }
    }

}
