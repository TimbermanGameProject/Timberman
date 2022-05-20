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
import options.window.OptionsWindowController;


import java.util.Objects;


public class PlayerPane extends GridPane {

    private double width;
    private double height;
    private int columnNumber;
    private int rowNumber;
    private int points;

    private Stage stage;

    private Label timeLabel;
    private Label pointsLabel;

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
        if(GameWindow.numberOfPlayers == 1) {
            setValignment(pointsLabel,VPos.BOTTOM);
        }
        else{
            setValignment(pointsLabel,VPos.CENTER);
        }
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

    public void checkForCollision(){
        Branch branch = null;
        for(Node node : getChildren()){
            if(node instanceof Branch && getRowIndex(node) == 2){
                branch = (Branch)node;
            }
        }
        if(branch != null && Objects.equals(getColumnIndex(branch), getColumnIndex(lumberjack))){
            lumberjack.setFill(Color.RED);
            getChildren().remove(branch);
            updatePoints(-1);
        }
        else {
            updatePoints(1);
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
        pointsLabel.setText(Integer.toString(points));
    }
}
