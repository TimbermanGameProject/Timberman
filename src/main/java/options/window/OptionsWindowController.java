package options.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import start.window.StartWindow;

import java.io.IOException;

public class OptionsWindowController {

    @FXML
    private Button returnButton;

    @FXML
    void returnToStartWindow(ActionEvent event) throws IOException {
        StartWindow startWindow = new StartWindow();
        startWindow.start((Stage)((Node)event.getSource()).getScene().getWindow());
    }

}