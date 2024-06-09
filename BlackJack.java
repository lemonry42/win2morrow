package project;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.css.RGBColor;

import javax.swing.*;

public class BlackJack extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void call(Stage stage) {
        stage.setTitle("BlackJack Tisch");
        stage.setWidth(1720);
        stage.setHeight(880);
        stage.setResizable(false);

        // Background image
        Image bgImage = new Image("file:resources/projekt/background/blackjack.png");
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(1720);
        bgImageView.setFitHeight(880);



        // Create buttons
        Button start = new Button("start");
        Button take = new Button("take");
        Button stand = new Button("stand");


        HBox buttons = new HBox(20);
        buttons.getChildren().addAll(start, take, stand);
        buttons.setPadding(new Insets(50));
        buttons.setAlignment(Pos.BOTTOM_CENTER);



        VBox layout = new VBox();
        layout.getChildren().addAll(bgImageView, buttons);

        StackPane root = new StackPane();
        root.getChildren().add(layout);
        root.getChildren().add(buttons);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Player a = new Player(1000, "player");
        Player b = new Player(0,  "bank");
        final int[] x1 = {-60, 150};
        final int[] x2 = {-60, -200};
        start.setOnAction(e -> {
            try {
                play(a, b, stage, root, scene);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            if(a.calculateBestValue() == 21) {
                checkForWin(a.calculateBestValue(), b.calculateBestValue(), stage);
            }
            start.setDisable(true);
        });
        take.setOnAction(e -> {
            take(a, x1[0],  x1[1],root,stage,scene);
            x1[0] -= 30;
            checkForLoose(a.calculateBestValue(), stage);
            checkForBlackJack(a.calculateBestValue(), stage);
        });
        stand.setOnAction(e -> {
            while (b.getBjCardValues() < 16) {
                take(b, x2[0], x2[1],root,stage,scene);
                x2[0] -= 30;
                System.out.println(b.getBjCardValues() + " Bank");
            }
            checkForWin(a.calculateBestValue(), b.calculateBestValue(), stage);
        });

    }

    public void play(Player player, Player bank, Stage stage, StackPane sp, Scene scene) throws InterruptedException {
        int a = ((int) (Math.random() * 52)) + 1;
        Card firstStartCard = new Card(a);
        Image card1 = new Image("file:resources/projekt/karten/" + a + ".jpg");
        ImageView ivCard1 = new ImageView(card1);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), ivCard1);
        transition.setFromY(-540);
        transition.setToY(150);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
        sp.getChildren().add(ivCard1);
        stage.show();
        a = ((int) (Math.random() * 52)) + 1;
        Card secondStartCard = new Card(a);
        Image card2 = new Image("file:resources/projekt/karten/" + a + ".jpg");
        ImageView ivCard2 = new ImageView(card2);
        transition = new TranslateTransition(Duration.seconds(2), ivCard2);
        transition.setFromY(-1000);
        transition.setToY(150);
        transition.setByX(-30);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
        sp.getChildren().add(ivCard2);
        stage.show();
        a = ((int) (Math.random() * 52)) + 1;
        Card BankFirstStartCard = new Card(a);
        Image card3 = new Image("file:resources/projekt/karten/" + a + ".jpg");
        ImageView ivCard3 = new ImageView(card3);
        transition = new TranslateTransition(Duration.seconds(2), ivCard3);
        transition.setFromY(-1000);
        transition.setToY(-200);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
        sp.getChildren().add(ivCard3);
        stage.show();
        a = ((int) (Math.random() * 52)) + 1;
        Card BankSecondStartCard = new Card(a);
        Image card4 = new Image("file:resources/projekt/karten/" + a + ".jpg");
        ImageView ivCard4 = new ImageView(card4);
        transition = new TranslateTransition(Duration.seconds(2), ivCard4);
        transition.setFromY(-1000);
        transition.setToY(-200);
        transition.setByX(-30);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
        sp.getChildren().add(ivCard4);
        stage.show();
        player.addCard(firstStartCard);
        player.addCard(secondStartCard);
        bank.addCard(BankFirstStartCard);
        bank.addCard(BankSecondStartCard);
    }

    public void take(Player player, int x, int y, StackPane sp, Stage stage, Scene scene) {
        int a = ((int) (Math.random() * 52)) + 1;
        Card card = new Card(a);
        player.addCard(card);
        Image card1 = new Image("file:resources/projekt/karten/" + a + ".jpg");
        ImageView ivCard1 = new ImageView(card1);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), ivCard1);
        transition.setByX(x);
        transition.setFromY(-540);
        transition.setToY(y);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);
        transition.play();
        sp.getChildren().add(ivCard1);
        scene.setRoot(sp);
        stage.setScene(scene);
        stage.show();
    }

    public void checkForWin(int cardValuePlayer, int cardValueBank, Stage stage) {
        Stage popup = new Stage();
        popup.setHeight(100);
        popup.setWidth(200);
        Label a = new Label();
        a.setAlignment(Pos.CENTER);
        Pane p = new Pane();
        Scene s = new Scene(p);
        popup.setScene(s);
        popup.initModality(Modality.APPLICATION_MODAL);
        if (cardValuePlayer == 21) {
            a.setText("Blackjack");
        } else if (cardValuePlayer > cardValueBank && cardValuePlayer < 21) {
            a.setText("Gewonnen");
        } else if (cardValuePlayer == cardValueBank) {
            a.setText("Standoff");
        } else if (cardValueBank > 21 && cardValuePlayer <= 21) {
            a.setText("Gewonnen");
        } else {
            a.setText("Verloren");
        }
        p.getChildren().add(a);
        popup.showAndWait();
        call(stage);
    }
    public void checkForBlackJack(int cardValuePlayer, Stage stage){
        if(cardValuePlayer == 21){
            Stage popup = new Stage();
            popup.setHeight(100);
            popup.setWidth(200);
            Label a = new Label();
            a.setAlignment(Pos.CENTER);
            Pane p = new Pane();
            Scene s = new Scene(p);
            popup.setScene(s);
            popup.initModality(Modality.APPLICATION_MODAL);
            a.setText("Blackjack");
            p.getChildren().add(a);
            popup.showAndWait();
            call(stage);
        }
    }

    public void checkForLoose(int cardValuePlayer, Stage stage){
        if(cardValuePlayer > 21){
            Stage popup = new Stage();
            popup.setHeight(100);
            popup.setWidth(200);
            Label a = new Label();
            a.setAlignment(Pos.CENTER);
            Pane p = new Pane();
            Scene s = new Scene(p);
            popup.setScene(s);
            popup.initModality(Modality.APPLICATION_MODAL);
            a.setText("Verloren");
            p.getChildren().add(a);
            popup.showAndWait();
            call(stage);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        call(stage);
    }
}
