package com.github.cc3002.citricliquid.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AlertBox {
    private static final String RESOURCE_PATH = "src/main/java/com/github/cc3002/citricliquid/gui/resources/";
    private static Image lemonBackground;


    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);
        window.setMinHeight(100);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());


        try {
            lemonBackground = new Image(new FileInputStream(RESOURCE_PATH + "lemonBackground.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BackgroundImage backgroundimage = new BackgroundImage(lemonBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundimage);


        VBox layout = new VBox(10);
        layout.setBackground(background);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
