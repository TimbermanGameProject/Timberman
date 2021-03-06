package game.window;


import javafx.animation.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PlayerPane extends StackPane {
    private static final int PENALTY_POINTS = -25;
    public static final String CSS_RIGHT_CLASS = "right";
    private static final String CSS_LEFT_CLASS = "left";
    private static final String CSS_CLASS_BRANCH_RIGHT = "branchRight";
    private static final String CSS_CLASS_BRANCH_LEFT = "branchLeft";
    private static final String CSS_CLASS_BRANCH_EMPTY = "branchEmpty";
    public  String CSS_CLASS_PLAYER_LEFT = "playerLeft";
    public  String CSS_CLASS_PLAYER_RIGHT = "playerRight";
    private int currentPlayerSide = 0;
    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    private int points;
    private Label timeLabel;
    private Label pointsLabel;
    private MediaPlayer choppingSound;
    private MediaPlayer ouchSound;
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
        trunkLayer = (GridPane) this.lookup("#trunkLayer");
        playerLayer = (GridPane) this.lookup("#playerLayer");
        pointsLabel = (Label) this.lookup("#pointsLabel");
        timeLabel = (Label) this.lookup("#timeLabel");

        CSS_CLASS_PLAYER_RIGHT += id;
        CSS_CLASS_PLAYER_LEFT += id;

        setPlayerSide(LEFT_SIDE);
    }

    public void initChoppingSound() {
        choppingSound = new MediaPlayer(new Media(GameWindow.class.getResource("/GameWindow/sounds/chop_chop.mp3").toExternalForm()));
        choppingSound.setVolume(0.3);
    }

    public void initOuchSound() {
        ouchSound = new MediaPlayer(new Media(GameWindow.class.getResource("/GameWindow/sounds/ouch2.mp3").toExternalForm()));
        ouchSound.setVolume(0.45);
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

    public void checkForCollision() {
        Pane bottomBranch = (Pane) branchLayer.lookup("#branch_0");
        Pane player = (Pane) playerLayer.lookup(".playerPane");

        String bottomBranchSide = getSideCssClass(bottomBranch);
        String playerSide = getSideCssClass(player);

        if (bottomBranchSide.equals(playerSide)) {
            //COLLISION
            addNegativePointAnimation();
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

    private void addNegativePointAnimation(){
        Text text = new Text(Integer.toString(PENALTY_POINTS));
        trunkLayer.setValignment(text, VPos.TOP);
        trunkLayer.setHalignment(text, HPos.CENTER);
        switch (GameWindow.numberOfPlayers){
            case 1 -> {
                text.setFont(new Font("Consolas", 60));
            }
            case 2 -> {
                text.setFont(new Font("Consolas", 50));
            }
            case 3 -> {
                text.setFont(new Font("Consolas", 45));
            }
        }
        text.setFill(Color.valueOf("#FF4E4E"));
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(1.5);
        setMargin(text, new Insets(15, 0, 0, 0));
        text.setStyle("-fx-font-weight: bold; -fx-effect: dropshadow(one-pass-box, black, 10, 0.5, 0.0, 0.0);");
        trunkLayer.add(text, 0, 1);

        //Animation
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(text);
        fadeTransition.setDuration(Duration.millis(1500));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setOnFinished(e -> trunkLayer.getChildren().remove(text));
        fadeTransition.play();
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
        Pane tree = new Pane();
        tree.getStyleClass().add("floorAnim");
        GridPane.setHalignment(tree, HPos.CENTER);
        trunkLayer.add(tree, 0, 5);

        //All animations
        ParallelTransition pt = new ParallelTransition();
        pt.setOnFinished(e -> trunkLayer.getChildren().remove(tree));

        //Animation rotate
        RotateTransition rotate = new RotateTransition(Duration.millis(250));
        rotate.setNode(tree);
        rotate.setByAngle(360);
        pt.getChildren().add(rotate);

        //Animation translate
        TranslateTransition translate = new TranslateTransition(Duration.millis(250));
        translate.setNode(tree);
        translate.setByX(currentPlayerSide == LEFT_SIDE ? 150 : -150);
        translate.setByY(150);
        pt.getChildren().add(translate);

        //Animation scale
        ScaleTransition scale = new ScaleTransition(Duration.millis(100));
        scale.setNode(tree);
        scale.setFromX(1);
        scale.setFromY(1);
        scale.setToX(0.7);
        scale.setToY(0.7);
        pt.getChildren().add(scale);

        //Play all animations
        pt.play();
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
