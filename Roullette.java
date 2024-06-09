package project;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Map;

public class Roullette extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("BlackJack Tisch");
        stage.setWidth(1720);
        stage.setHeight(880);
        stage.setResizable(false);

        // Background image
        Image bgImage = new Image("file:resources/projekt/background/roullette.png");
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(1720);
        bgImageView.setFitHeight(880);

        Image outerWheel = new Image("file:resources/projekt/background/scheibe-aussen.png");
        ImageView oWhView = new ImageView(outerWheel);
        oWhView.setFitHeight(600);
        oWhView.setFitWidth(600);

        Image innerWheel = new Image("file:resources/projekt/background/scheibe-innen.png");
        ImageView iWhView = new ImageView(innerWheel);
        iWhView.setFitHeight(600);
        iWhView.setFitWidth(600);
        iWhView.setRotate(-2);

        TranslateTransition transitionOut = new TranslateTransition(Duration.seconds(1), oWhView);
        transitionOut.setFromX(-480);
        transitionOut.setToX(-480);
        transitionOut.setCycleCount(1);
        transitionOut.setAutoReverse(false);
        transitionOut.play();

        TranslateTransition transitionIn = new TranslateTransition(Duration.seconds(2), iWhView);
        transitionIn.setFromX(-480);
        transitionIn.setToX(-480);
        transitionIn.setCycleCount(1);
        transitionIn.setAutoReverse(false);
        transitionIn.play();

        RotateTransition rotate = new RotateTransition(Duration.seconds(5), iWhView);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setFromAngle(0);
        rotate.setToAngle(360);
        rotate.setAutoReverse(false);
        rotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotate.setRate(1);
        rotate.play();



        // Create buttons
        Button start = new Button("start");

        HBox buttons = new HBox(20);
        buttons.getChildren().addAll(start);
        buttons.setPadding(new Insets(50));
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        VBox layout = new VBox();
        layout.getChildren().addAll(bgImageView);

        StackPane root = new StackPane();
        root.getChildren().add(layout);
        root.getChildren().add(rotate.getNode());
        root.getChildren().add(transitionOut.getNode());
        root.getChildren().add(buttons);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        start.setOnAction(event -> {
            Image ball =  new Image("file:resources/projekt/background/ball.png");
            ImageView ballView = new ImageView(ball);
            ballView.setFitWidth(600);
            ballView.setFitHeight(600);

            TranslateTransition ballTrans = new TranslateTransition(Duration.seconds(2), ballView);
            ballTrans.setFromX(-480);
            ballTrans.setToX(-480);
            ballTrans.setCycleCount(1);
            ballTrans.setAutoReverse(false);
            ballTrans.play();

            RotateTransition bRotate = new RotateTransition(Duration.seconds(2), ballView);
            bRotate.setCycleCount(RotateTransition.INDEFINITE);
            bRotate.setFromAngle(0);
            bRotate.setToAngle(360);
            bRotate.setAutoReverse(false);
            bRotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
            bRotate.setRate(1);
            bRotate.play();
            root.getChildren().add(ballTrans.getNode());
            start.setDisable(true);
        });
    }


}
