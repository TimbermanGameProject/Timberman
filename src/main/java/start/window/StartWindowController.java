package start.window;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import options.window.OptionsWindow;

import java.io.IOException;


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
    void optionsButtonClicked(ActionEvent event) throws IOException{
        OptionsWindow optionsWindow = new OptionsWindow();
        optionsWindow.start((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    void startButtonClicked(ActionEvent event)  {
    }

}
