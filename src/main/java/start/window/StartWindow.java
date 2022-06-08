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


public class StartWindow extends Application {
    private static final ArrayList<ImageView> backgroundImgs = new ArrayList<>();
    private void addResizeListeners(Stage stage) {
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            setWidthOnPhotos(newVal);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            setHeightOnPhotos(newVal);
        });
    }

    private void setHeightOnPhotos(Number newVal) {
        for (ImageView photo : backgroundImgs)
            photo.fitHeightProperty().setValue(newVal);
    }

    private void setWidthOnPhotos(Number newVal) {
        for (ImageView photo : backgroundImgs)
            photo.fitWidthProperty().setValue(newVal);
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
        String css = Objects.requireNonNull(this.getClass().getResource("/StartWindow/style.css")).toExternalForm();
        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add(css);
        stage.getScene().setRoot(FXMLLoader.load(Objects.requireNonNull(StartWindow.class.getResource("/StartWindow/startWindow.fxml"))));

        fillBackgroundImgArray(stage.getScene());
        setHeightOnPhotos(stage.getScene().getHeight());
        setWidthOnPhotos(stage.getScene().getWidth());
        addResizeListeners(stage);
    }
}