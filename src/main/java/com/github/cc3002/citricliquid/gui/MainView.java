package com.github.cc3002.citricliquid.gui;

import com.github.cc3002.citricliquid.controller.BoardBuilder;
import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.gui.nodes.MovableNode;
import com.github.cc3002.citricliquid.gui.nodes.MovableNodeBuilder;
import com.github.cc3002.citricliquid.model.unit.Player;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * @author Ignacio Slater Muñoz.
 * @version 1.0.6-rc.1
 * @since 1.0
 */
public class MainView extends Application {
    private static final String RESOURCE_PATH = "src/main/java/com/github/cc3002/citricliquid/gui/resources/";
    public Scene scene1,scene2;
    private GameController controller = new GameController();
    private BoardBuilder builder = new BoardBuilder(controller);
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    private MovableNode player1Icon;
    private MovableNode player2Icon;
    private MovableNode player3Icon;
    private MovableNode player4Icon;

    private Dictionary playersIcons = new Hashtable();
    private Image lemonBackground;
    private ImageView board;
    private int iconSize;

    @Override
    public void start(@NotNull Stage stage) throws FileNotFoundException {

        stage.setTitle("99.7% Citric Liquid");
        int width = 1280;
        int height = 720;

        /**
         * Scene 1
         */
        VBox root = new VBox();
        scene1 = new Scene(root, width, height);

        // Background Image
        try {
            lemonBackground = new Image(new FileInputStream(RESOURCE_PATH + "lemon4.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BackgroundImage backgroundimage = new BackgroundImage(lemonBackground,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Background background = new Background(backgroundimage);

        // Welcome Text
        Text welcomeText = new Text ("Welcome to 99.7% Citric Liquid ");
        welcomeText.setFont(new Font("Cambria", 10));
        welcomeText.setFill(Color.web("#235723"));
        //welcomeText.setLayoutX(width/4);
        //welcomeText.setLayoutY(height/6);

        //Player's name
        Text nameText = new Text("Player's Name");
        welcomeText.setFont(new Font("Cambria", 25));
        //nameText.setLayoutX(width/3);
        //nameText.setLayoutY(height/2);

        TextField nameField = new TextField();
        //nameField.setLayoutX(width/3 + 200);
        //nameField.setLayoutY(height/2);

        //Start button
        Button startButton = new Button("Start Game");
        //startButton.setLayoutX(950);
        //startButton.setLayoutY(100);
        startButton.setFocusTraversable(false);
        startButton.setOnAction( e -> {
            stage.setScene(scene2);
            startGame();
        }

                       );

        //Adding nodes to the group
        root.setBackground(background);
        root.getChildren().addAll(welcomeText,nameText,nameField,startButton);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(50);

        stage.setScene(scene1);



        /**
         * Scene 2
         */
        Group root2 = new Group();
        scene2 = new Scene(root2,width,height);

        //Board
        try {
            board =
                    new ImageView(new Image(new FileInputStream(RESOURCE_PATH + "lemonBoard.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        iconSize = 75;
         player1Icon = new MovableNodeBuilder(scene2).setImagePath(RESOURCE_PATH + "shrek.png")
                .setPosition(495-iconSize/2, 210-iconSize/2)
                .setSize(iconSize, iconSize)
                .build();

         player2Icon = new MovableNodeBuilder(scene2).setImagePath(RESOURCE_PATH + "burro.png")
                .setPosition(785-iconSize/2, 210-iconSize/2)
                .setSize(iconSize, iconSize)
                .build();

         player3Icon = new MovableNodeBuilder(scene2).setImagePath(RESOURCE_PATH + "fiona.png")
                .setPosition(495-iconSize/2, 500-iconSize/2)
                .setSize(iconSize, iconSize)
                .build();

         player4Icon = new MovableNodeBuilder(scene2).setImagePath(RESOURCE_PATH + "pussNboots.png")
                .setPosition(785-iconSize/2, 500-iconSize/2)
                .setSize(iconSize, iconSize)
                .build();


        root2.getChildren().add(board);
        root2.getChildren().add(player1Icon.getNode());
        root2.getChildren().add(player2Icon.getNode());
        root2.getChildren().add(player3Icon.getNode());
        root2.getChildren().add(player4Icon.getNode());




        stage.show();
        //Controller


    }

    /**
     * Sets up the board, players and all the necessary to start the game
     */
    private void startGame(){
        controller.setView(this);
        controller.setBoard(builder.build());
        player1 = controller.createPlayer("Shrek",5,5,5,5,builder.getHomePanels(0));
        player2 = controller.createPlayer("Burro",5,5,5,5,builder.getHomePanels(1));
        player3 = controller.createPlayer("Fiona",5,5,5,5,builder.getHomePanels(2));
        player4 = controller.createPlayer("GatoConBotas",5,5,5,5,builder.getHomePanels(3));

        playersIcons.put(player1,player1Icon);
        playersIcons.put(player2,player2Icon);
        playersIcons.put(player3,player3Icon);
        playersIcons.put(player4,player4Icon);

        controller.setTurnOwner(player1);

        controller.beginTurn();



    }


    public void moveIcon(String position) {
        MovableNode icon = (MovableNode) playersIcons.get(controller.getTurnOwner());
        switch (position) {
            case "UP":
                icon.moveUp(iconSize);
                break;
            case "DOWN":
                icon.moveDown(iconSize);
                break;
            case "RIGHT":
                icon.moveRight(iconSize);
                break;
            case "LEFT":
                icon.moveLeft(iconSize);
                break;
            default:
                break;
        }

    }
}

