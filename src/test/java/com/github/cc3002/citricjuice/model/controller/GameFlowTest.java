package com.github.cc3002.citricjuice.model.controller;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.controller.States.StartState;
import com.github.cc3002.citricliquid.model.board.HomePanel;
import com.github.cc3002.citricliquid.model.unit.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameFlowTest {

    private ArrayList<Player> testFlowPlayers;
    private Player Shrek;
    private Player Vegeta;
    private Random random = new Random();
    private GameController flowController;
    private HomePanel testHomePanel;
    private HomePanel testHomePanel2;
    private Player turnOwner;
    protected long testSeed = new Random().nextLong();
    protected int rollTest() {
        return random.nextInt(6) + 1;
    }

    protected GameController getController() {
        return new GameController();
    }

    @BeforeEach
    public void setUp(){
        flowController = getController();
        testHomePanel = flowController.createHomePanel(1);
        testHomePanel2 = flowController.createHomePanel(2);
        Vegeta = flowController.createPlayer("Vegeta",5,5,5,5,testHomePanel);
        Shrek = flowController.createPlayer("Shrek",5,5,5,5,testHomePanel2);
        Vegeta.setSeed(testSeed);
        random.setSeed(testSeed);
        flowController.setTurnOwner(Vegeta);
        turnOwner = Vegeta;
        flowController.setState(new StartState());

    }

    @RepeatedTest(50)
    public void startStateTest(){
        assertTrue(flowController.atStart());
        turnOwner.setCurrentHP(random.nextInt(1));
        flowController.beginTurn();
        if (turnOwner.getCurrentHP() == 0 ){
            assertTrue(flowController.atEndTurn());
            }
        else assertTrue(flowController.atMovePlayer());
    }

    @RepeatedTest(50)
    public void RecoveryStateTest(){
        turnOwner.setCurrentHP(0);
        flowController.setChapter(new Random().nextInt(6)+1);
        flowController.beginTurn();
        int recoveryRoll = Math.max(7 - flowController.getChapter(),0);
        if (rollTest() >= recoveryRoll){
            assertTrue(flowController.atMovePlayer());
        }
        else assertTrue(flowController.atEndTurn());
    }

    @Test
    public void MovePlayerStateTest(){

    }




    /*
    @RepeatedTest(50)
    public void BeginTurnStateTest(){
        assertEquals(flowController.getTurnOwner(),flowController.getPlayer(0));
        assertEquals(Vegeta,flowController.getTurnOwner());
        assertEquals(1,flowController.getChapter());
        assertTrue(flowController.atStart());


    }

    @RepeatedTest(100)
    public void KoCheckTest() {
        BeginTurnStateTest();
        assertTrue(flowController.atStart());
        assertEquals(1,flowController.getChapter());
        assertEquals(Vegeta,flowController.getTurnOwner());
        Vegeta.setCurrentHP(random.nextInt(10)-5);
        flowController.stateDoYourThing();

        if (flowController.getTurnOwner().isKO()){
            assertTrue(flowController.atRecovery());
        }
        else
            assertTrue(flowController.atStart());
    }

    @RepeatedTest(50)
    public void RecoveryStateTest(){
        BeginTurnStateTest();
        Vegeta.setCurrentHP(0);
        flowController.stateDoYourThing();
        assertTrue(flowController.atRecovery());
        flowController.stateDoYourThing();
        assertEquals(1,flowController.getChapter());
       int recoveryRoll = Math.max(7 - flowController.getChapter(),0);
        assertEquals(6,recoveryRoll);
        if (rollTest2() >= recoveryRoll){assertTrue(flowController.atStart()); }
        else assertTrue(flowController.atEndTurn());
    }

    @RepeatedTest(100)
    public void StarsStateTest(){
        BeginTurnStateTest();
        Vegeta.setCurrentHP(4);
        assertTrue(flowController.atStart());
        assertEquals(Vegeta,flowController.getTurnOwner());
        flowController.stateDoYourThing();
        assertTrue(flowController.atStart());

        int starsGiven =(int) Math.floor(flowController.getChapter()/5) +1;
        int expectedStars = flowController.getTurnOwner().getStars() + starsGiven;

        flowController.stateDoYourThing();
        assertEquals(expectedStars,flowController.getTurnOwner().getStars());

        assertTrue(flowController.atPlayCard());

    }

    @Test
    public void PlayCardStateTest(){
        StarsStateTest();
        assertTrue(flowController.atPlayCard());
        flowController.stateDoYourThing();
        assertTrue(flowController.atMovePlayer());
    }
    @Test
    public void MovePlayerStateTest(){
        PlayCardStateTest();
        assertTrue(flowController.atMovePlayer());
        flowController.stateDoYourThing();
        //if (flowController.atChooseNextPanel()){

        }


    @Test
    public void ChooseNextPanelState(){

    }
    @Test
    public void HomePanelState(){}
    @Test
    public void BattleState(){}





    @Test
    public void CardEffectStateTest(){
        MovePlayerStateTest();
        assertTrue(flowController.atCardEffect());
        flowController.stateDoYourThing();
        assertTrue(flowController.atPanelEffect());
    }

    @Test
    public void PanelEffectState(){
        CardEffectStateTest();
        assertTrue(flowController.atPanelEffect());
        Vegeta.setCurrentHP(1);
        int expectedHP = flowController.getTurnOwner().getCurrentHP() + 1;
        flowController.stateDoYourThing();
        assertEquals(expectedHP,flowController.getTurnOwner().getCurrentHP());
        assertTrue(flowController.atEndTurn());
    }

    @Test
    public void EndTurnTest(){
        PanelEffectState();
        assertTrue(flowController.atEndTurn());
        Player turnOwner= flowController.getTurnOwner();
        assertEquals(Vegeta,turnOwner);
        flowController.stateDoYourThing();
        assertEquals(Shrek,flowController.getTurnOwner());
        assertTrue(flowController.atStart());

    }




*/
}
