package start.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import options.window.OptionsWindow;

import java.io.IOException;
import java.util.Objects;

public class StartWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        String css = Objects.requireNonNull(this.getClass().getResource("/StartWindow/style.css")).toExternalForm();
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(css);
        stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(StartWindow.class.getResource("/StartWindow/startWindow.fxml"))));
    }
}