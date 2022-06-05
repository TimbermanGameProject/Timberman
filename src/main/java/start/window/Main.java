package start.window;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

// odpalamy tylko raz zeby dodac scene, potem uzywamy klasy StartWindow zeby edytowac roota i tym samym
// zachowac fullscreen

public class Main extends Application {

    private static final ArrayList<ImageView> backgroundImgs = new ArrayList<>();
    private void addResizeListeners(Stage stage) {
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            for (ImageView photo : backgroundImgs)
                photo.fitWidthProperty().setValue(newVal);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            for (ImageView photo : backgroundImgs)
                photo.fitHeightProperty().setValue(newVal);
        });
    }

    private StackPane fillBackgroundImgArray(Scene scene) {
        StackPane backgroundContainer = (StackPane) scene.lookup("#backgroundContainer");
        ObservableList<Node> imgs = backgroundContainer.getChildren();
        for (Node img : imgs) {
            ImageView photo = (ImageView) img;
            photo.setPreserveRatio(false);
            backgroundImgs.add(photo);
        }
        return backgroundContainer;
    }

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(StartWindow.class.getResource("/StartWindow/startWindow.fxml"));
        String css = Objects.requireNonNull(this.getClass().getResource("/StartWindow/style.css")).toExternalForm();
        stage.setFullScreen(true);
        stage.setMaximized(true);

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css);

        fillBackgroundImgArray(scene);
        addResizeListeners(stage);
        stage.setFullScreen(true);
        stage.setMaximized(true);

        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}