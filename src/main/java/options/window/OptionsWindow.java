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

    @Override
    public void start(Stage stage) throws IOException {
        //### TU NIE PODMIENIAMY SCENY TYLKO ROOTA I OD RAZU MAMY FULLSCREEN ###
        String css = Objects.requireNonNull(this.getClass().getResource("/OptionsWindow/style.css")).toExternalForm();
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(css);
        stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(OptionsWindow.class.getResource("/OptionsWindow/optionsWindow.fxml"))));

    }
}
