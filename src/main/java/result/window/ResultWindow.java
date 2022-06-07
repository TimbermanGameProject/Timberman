package result.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ResultWindow extends Application {

    private final String pathToFXML;

    public ResultWindow(final int number){
        this.pathToFXML = "/ResultWindow/ResultWindow.fxml";
    }

    @Override
    public void start(Stage stage) throws IOException {
        String css = Objects.requireNonNull(this.getClass().getResource("/ResultWindow/style.css")).toExternalForm();
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(css);
        stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(ResultWindow.class.getResource(pathToFXML))));
    }
}
