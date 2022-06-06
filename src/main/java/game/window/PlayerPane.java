package game.window;


import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PlayerPane extends StackPane {
    public static final int[] charactersID = {0, 1, 2};
    private static final int PENALTY_POINTS = -10;
    public static final String CSS_RIGHT_CLASS = "right";
    public static final String CSS_LEFT_CLASS = "left";
    public static final String CSS_CLASS_BRANCH_RIGHT = "branchRight";
    public static final String CSS_CLASS_BRANCH_LEFT = "branchLeft";
    public static final String CSS_CLASS_BRANCH_EMPTY = "branchEmpty";
    public static final String CSS_CLASS_PLAYER_LEFT = "playerLeft";
    public static final String CSS_CLASS_PLAYER_RIGHT = "playerRight";
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

    public PlayerPane(int id) {
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

    public void initChoppingSound() {
        choppingSound = new MediaPlayer(new Media(GameWindow.class.getResource("/GameWindow/chop_chop.mp3").toExternalForm()));
        choppingSound.setVolume(0.6);
    }

    public void initOuchSound() {
        ouchSound = new MediaPlayer(new Media(GameWindow.class.getResource("/GameWindow/ouch.mp3").toExternalForm()));
        ouchSound.setVolume(0.6);
    }

    public void makeMove(int Side) {
        placeLumberjack(Side);
        lowerBranches();
        checkForCollision();
        addBranch();
    }

    public void placeLumberjack(int side) {
        if (side == currentPlayerSide)
            return;
        currentPlayerSide = side;
        setPlayerSide(side);
    }

    private void setPlayerSide(int side) {
        Pane player = (Pane) this.lookup(".playerPane");
        player.getStyleClass().remove(CSS_LEFT_CLASS);
        player.getStyleClass().remove(CSS_RIGHT_CLASS);
        player.getStyleClass().remove(CSS_CLASS_PLAYER_RIGHT);
        player.getStyleClass().remove(CSS_CLASS_PLAYER_LEFT);

        switch (side) {
            case LEFT_SIDE -> {
                player.getStyleClass().add(CSS_LEFT_CLASS);
                player.getStyleClass().add(CSS_CLASS_PLAYER_LEFT);
            }
            case RIGHT_SIDE -> {
                player.getStyleClass().add(CSS_RIGHT_CLASS);
                player.getStyleClass().add(CSS_CLASS_PLAYER_RIGHT);
            }
        }
    }

    //TODO: Method uses long algorithm, maybe can be changed to be faster
    public void lowerBranches() {
        ArrayList<Pane> branches = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            branches.add((Pane) branchLayer.lookup("#branch_" + i));
        }

        for (int i = 0; i < branches.size() - 1; i++) {
            Pane currentBranch = branches.get(i);
            currentBranch.getStyleClass().clear();

            Pane higherBranch = branches.get(i + 1);
            List<String> styleClass = higherBranch.getStyleClass();
            currentBranch.getStyleClass().addAll(styleClass);
        }
        clearBranch(5);
    }

    void clearBranch(int id) {
        Pane branch = (Pane) branchLayer.lookup("#branch_" + id);
        try {
            branch.getStyleClass().remove(CSS_CLASS_BRANCH_LEFT);
            branch.getStyleClass().remove(CSS_CLASS_BRANCH_RIGHT);
        } finally {
            branch.getStyleClass().add(CSS_CLASS_BRANCH_EMPTY);
        }
    }

    //TODO: make some animation when lumberjack hits branch
    public void checkForCollision() {
        Pane bottomBranch = (Pane) branchLayer.lookup("#branch_0");
        Pane player = (Pane) playerLayer.lookup(".playerPane");

        String bottomBranchSide = getSideCssClass(bottomBranch);
        String playerSide = getSideCssClass(player);

        if (bottomBranchSide.equals(playerSide)) {
            //COLLISION
            //addNegativePointAnimation();
            updatePoints(PENALTY_POINTS);
            chopDownTreeAnimation();
            ouchSound.seek(ouchSound.getStartTime());
            ouchSound.play();
        } else {
            //NO COLLISION
            updatePoints(1);
            chopDownTreeAnimation();
            choppingSound.seek(choppingSound.getStartTime());
            choppingSound.play();
        }
        clearBranch(0);
    }

    private String getSideCssClass(Pane pane) {
        ObservableList<String> CssClasses = pane.getStyleClass();
        if (CssClasses.contains(CSS_LEFT_CLASS) || CssClasses.contains(CSS_CLASS_BRANCH_LEFT))
            return CSS_LEFT_CLASS;
        else if (CssClasses.contains(CSS_RIGHT_CLASS) || CssClasses.contains(CSS_CLASS_BRANCH_RIGHT))
            return CSS_RIGHT_CLASS;
        else return "empty";
    }

    public void updatePoints(int toAdd) {
        points += toAdd;
        if (points < 0) points = 0;
        pointsLabel.setText(Integer.toString(points));
    }


    //BRANCH HAS 50% TO BE ADDED AND THEN 50% TO SPAWN ON EITHER SIDE
    public void addBranch() {
        //DO NOT ADD BRANCH
        if (rollChance(2)) {
            return;
        }

        boolean isRightSide = rollChance(3);
        String side = isRightSide ? CSS_RIGHT_CLASS : CSS_LEFT_CLASS;
        Pane topBranch = (Pane) branchLayer.lookup("#branch_5");

        topBranch.getStyleClass().removeAll(CSS_RIGHT_CLASS, CSS_LEFT_CLASS, CSS_CLASS_BRANCH_RIGHT, CSS_CLASS_BRANCH_LEFT, CSS_CLASS_BRANCH_EMPTY);
        topBranch.getStyleClass().add(side.equals(CSS_RIGHT_CLASS) ? CSS_CLASS_BRANCH_RIGHT : CSS_CLASS_BRANCH_LEFT);
    }

    boolean rollChance(int chance) {
        int check = new Random().nextInt(chance);
        return check != 1;
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


    public void changeTime(int time) {
        int minutes = time / 60;
        int seconds = (time - (time / 60) * 60);
        String output = String.format("%d:%02d", minutes, seconds);
        timeLabel.setText(output);
    }


    public int getPoints() {
        return points;
    }


}
