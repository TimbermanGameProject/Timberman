package result.window;

import game.window.GameWindow;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import start.window.StartWindow;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.ResourceBundle;

public class ResultWindowControllerOne implements Initializable {

    @FXML
    private Button menuButton;

    @FXML
    private Button restartButton;

    @FXML
    private HBox resultsContainer;

    @FXML
    void restartGame(ActionEvent event) throws IOException {
        GameWindow gameWindow = new GameWindow();
        gameWindow.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    @FXML
    void returnToMenu(ActionEvent event) throws IOException {
        StartWindow startWindow = new StartWindow();
        startWindow.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] cssClasses = {"player1Points", "player2Points", "player3Points"};


        for (int i = 0; i < GameWindow.numberOfPlayers; i++) {
            try {
                VBox bar = initBar();
                ObservableList<String> barCssClasses = bar.lookup("#bar").getStyleClass();
                barCssClasses.add(cssClasses[i]);

                int points = GameWindow.playerPoints[i];

                addPlaceCss(barCssClasses, points);
                addPointsTolabel(bar, points);

                resultsContainer.getChildren().add(bar);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private VBox initBar() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartWindow.class.getResource("/ResultWindow/resultBar.fxml"));
        VBox bar = (VBox) fxmlLoader.load();
        return bar;
    }

    private void addPointsTolabel(VBox bar, int points) {
        Label pointsLabel = (Label) bar.lookup("#pointsLabel");
        pointsLabel.setText("Points: " + points);
    }

    private void addPlaceCss(ObservableList<String> barCssClasses, int points) {
        String[] cssPlaceClasses = {"fristPalce", "secondPlace", "thirdsPlace"};
        int max = Arrays.stream(GameWindow.playerPoints).max().getAsInt();
        int min = Arrays.stream(GameWindow.playerPoints).min().getAsInt();

        if (points == max)
            barCssClasses.add(cssPlaceClasses[0]);
        else if (points == min) {
            barCssClasses.add(cssPlaceClasses[1]);
        } else {
            barCssClasses.add(cssPlaceClasses[2]);
        }
    }

}
