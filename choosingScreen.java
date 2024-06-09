package project;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class choosingScreen extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Casino - Spielauswahl");
        stage.setResizable(false);
        stage.setWidth(600);
        stage.setHeight(600);

        // Create buttons
        Button rouletteButton = new Button("Roulette");
        Button blackJackButton = new Button("Blackjack");

        // Create a VBox to hold the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(rouletteButton, blackJackButton);

        // Load background image
        Image bgImage = new Image("file:resources/projekt/background/auswahl.png");
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(600);
        bgImageView.setFitHeight(600);

        // Create a StackPane to hold the background image and the button box
        StackPane root = new StackPane();
        root.getChildren().addAll(bgImageView, buttonBox);

        // Set up the scene and stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Add action listener for blackjack button
        blackJackButton.setOnAction(event -> {
            BlackJack bj = new BlackJack();
            bj.call(stage);
            System.out.println("Blackjack button pressed");
        });
        rouletteButton.setOnAction(event -> {
            Roullette r = new Roullette();
            r.start(stage);
        });

    }
}
