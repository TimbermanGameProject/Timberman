package game.window;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Objects;

public class Branch extends Rectangle {

    public Branch(Stage stage){
        setWidth(stage.getScene().getWidth() / (GameWindow.numberOfPlayers * 3) - 10);
        setHeight(stage.getScene().getHeight() / 18);
        Image img = new Image(Objects.requireNonNull(getClass().getResource("/GameWindow/treeTexture.jpg")).toExternalForm());
        setFill(new ImagePattern(img));
    }
}
