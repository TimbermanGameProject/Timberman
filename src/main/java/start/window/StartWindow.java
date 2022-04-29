package start.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartWindow extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartWindow.class.getResource("startWindow.fxml"));
        String css = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
        stage.setFullScreen(true);
        stage.setMaximized(true);

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css);

        stage.setTitle("Timberman");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}