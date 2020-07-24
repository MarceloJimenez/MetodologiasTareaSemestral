package com.github.cc3002.citricliquid.gui;

import com.github.cc3002.citricliquid.gui.nodes.MovableNodeBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Ignacio Slater Muñoz.
 * @version 1.0.6-rc.1
 * @since 1.0
 */
public class CitricLiquid extends Application {
  private static final String RESOURCE_PATH = "src/main/resources/";

  @Override
  public void start(@NotNull Stage stage) throws FileNotFoundException {
    stage.setTitle("99.7% Citric Liquid");
    Group root = new Group();
    int width = 1280;
    int height = 720;
    Scene scene = new Scene(root, width, height);
    var sprite = new MovableNodeBuilder(scene).setImagePath(RESOURCE_PATH + "sprite.png")
                                              .setPosition(100, 100)
                                              .setSize(50, 50)
                                              .build();
    var background =
        new ImageView(new Image(new FileInputStream(RESOURCE_PATH + "background.jpg")));
    root.getChildren().add(background);
    root.getChildren().add(setupButton());
    root.getChildren().add(sprite.getNode());
    stage.setScene(scene);
    stage.show();
  }

  private @NotNull Button setupButton() {
    Button button = new Button("Play sound");
    button.setLayoutX(950);
    button.setLayoutY(100);
    button.setFocusTraversable(false);
    button.setOnAction(CitricLiquid::playSound);
    return button;
  }

  private static void playSound(ActionEvent event) {
    String audioFilePath = RESOURCE_PATH + "prfvr.wav";
    try {
      Clip sound = AudioSystem.getClip();
      try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
          new File(audioFilePath))) {
        sound.open(audioInputStream);
        sound.start();
      }
    } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
      e.printStackTrace();
    }
  }
}
