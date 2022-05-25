package game.window;


import javafx.animation.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import options.window.OptionsWindowController;


import java.util.Objects;


public class PlayerPane extends GridPane {

    private static final int PENALTY_POINTS = -10;
    private double width;
    private double height;
    private int columnNumber;
    private int rowNumber;
    private int points;

    private Stage stage;

    private Label timeLabel;
    private Label pointsLabel;

    //TODO: find some png of lumberjack
    private Circle lumberjack;

    public PlayerPane(Stage stage) {
        width = stage.getScene().getWidth() / GameWindow.numberOfPlayers;
        height = stage.getScene().getHeight();
        columnNumber = 3;
        rowNumber = 3;
        this.stage = stage;

        setPrefHeight(height);
        setPrefWidth(width);
        setGridLinesVisible(true); // TEMPORARILY

        //LUMBERJACK INIT
        lumberjack = new Circle(30);
        setHalignment(lumberjack, HPos.CENTER);
        setValignment(lumberjack, VPos.CENTER);
        lumberjack.setFill(Color.VIOLET);


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

    public void changeTime(int time){
        int minutes = time/60;
        int seconds = (time - (time / 60) * 60);
        String output = String.format("%d:%02d", minutes, seconds);
        timeLabel.setText(output);
    }

    public void addTree() {
        double treeWidth = width / 3 - 0.5;
        double treeHeight = height / 3;
        for (int i = 0; i < 3; i++) {
            Rectangle tree = new Rectangle(treeWidth, treeHeight);
            Image img = new Image(Objects.requireNonNull(getClass().getResource("/GameWindow/treeTexture.jpg")).toExternalForm());
            tree.setFill(new ImagePattern(img));
            tree.setStrokeWidth(2.5);
            setHalignment(tree, HPos.CENTER);
            add(tree, 1, i);
        }
    }

    public void addTimeLabel() {
        int minutes = OptionsWindowController.timeValue;
        int seconds = 0;
        String output = String.format("%d:%02d", minutes, seconds);

        timeLabel = new Label(output);
        timeLabel.setPrefWidth(width / 3 - 50);
        timeLabel.setPrefHeight(height / 3 - 200);
        timeLabel.setFont(new Font("Consolas", (int)(100/GameWindow.numberOfPlayers)));
        timeLabel.setAlignment(Pos.CENTER);
        setHalignment(timeLabel,HPos.CENTER);
        setValignment(timeLabel,VPos.TOP);
        timeLabel.getStyleClass().add("timeLabel");
        add(timeLabel, 1, 0);
    }

    public void addPointsLabel(){
        pointsLabel = new Label(Integer.toString(points));
        pointsLabel.setPrefWidth(width / 4 - 30);
        pointsLabel.setPrefHeight(height / 5 - 200);
        pointsLabel.setFont(new Font("Consolas", (int)(75/GameWindow.numberOfPlayers)));
        pointsLabel.setAlignment(Pos.CENTER);
        setHalignment(pointsLabel,HPos.CENTER);
        setValignment(pointsLabel,VPos.BOTTOM);
        pointsLabel.getStyleClass().add("pointsLabel");
        add(pointsLabel,  1,0);
    }

    public void placeLumberjack(int colIndex){
        add(lumberjack, colIndex, 2);
    }

    public void removeLumberjack() {
        for (Node node : getChildren()) {
            if (node instanceof Circle) {
                getChildren().remove(node);
                break;
            }
        }
    }

    //BRANCH HAS 50% TO BE ADDED AND THEN 50% TO SPAWN ON EITHER SIDE
    public void addBranch(){
        //DO NOT ADD BRANCH
        if(Math.floor(Math.random() * 2) % 2 == 1){
            return;
        }
        Branch branch = new Branch(stage);
        int branchColumn = Math.floor(Math.random() * 2) % 2 == 1 ? 0 : 2;
        setValignment(branch, VPos.CENTER);
        setHalignment(branch, branchColumn == 0 ? HPos.RIGHT : HPos.LEFT);
        add(branch, branchColumn, 0);
    }

    //TODO: make some animation when lumberjack hits branch
    public void checkForCollision(){
        Branch branch = null;
        for(Node node : getChildren()){
            if(node instanceof Branch && getRowIndex(node) == 2){
                branch = (Branch)node;
            }
        }
        //COLLISION
        if(branch != null && Objects.equals(getColumnIndex(branch), getColumnIndex(lumberjack))){
            lumberjack.setFill(Color.RED);
            getChildren().remove(branch);
            addNegativePointAnimation();
            updatePoints(PENALTY_POINTS);
            chopDownTreeAnimation();
        }
        //NO COLLISION
        else {
            updatePoints(1);
            chopDownTreeAnimation();
        }
    }

    //TODO: Method uses long algorithm, maybe can be changed to be faster
    public void lowerBranches(){
        Branch[] branches = new Branch[6];
        int[] columns = new int[6];
        int[] rows = new int[6];
        int i = 0;
        //FIND ALL BRANCHES (CAN'T UPDATE CORDS RIGHT AWAY AS IT THROWS AN ERROR)
        for(Node node : getChildren()){
            if(node instanceof Branch){
                branches[i] = (Branch)node;
                columns[i] = getColumnIndex(node);
                rows[i] = getRowIndex(node);
                i++;
            }
        }
        //REMOVE ALL BRANCHES
        getChildren().removeAll(branches);
        //ADD NEW BRANCHES WITH UPDATED CORDS
        for(int j = 0;j<i;j++){
            if(rows[j] < 2){
                Branch branch = new Branch(stage);
                setValignment(branch, VPos.CENTER);
                setHalignment(branch, columns[j] == 0 ? HPos.RIGHT : HPos.LEFT);
                add(branch, columns[j], rows[j] + 1);
            }
        }
    }

    public void updatePoints(int toAdd){
        points += toAdd;
        if(points < 0 )
            points = 0;
        pointsLabel.setText(Integer.toString(points));
    }

    public void addNegativePointAnimation(){
        Text text = new Text(Integer.toString(PENALTY_POINTS));
        setValignment(text, VPos.TOP);
        setHalignment(text, HPos.CENTER);
        text.setFont(new Font("Consolas", (int)(75/GameWindow.numberOfPlayers)));
        text.setFill(Color.valueOf("#FF4E4E"));
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1.5);
        setMargin(text, new Insets(15,0,0,0));
        text.setStyle("-fx-font-weight: bold; -fx-effect: dropshadow(one-pass-box, black, 10, 0.5, 0.0, 0.0);");
        add(text, 1,1);

        //Animation
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(text);
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(e -> getChildren().remove(text));
        fadeTransition.play();
    }

    public int getPoints() {
        return points;
    }

    public void chopDownTreeAnimation(){
        double treeWidth = width / 3 - 0.5;
        double treeHeight = height / 3;
        Rectangle tree = new Rectangle(treeWidth, treeHeight);
        Image img = new Image(Objects.requireNonNull(getClass().getResource("/GameWindow/treeTexture.jpg")).toExternalForm());
        tree.setFill(new ImagePattern(img));
        tree.setStrokeWidth(2.5);
        setHalignment(tree, HPos.CENTER);
        add(tree, 1,2);

        //All animations
        ParallelTransition pt = new ParallelTransition();
        pt.setOnFinished(e -> getChildren().remove(tree));

        //Animation rotate
        RotateTransition rotate = new RotateTransition(Duration.millis(250));
        rotate.setNode(tree);
        rotate.setByAngle(360);
        pt.getChildren().add(rotate);

        //Animation translate
        TranslateTransition translate = new TranslateTransition(Duration.millis(250));
        translate.setNode(tree);
        int colIndex = getColumnIndex(lumberjack);
        if (colIndex == 0) {
            translate.setByX(treeWidth);
        }
        else{
            translate.setByX(-treeWidth);
        }
        translate.setByY(treeHeight);
        pt.getChildren().add(translate);

        //Animation scale
        ScaleTransition scale = new ScaleTransition(Duration.millis(50));
        scale.setNode(tree);
        scale.setByX(-2);
        scale.setByY(-2);
        pt.getChildren().add(scale);
        pt.play();
    }
}
