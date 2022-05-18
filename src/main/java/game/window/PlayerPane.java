package game.window;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class PlayerPane extends GridPane {

    private double width;
    private double height;
    private int columnNumber;
    private int rowNumber;
    private int points;
    private Label timeLabel;
    private Label pointsLabel;

    public PlayerPane(Stage stage){
        width = stage.getScene().getWidth() / GameWindow.numberOfPlayers;
        height = stage.getScene().getHeight();
        columnNumber = 3;
        rowNumber = 3;
        setPrefHeight(height);
        setPrefWidth(width);
        setGridLinesVisible(true); // TEMPORARILY

        for (int i = 0; i < columnNumber; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / columnNumber);
            getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < rowNumber; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / rowNumber);
            getRowConstraints().add(rowConst);
        }
        addTree();
        addTimeLabel();
        addPointsLabel();
        placeLumberjack(0);
    }

    public void addTree(){
        double treeWidth = width / 3 - 0.5;
        double treeHeight = height / 3;
        for(int i = 0;i<3;i++){
            Rectangle tree = new Rectangle(treeWidth,treeHeight);
            Image img = new Image(Objects.requireNonNull(getClass().getResource("/GameWindow/treeTexture.jpg")).toExternalForm());
            tree.setFill(new ImagePattern(img));
            tree.setStrokeWidth(2.5);
            setHalignment(tree, HPos.CENTER);
            add(tree,1,i);
        }
    }

    public void addTimeLabel(){
        timeLabel = new Label("0:00");
        timeLabel.setPrefWidth(width / 3 - 50);
        timeLabel.setPrefHeight(height / 3 - 200);
        timeLabel.setFont(new Font("Consolas", (int)(100/GameWindow.numberOfPlayers)));
        timeLabel.setAlignment(Pos.CENTER);
        setHalignment(timeLabel,HPos.CENTER);
        setValignment(timeLabel,VPos.CENTER);
        timeLabel.getStyleClass().add("timeLabel");
        add(timeLabel, 1,0);
    }

    public void addPointsLabel(){
        pointsLabel = new Label(Integer.toString(points));
        pointsLabel.setPrefWidth(width / 4 - 50);
        pointsLabel.setPrefHeight(height / 5 - 200);
        pointsLabel.setFont(new Font("Consolas", (int)(75/GameWindow.numberOfPlayers)));
        pointsLabel.setAlignment(Pos.CENTER);
        setHalignment(pointsLabel,HPos.CENTER);
        setValignment(pointsLabel,VPos.BOTTOM);
        pointsLabel.getStyleClass().add("pointsLabel");
        add(pointsLabel,  1,0);
    }

    public void placeLumberjack(int colIndex){
        Circle circle = new Circle(30);
        setHalignment(circle,HPos.CENTER);
        setValignment(circle,VPos.CENTER);
        circle.setFill(Color.VIOLET);
        add(circle,colIndex,2);
    }

    public void removeLumberjack(){
        for(Node node : getChildren()) {
            if(node instanceof Circle) {
                getChildren().remove(node);
                break;
            }
        }
    }
}
