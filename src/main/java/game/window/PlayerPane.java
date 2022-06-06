package game.window;


import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Random;


public class PlayerPane extends StackPane {
    public static final int[] charactersID = {0, 1, 2};
    private static final int PENALTY_POINTS = -10;
    private int currentSide = 0;
    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    public static final int EMPTY_SIDE = 2;

    private double width;
    private double height;
    private int columnNumber;
    private int rowNumber;
    private int points;

    private Stage stage;

    private Label timeLabel;
    private Label pointsLabel;

    private MediaPlayer choppingSound;
    private MediaPlayer ouchSound;

    private ImageView lumberjack;

    private GridPane trunkLayer;
    private GridPane branchLayer;
    private GridPane playerLayer;

    public PlayerPane(Stage stage, int id) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameWindow/PlayerPane/GamePanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //CHOPPING SOUND INIT
        initChoppingSound();

        //OUCH SOUND INIT
        initOuchSound();
        branchLayer = (GridPane) this.lookup("#branchLayer");
        pointsLabel = (Label) this.lookup("#pointsLabel");
        timeLabel = (Label) this.lookup("#timeLabel");
    }

    //TODO: Method uses long algorithm, maybe can be changed to be faster
    public void lowerBranches() {
    }

    boolean roll(int chance) {
        return new Random().nextInt(chance) == 0;
    }

    //BRANCH HAS 50% TO BE ADDED AND THEN 50% TO SPAWN ON EITHER SIDE
    public void addBranch() {
        //DO NOT ADD BRANCH
        if (roll(4))
            return;

        boolean isRightSide = roll(2);
        String side = isRightSide ? "right" : "left";
        int size = roll(2) ? 1 : 0;

        String imgPath = "/GameWindow/PlayerPane/img/tree/" + side + "_" + size + ".png";
        String css = "-fx-background-image: url(" + imgPath + ")";

        Pane topBranch = (Pane) branchLayer.lookup("#branch_5");
        topBranch.setStyle(css);
    }

    void clearBranch(Pane branch) {
        try {
            branch.getStyleClass().remove("branchLeftBig");
            branch.getStyleClass().remove("branchLeftSmall");
            branch.getStyleClass().remove("branchRightBig");
            branch.getStyleClass().remove("branchRightSmall");
        } finally {
            branch.getStyleClass().add("branchEmpty");
        }
    }

    //TODO: make some animation when lumberjack hits branch
    public void checkForCollision() {
        Pane bottomBranch = (Pane) branchLayer.lookup("#branch_1");

        //NO COLLISION
        if (bottomBranch.getStyleClass().contains("branchEmpty")) {
            updatePoints(1);
            chopDownTreeAnimation();
            choppingSound.seek(choppingSound.getStartTime());
            choppingSound.play();
        }
        //COLLISION
        else {
            //todo have to check where the player is :P
            clearBranch(bottomBranch);
            //todo uncomment animation
            //addNegativePointAnimation();
            updatePoints(PENALTY_POINTS);
            chopDownTreeAnimation();
            ouchSound.seek(choppingSound.getStartTime());
            ouchSound.play();
        }


    }

    public void chopDownTreeAnimation() {
//        double treeWidth = width / 3 - 0.5;
//        double treeHeight = height / 3;
//        Rectangle tree = new Rectangle(treeWidth, treeHeight);
//        Image img = new Image(Objects.requireNonNull(getClass().getResource("/GameWindow/treeTexture.jpg")).toExternalForm());
//        tree.setFill(new ImagePattern(img));
//        tree.setStrokeWidth(2.5);
//        setHalignment(tree, HPos.CENTER);
//        add(tree, 1, 2);
//
//        //All animations
//        ParallelTransition pt = new ParallelTransition();
//        pt.setOnFinished(e -> getChildren().remove(tree));
//
//        //Animation rotate
//        RotateTransition rotate = new RotateTransition(Duration.millis(250));
//        rotate.setNode(tree);
//        rotate.setByAngle(360);
//        pt.getChildren().add(rotate);
//
//        //Animation translate
//        TranslateTransition translate = new TranslateTransition(Duration.millis(250));
//        translate.setNode(tree);
//        int colIndex = getColumnIndex(lumberjack);
//        translate.setByX(colIndex == 0 ? treeWidth : -treeWidth);
//        translate.setByY(treeHeight);
//        pt.getChildren().add(translate);
//
//        //Animation scale
//        ScaleTransition scale = new ScaleTransition(Duration.millis(100));
//        scale.setNode(tree);
//        scale.setFromX(1);
//        scale.setFromY(1);
//        scale.setToX(0.7);
//        scale.setToY(0.7);
//        pt.getChildren().add(scale);
//
//        //Play all animations
//        pt.play();
    }

    public void placeLumberjack(int side) {
        if (side != currentSide) {
            currentSide = side;
            Pane player = (Pane) this.lookup("#playerPane");
            setSideCssClass(player, side);
        }
    }

    private void setSideCssClass(Pane pane, int side) {
        switch(side){
            case LEFT_SIDE -> {
                pane.getStyleClass().remove("right");
                pane.getStyleClass().add("left");
            }
            case RIGHT_SIDE -> {
                pane.getStyleClass().remove("left");
                pane.getStyleClass().add("right");
            }
            case EMPTY_SIDE -> {
                pane.getStyleClass().remove("left");
                pane.getStyleClass().remove("right");
            }
        }
    }
    private String getSideCssClass(Pane pane){
        ObservableList<String> CssClasses = pane.getStyleClass();
        if(CssClasses.contains("left"))
            return "left";
        else if (CssClasses.contains("left"))
            return "right";
        else
            return "empty";
    }

    public void changeTime(int time) {
        int minutes = time / 60;
        int seconds = (time - (time / 60) * 60);
        String output = String.format("%d:%02d", minutes, seconds);
        timeLabel.setText(output);
    }
    public void initChoppingSound(){
        choppingSound = new MediaPlayer(new Media(GameWindow.class.getResource("/GameWindow/chop_chop.mp3").toExternalForm()));
        choppingSound.setVolume(0.6);
    }

    public void initOuchSound(){
        ouchSound = new MediaPlayer(new Media(GameWindow.class.getResource("/GameWindow/ouch.mp3").toExternalForm()));
        ouchSound.setVolume(0.6);
    }
    public int getPoints() {
        return points;
    }

    public void updatePoints(int toAdd) {
        points += toAdd;
        if (points < 0)
            points = 0;
        pointsLabel.setText(Integer.toString(points));
    }
}
