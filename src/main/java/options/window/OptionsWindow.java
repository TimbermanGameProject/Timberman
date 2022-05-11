package options.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import options.window.OptionsWindowController;

import java.io.IOException;
import java.util.Objects;

public class OptionsWindow extends Application {
//    public static void setCheckboxValue(boolean checkboxValue) {
//        OptionsWindow.checkboxValue = checkboxValue;
//    }
//
//    public static void setPlayerValue(int playerValue) {
//        OptionsWindow.playerValue = playerValue;
//    }
//
//    public static void setTimeValue(int timeValue) {
//        OptionsWindow.timeValue = timeValue;
//    }

//    static boolean checkboxValue;
//    static int playerValue = 1;
//    static int timeValue = 1;


    @Override
    public void start(Stage stage) throws IOException {
        //### TU NIE PODMIENIAMY SCENY TYLKO ROOTA I OD RAZU MAMY FULLSCREEN ###


        String css = Objects.requireNonNull(this.getClass().getResource("/OptionsWindow/style.css")).toExternalForm();
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(css);
        stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(OptionsWindow.class.getResource("/OptionsWindow/optionsWindow.fxml"))));

        /*
        ### OPCJA BARDZIEJ CZYTELNA ALE NIE DAJE FULLSCREENA OD RAZU :( ###

        FXMLLoader fxmlLoader = new FXMLLoader(OptionsWindow.class.getResource("/OptionsWindow/optionsWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        */
    }


}
