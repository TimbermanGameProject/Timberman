package options.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import start.window.StartWindow;

import java.io.IOException;

public class OptionsWindowController {

    @FXML
    private CheckBox musicCheckbox;

    @FXML
    private TextField playersNumberBox;

    @FXML
    private Button returnButton;

    @FXML
    private TextField roundTimeBox;

    @FXML
    void returnToStartWindow(ActionEvent event) throws IOException {
        StartWindow startWindow = new StartWindow();
        startWindow.start((Stage)((Node)event.getSource()).getScene().getWindow());
    }

}