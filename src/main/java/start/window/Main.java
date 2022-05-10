package start.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import options.window.OptionsWindow;

import java.io.IOException;
import java.util.Objects;

// odpalamy tylko raz zeby dodac scene, potem uzywamy klasy StartWindow zeby edytowac roota i tym samym
// zachowac fullscreen

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartWindow.class.getResource("/StartWindow/startWindow.fxml"));
        String css = Objects.requireNonNull(this.getClass().getResource("/StartWindow/style.css")).toExternalForm();
        stage.setFullScreen(true);
        stage.setMaximized(true);

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}