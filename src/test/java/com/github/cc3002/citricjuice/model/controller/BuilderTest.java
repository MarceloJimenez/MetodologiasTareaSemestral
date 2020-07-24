package com.github.cc3002.citricjuice.model.controller;

import com.github.cc3002.citricliquid.controller.BoardBuilder;
import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.model.board.HomePanel;
import com.github.cc3002.citricliquid.model.board.IPanel;
import org.junit.jupiter.api.BeforeEach;

public class BuilderTest {
    private HomePanel testHomePanel;
    private IPanel testNeutralPanel;
    private GameController controller;
    private BoardBuilder builder;

    @BeforeEach
    public void setUp(){
        controller = new GameController();
        builder = new BoardBuilder(controller);
        testHomePanel = controller.createHomePanel(1);
        testNeutralPanel = controller.createNeutralPanel(1);

    }




}
