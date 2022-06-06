package game.window;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static javafx.collections.FXCollections.observableArrayList;


public class PlayerPane extends StackPane {
    public static final int[] charactersID = {0, 1, 2};
    private static final int PENALTY_POINTS = -10;
    public static final String CSS_RIGHT_CLASS = "right";
    public static final String CSS_LEFT_CLASS = "left";
    private int currentPlayerSide = 0;
    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    public static final int EMPTY_SIDE = 2;
    private int points;
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
        playerLayer = (GridPane) this.lookup("#playerLayer");
        pointsLabel = (Label) this.lookup("#pointsLabel");
        timeLabel = (Label) this.lookup("#timeLabel");
    }

    //TODO: Method uses long algorithm, maybe can be changed to be faster
    public void lowerBranches() {
        ArrayList<Pane> branches = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            branches.add((Pane) branchLayer.lookup("#branch_" + i));
        }

        //List<String> cssClass =  branches.get(1).getStyleClass();

        for (int i = 0; i < branches.size()-1 ; i++) {
            Pane currentBranch = branches.get(i);
            List<String> tempCss =  observableArrayList(currentBranch.getStyleClass());

            currentBranch.getStyleClass().clear();
            currentBranch.setStyle(null);

            Pane higherBranch = branches.get(i+1);
            List<String> styleClass = higherBranch.getStyleClass();
            currentBranch.getStyleClass().addAll(styleClass);

//            System.out.println(i +":"+
//                    "\n OLD: " + currentBranch.getStyleClass() +
//                    "\n NEW:" + higherBranch.getStyleClass());

        }
        clearBranch(5);


    }

    boolean roll(int chance) {
        int check = new Random().nextInt(chance);
        System.out.println(check);
        return check != 1;
    }

    //BRANCH HAS 50% TO BE ADDED AND THEN 50% TO SPAWN ON EITHER SIDE
    public void addBranch() {
        //DO NOT ADD BRANCH
        if (roll(2)){
            return;
        }

        boolean isRightSide = roll(4);
        String side = isRightSide ? CSS_RIGHT_CLASS : CSS_LEFT_CLASS;
        Pane topBranch = (Pane) branchLayer.lookup("#branch_5");

        topBranch.getStyleClass().removeAll(CSS_RIGHT_CLASS, CSS_LEFT_CLASS, "branchRight", "branchLeft", "branchEmpty");
        topBranch.getStyleClass().add(side.equals(CSS_RIGHT_CLASS) ? "branchRight" : "branchLeft");
    }

    void clearBranch(int id) {
        Pane branch = (Pane) branchLayer.lookup("#branch_"+id);
        try {
            branch.getStyleClass().remove("branchLeft");
            branch.getStyleClass().remove("branchRight");
        } finally {
            branch.getStyleClass().add("branchEmpty");
        }
    }

    //TODO: make some animation when lumberjack hits branch
    public void checkForCollision() {
        Pane bottomBranch = (Pane) branchLayer.lookup("#branch_0");

        //NO COLLISION
        if (bottomBranch.getStyleClass().contains("branchEmpty")) {
            updatePoints(1);
            chopDownTreeAnimation();
            //clearBranch(bottomBranch);
            choppingSound.seek(choppingSound.getStartTime());
            choppingSound.play();
        }
        //COLLISION
        else {
            //todo have to check where the player is :P
            //clearBranch(bottomBranch);
            //todo uncomment animation
            //addNegativePointAnimation();
            updatePoints(PENALTY_POINTS);
            chopDownTreeAnimation();
            ouchSound.seek(ouchSound.getStartTime());
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
        if (side != currentPlayerSide) {
            currentPlayerSide = side;
            Pane player = (Pane) this.lookup("#playerPane");
            setSideCssClass(player, side);
        }
    }

    private void setSideCssClass(Pane pane, int side) {
        switch (side) {
            case LEFT_SIDE -> {
                pane.getStyleClass().remove(CSS_RIGHT_CLASS);
                pane.getStyleClass().add(CSS_LEFT_CLASS);
            }
            case RIGHT_SIDE -> {
                pane.getStyleClass().remove(CSS_LEFT_CLASS);
                pane.getStyleClass().add(CSS_RIGHT_CLASS);
            }
            case EMPTY_SIDE -> {
                pane.getStyleClass().remove(CSS_LEFT_CLASS);
                pane.getStyleClass().remove(CSS_RIGHT_CLASS);
            }
        }
    }

    private String getSideCssClass(Pane pane) {
        ObservableList<String> CssClasses = pane.getStyleClass();
        if (CssClasses.contains(CSS_LEFT_CLASS))
            return CSS_LEFT_CLASS;
        else if (CssClasses.contains(CSS_RIGHT_CLASS))
            return CSS_RIGHT_CLASS;
        else
            return "empty";
    }

    public void changeTime(int time) {
        int minutes = time / 60;
        int seconds = (time - (time / 60) * 60);
        String output = String.format("%d:%02d", minutes, seconds);
        timeLabel.setText(output);
    }

    public void initChoppingSound() {
        choppingSound = new MediaPlayer(new Media(GameWindow.class.getResource("/GameWindow/chop_chop.mp3").toExternalForm()));
        choppingSound.setVolume(0.6);
    }

    public void initOuchSound() {
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

    public void makeMove(int Side) {
        placeLumberjack(Side);
        lowerBranches();
        checkForCollision();
        clearBranch(0);
        addBranch();
    }
}
