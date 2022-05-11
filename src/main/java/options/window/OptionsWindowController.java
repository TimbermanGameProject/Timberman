package options.window;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import start.window.StartWindow;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsWindowController implements Initializable{
    static boolean checkboxValue = false;
    static int playerValue = 1;
    static int timeValue = 1;

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
        checkboxValue = musicCheckbox.isSelected();
        int playerValueTemp;
        int timeValueTemp;
        try {
            playerValueTemp = Integer.parseInt(playersNumberBox.getCharacters().toString());
        } catch (Exception e ){
            playerValueTemp = 1;
        }
        try {
            timeValueTemp = Integer.parseInt(roundTimeBox.getCharacters().toString());
        } catch (Exception e){
            timeValueTemp = 1;
        }

        if(playerValueTemp < 1 || playerValueTemp > 3) playerValueTemp = 1;
        if(timeValueTemp < 1 || timeValueTemp > 10) timeValueTemp = 1;

        playerValue = playerValueTemp;
        timeValue = timeValueTemp;

        System.out.println(checkboxValue + "\n" + playerValue + "\n" + timeValue);

        StartWindow startWindow = new StartWindow();
        startWindow.start((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    public void setStaticValues(){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        musicCheckbox.setSelected(checkboxValue);
        playersNumberBox.setText(Integer.toString(playerValue));
        roundTimeBox.setText(Integer.toString(timeValue));
    }
}