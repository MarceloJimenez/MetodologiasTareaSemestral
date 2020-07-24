package com.github.cc3002.citricjuice.model.controller;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.controller.States.MovePlayerState;
import com.github.cc3002.citricliquid.model.NormaGoal;
import com.github.cc3002.citricliquid.model.board.HomePanel;
import com.github.cc3002.citricliquid.model.board.IPanel;
import com.github.cc3002.citricliquid.model.unit.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObserverTest {
    private Player suguri;
    private Player shrek;
    private HomePanel testHomePanel;
    private HomePanel testHomePanel2;
    private GameController controller;
    private long testSeed = new Random().nextLong();
    private Random random = new Random();
    private IPanel testBonusPanel;
    private IPanel testNeutralPanel;
    private IPanel testBossPanel;



    private Player getPlayer() {
        return controller.createPlayer("suguri", 4, 2, -1, 2, testHomePanel);
    }
    private Player getPlayer2() {
        return controller.createPlayer("shrek", 4, 2, -1, 2, testHomePanel2);
    }

    private GameController getController() {
        return new GameController();
    }
    private int rollTest() {
        return random.nextInt(6) + 1;
    }

    private ArrayList<IPanel> getBoard(){
        controller.addNewPanel(testHomePanel);
        controller.addNewPanel(testBonusPanel);
        controller.addNewPanel(testNeutralPanel);
        controller.addNewPanel(testBossPanel);
        /*
        controller.setNextPanel(testHomePanel,testBonusPanel);
        controller.setNextPanel(testBonusPanel,testNeutralPanel);
        controller.setNextPanel(testBonusPanel,testBossPanel);

         */

        return controller.getPanels();
    }


    @BeforeEach
    public void setUp() {
        controller = getController();
        testHomePanel = controller.createHomePanel(1);
        testHomePanel2 = controller.createHomePanel(2);
        suguri = getPlayer();
        shrek = getPlayer2();
        testBonusPanel   = controller.createBonusPanel(1);
        testBossPanel    = controller.createBossPanel(1);
        testNeutralPanel = controller.createNeutralPanel(1);
        suguri.setSeed(testSeed);
        random.setSeed(testSeed);
    }

    @Test
    public void PlayerHomeHandlerTest(){
        controller.setState(new MovePlayerState());
        assertTrue(controller.atMovePlayer());
        controller.setPlayerHome(suguri,testHomePanel);
        assertEquals(suguri.getHome(),testHomePanel);
        controller.setPlayerPanel(suguri,testHomePanel);
        assertEquals(suguri.getCurrentPanel(),testHomePanel);
        assertTrue(controller.atIDLEHome());
    }

    @Test
    public void PlayerWinsHandlerTest(){
        controller.setState(new MovePlayerState());
        suguri.setNormaLevel(5);
        suguri.setNormaGoal(NormaGoal.STARS);
        suguri.increaseStarsBy(201);
        assertTrue(suguri.getStars() > 200);
        controller.setPlayerHome(suguri,testHomePanel);
        controller.setPlayerPanel(suguri,testHomePanel);
        controller.applyPanelEffect(testHomePanel);

        assertEquals(suguri, controller.getWinner());
    }



    @Test
    public void MultipleNextPanelsHandlerTest(){
        getBoard();
        controller.setState(new MovePlayerState());
        controller.atMovePlayer();
        controller.setPlayerPanel(suguri,testHomePanel);
        controller.movePlayer();

        assertEquals(testBonusPanel,suguri.getCurrentPanel());
        assertTrue(controller.atIDLENextPanel());

    }

    @Test
    public void NormaClearHandlerTest(){
        controller.setState(new MovePlayerState());
        assertTrue(controller.atMovePlayer());
        controller.setPlayerHome(suguri,testHomePanel);
        assertEquals(suguri.getHome(),testHomePanel);

        Player turnOwner = controller.getTurnOwner();
        turnOwner.increaseStarsBy(25);
        assertEquals(1,turnOwner.getNormaLevel());

        controller.setPlayerPanel(suguri,testHomePanel);
        assertEquals(suguri.getCurrentPanel(),testHomePanel);
        testHomePanel.activatedBy(turnOwner);

        assertEquals(2, turnOwner.getNormaLevel());
        assertTrue(controller.atIDLEHome());

    }

    @Test
    public void PlayersMeetHandlerTest(){
        controller.setState(new MovePlayerState());
        controller.setPlayerPanel(suguri,testNeutralPanel);
        controller.setPlayerPanel(shrek,testNeutralPanel);

        assertTrue(controller.atIDLEBattle());


    }
}
