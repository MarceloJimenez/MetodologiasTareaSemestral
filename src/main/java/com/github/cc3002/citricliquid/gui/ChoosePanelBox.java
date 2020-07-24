package com.github.cc3002.citricliquid.gui;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.model.board.IPanel;
import com.github.cc3002.citricliquid.model.board.NPPos;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ChoosePanelBox {
    private static final String RESOURCE_PATH = "src/main/java/com/github/cc3002/citricliquid/gui/resources/";
    private static Image lemonBackground;
    private static ArrayList<IPanel> nextPanelsArray = new ArrayList<>();
    private GameController controller;

    public ChoosePanelBox(GameController controller){
        this.controller = controller;
    }

    public void display(Set<IPanel> nextPanels, String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);
        window.setMinHeight(100);

        Label label = new Label();
        label.setText(message);

        Label movesLeft = new Label();
        movesLeft.setText("You have " + controller.getMovesLeft() + " moves left");


        Iterator it = nextPanels.iterator();
        while(it.hasNext()){
            nextPanelsArray.add((IPanel) it.next());
        }
        ArrayList<NPPos> pos = controller.getTurnOwner().getCurrentPanel().getNPPosition();
        Button rightButton = new Button(pos.get(0).name());
        Button leftButton = new Button(pos.get(1).name());

        rightButton.setOnAction(e -> {

            controller.getView().moveIcon(pos.get(0).name());
            controller.setPlayerPanel(controller.getTurnOwner(),nextPanelsArray.get(0));
            //controller.movePlayer(controller.getMovesLeft());
            window.close();
        });
        leftButton.setOnAction(e -> {

            controller.getView().moveIcon(pos.get(1).name());
            controller.setPlayerPanel(controller.getTurnOwner(),nextPanelsArray.get(1));
            //controller.movePlayer(controller.getMovesLeft());
            window.close();
        });


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
        layout.getChildren().addAll(label,movesLeft,rightButton,leftButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
