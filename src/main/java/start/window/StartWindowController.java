package start.window;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class StartWindowController {

    @FXML
    private Button exitButton;

    @FXML
    private Button optionsButton;

    @FXML
    private Button startButton;

    @FXML
    private Label titleLabel;

    @FXML
    void exitButtonClicked(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void optionsButtonClicked(ActionEvent event) {

    }

    @FXML
    void startButtonClicked(ActionEvent event) {

    }

}
