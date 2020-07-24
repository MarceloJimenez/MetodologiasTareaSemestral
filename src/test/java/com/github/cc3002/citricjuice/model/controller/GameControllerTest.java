package com.github.cc3002.citricjuice.model.controller;

import com.github.cc3002.citricliquid.controller.GameController;
import com.github.cc3002.citricliquid.controller.States.MovePlayerState;
import com.github.cc3002.citricliquid.model.NormaGoal;
import com.github.cc3002.citricliquid.model.board.*;
import com.github.cc3002.citricliquid.model.unit.BossUnit;
import com.github.cc3002.citricliquid.model.unit.Player;
import com.github.cc3002.citricliquid.model.unit.WildUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {
    protected Player suguri;
    protected WildUnit testWildUnit;
    protected BossUnit testBossUnit;
    protected HomePanel testHomePanel;
    protected NeutralPanel testNeutralPanel;
    protected NeutralPanel testNeutralPanel2;
    protected BonusPanel testBonusPanel;
    protected DropPanel testDropPanel;
    protected EncounterPanel testEncounterPanel;
    protected BossPanel testBossPanel;
    protected GameController controller;
    protected GameController controller2;
    protected long testSeed = new Random().nextLong();
    protected Random random = new Random();
    //protected ArrayList<IPanel> board = new ArrayList<>();
    protected ArrayList<Player> testPlayers;
    protected ArrayList<Player> testPlayers2;
    protected ArrayList<WildUnit> testWilds;
    protected ArrayList<BossUnit> testBosses;


    protected Player getPlayer() {
        return controller.createPlayer("suguri", 4, 2, -1, 2, testHomePanel);
    }

    protected WildUnit getWildUnit() {
        return new WildUnit("Chicken", 3, -1, -1, 1);
    }

    protected BossUnit getBossUnit() {
        return new BossUnit("Store Manager", 8, 3, 2, -1);
    }

    protected GameController getController() {
        return new GameController();
    }

    protected int rollTest() {
        return random.nextInt(6) + 1;
    }

    protected void createTestPlayers() {
        testPlayers = new ArrayList<>();
        List.of("Suguri", "Vegeta", "Shrek", "LaVendeHandRoll").forEach(name -> testPlayers.add(
                controller.createPlayer(name, random.nextInt(10), random.nextInt(10) - 10,
                        random.nextInt(10) - 10, random.nextInt(10) - 10,testHomePanel)));
    }
    protected void createTestPlayers2() {
        testPlayers2 = new ArrayList<>();
        List.of("Suguri", "Vegeta", "Shrek", "LaVendeHandRoll").forEach(name -> testPlayers2.add(
                controller2.createPlayer(name, random.nextInt(10), random.nextInt(10) - 10,
                        random.nextInt(10) - 10, random.nextInt(10) - 10,testHomePanel)));
    }

    protected void createTestWildUnits() {
        testWilds = new ArrayList<>();
        List.of("Chicken", "Robo ball", "Seagull").forEach(name -> testWilds.add(
                new WildUnit(name, random.nextInt(10), random.nextInt(10) - 10,
                        random.nextInt(10) - 10, random.nextInt(10) - 10)));
    }

    protected void createBossUnits() {
        testBosses = new ArrayList<>();
        List.of("Store Manager", "Shifu Robot", "Flying Castle").forEach(name -> testBosses.add(
                new BossUnit(name, random.nextInt(10), random.nextInt(10) - 10,
                        random.nextInt(10) - 10, random.nextInt(10) - 10)));
    }

    protected ArrayList<IPanel> getBoard() {
       controller.addNewPanel(testBonusPanel);
       controller.setNextPanel(testBonusPanel,testNeutralPanel2);
       controller.addNewPanel(testNeutralPanel2);
       controller.setNextPanel(testNeutralPanel2,testBossPanel);
       controller.addNewPanel(testBossPanel);
       controller.setNextPanel(testBossPanel,testDropPanel);
       controller.addNewPanel(testDropPanel);
       controller.setNextPanel(testDropPanel,testEncounterPanel);
       controller.addNewPanel(testEncounterPanel);
       controller.setNextPanel(testEncounterPanel,testHomePanel);
       controller.addNewPanel(testHomePanel);
       controller.setNextPanel(testHomePanel,testNeutralPanel);
       controller.addNewPanel(testNeutralPanel);
       controller.setNextPanel(testNeutralPanel,testBonusPanel);
        return controller.getPanels();
    }


    @BeforeEach
    public void setUp() {
        controller = getController();

        testBonusPanel = new BonusPanel(1);
        testBossPanel = new BossPanel(1);
        testDropPanel = new DropPanel(1);
        testEncounterPanel = new EncounterPanel(1);
        testHomePanel = new HomePanel(1);
        testNeutralPanel = new NeutralPanel(1);
        testNeutralPanel2 = new NeutralPanel(2);

        suguri = getPlayer();
        testWildUnit = getWildUnit();
        testBossUnit = getBossUnit();
        createTestPlayers();
        createTestWildUnits();
        createBossUnits();

        suguri.setSeed(testSeed);
        random.setSeed(testSeed);

    }

    @Test
    public void panelConstructorTest(){
        final var expectedBonusPanel = controller.createBonusPanel(1);
        final var expectedBossPanel = controller.createBossPanel(1);
        final var expectedDropPanel = controller.createDropPanel(1);
        final var expectedEncounterPanel = controller.createEncounterPanel(1);
        final var expectedHomePanel = controller.createHomePanel(1);
        final var expectedtNeutralPanel = controller.createNeutralPanel(1);
        assertEquals(expectedBonusPanel,testBonusPanel);
        assertEquals(expectedBossPanel,testBossPanel);
        assertEquals(expectedDropPanel,testDropPanel);
        assertEquals(expectedEncounterPanel,testEncounterPanel);
        assertEquals(expectedHomePanel,testHomePanel);
        assertEquals(expectedtNeutralPanel,testNeutralPanel);
    }
    @Test
    public void setNextPanelTest(){
        assertTrue(controller.getNextPanel(testNeutralPanel).isEmpty());
        final var expectedPanel1 = controller.createNeutralPanel(2);
        final var expectedPanel2 = controller.createNeutralPanel(3);

        controller.setNextPanel(testNeutralPanel,expectedPanel1);
        assertEquals(1, controller.getNextPanel(testNeutralPanel).size());

        controller.setNextPanel(testNeutralPanel,testNeutralPanel);
        assertEquals(1, controller.getNextPanel(testNeutralPanel).size());

        controller.setNextPanel(testNeutralPanel,expectedPanel2);
        assertEquals(2, controller.getNextPanel(testNeutralPanel).size());

        controller.setNextPanel(testNeutralPanel,expectedPanel2);
        assertEquals(2, controller.getNextPanel(testNeutralPanel).size());

        assertEquals(Set.of(expectedPanel1, expectedPanel2),
                controller.getNextPanel(testNeutralPanel));

    }
    @Test
    public void testCreatePlayer() {
        for (var player : testPlayers) {
            var actualPlayer =
                    controller.createPlayer(player.getName(), player.getMaxHP(), player.getAtk(),
                            player.getDef(), player.getEvd(), testHomePanel);
            assertEquals(player, actualPlayer);
            assertEquals(actualPlayer.getCurrentPanel(), testHomePanel);
        }

    }

    @Test
    public void testCreateWildUnit() {
        for (var wildUnit : testWilds) {
            var actualWildUnit =
                    controller.createWildUnit(wildUnit.getName(), wildUnit.getMaxHP(), wildUnit.getAtk(),
                            wildUnit.getDef(), wildUnit.getEvd());
            assertEquals(wildUnit, actualWildUnit);
        }
    }
    @Test
    public void testCreateBossUnit() {
        for (var bossUnit : testBosses) {
            var actualBossUnit =
                    controller.createBossUnit(bossUnit.getName(), bossUnit.getMaxHP(), bossUnit.getAtk(),
                            bossUnit.getDef(), bossUnit.getEvd());
            assertEquals(bossUnit, actualBossUnit);
        }
    }

@Test
public void getAllPanelsTest(){
        controller.addNewPanel(testBonusPanel);
        controller.addNewPanel(testNeutralPanel);
        controller.addNewPanel(testBossPanel);
        var Board = controller.getPanels();
        assertTrue(Board.contains(testBonusPanel));
        assertTrue(Board.contains(testNeutralPanel));
        assertTrue(Board.contains(testBossPanel));

}

@Test
public void Winner(){
        assertNull(controller.getWinner());
       suguri.setNormaLevel(5);
       suguri.increaseStarsBy(200);
       suguri.setCurrentPanel(testHomePanel);
       controller.normaClear(suguri);
       assertEquals(suguri,controller.getWinner());
}

@Test
public void setPlayerHomeTest(){
    controller.setBoard(getBoard());
    controller.setTurnOwner(suguri);
    Player turnOwner = controller.getTurnOwner();
    assertEquals(suguri, turnOwner);

    controller.setPlayerHome(turnOwner,testHomePanel);

    assertEquals(testHomePanel, controller.getPlayerHome(turnOwner));




}
    private ArrayList<IPanel> getBoard2() {
        controller.addNewPanel(testBonusPanel);
        controller.setNextPanel(testBonusPanel,testNeutralPanel2);
        controller.addNewPanel(testNeutralPanel2);
        controller.setNextPanel(testNeutralPanel2,testBossPanel);
        controller.addNewPanel(testBossPanel);
        controller.setNextPanel(testBossPanel,testDropPanel);
        controller.addNewPanel(testDropPanel);
        controller.setNextPanel(testDropPanel,testBonusPanel);

        return controller.getPanels();
    }
    private ArrayList<IPanel> getBoard3() {

        controller.addNewPanel(testBonusPanel);
        controller.setNextPanel(testBonusPanel,testNeutralPanel);
        controller.addNewPanel(testNeutralPanel);
        controller.setNextPanel(testBonusPanel,testNeutralPanel2);
        controller.addNewPanel(testNeutralPanel2);

        controller.setNextPanel(testNeutralPanel2,testBossPanel);
        controller.setNextPanel(testNeutralPanel,testBossPanel);
        controller.addNewPanel(testBossPanel);

        controller.setNextPanel(testBossPanel,testDropPanel);
        controller.addNewPanel(testDropPanel);
        controller.setNextPanel(testDropPanel,testBonusPanel);

        return controller.getPanels();
    }



    @RepeatedTest(50)
    public void movePlayerTest() {
        controller.setBoard(getBoard2());
        controller.setTurnOwner(suguri);
        Player turnOwner = controller.getTurnOwner();
        assertEquals(suguri, turnOwner);
        controller.setPlayerPanel(turnOwner,testBonusPanel);
        IPanel currentPanel = controller.getPlayerPanel(turnOwner);
        assertNotNull(currentPanel);
        assertTrue(controller.getPanels().contains(currentPanel));
        int move = rollTest();
        IPanel tmpPanel = currentPanel;

        for (int x = 0; x < move; x++) {
            Iterator<IPanel> i = tmpPanel.getNextPanels().iterator();
            while (i.hasNext()) {
                if (tmpPanel.getNextPanels().size() == 1) {
                    tmpPanel = i.next();
                }
                else {
                    tmpPanel = currentPanel;
                }
            }
        }
        IPanel ExpectedPanel = tmpPanel;
        controller.movePlayer();
        assertNotNull(ExpectedPanel);
        assertTrue(controller.getPanels().contains(ExpectedPanel));
        assertEquals(suguri, turnOwner);
        assertEquals(ExpectedPanel, turnOwner.getCurrentPanel());

    }

    @Test
    public void stopAtNextPanelsTest(){
        controller.setBoard(getBoard3());
        controller.setTurnOwner(suguri);
        controller.setState(new MovePlayerState());
        Player turnOwner = controller.getTurnOwner();
        assertEquals(suguri, turnOwner);
        controller.setPlayerPanel(turnOwner,testBonusPanel);
        controller.movePlayer();
        assertTrue(controller.getMovesLeft()>0);

    }



    @Test
    public void normaCheckStarsTest(){
        controller.setBoard(getBoard());
        controller.setTurnOwner(suguri);
        Player turnOwner = controller.getTurnOwner();
        assertEquals(suguri, turnOwner);

        controller.setPlayerPanel(turnOwner,testHomePanel);
        IPanel currentPanel = controller.getPlayerPanel(turnOwner);
        assertNotNull(currentPanel);

        //normaCheck Stars
        turnOwner.increaseStarsBy(9);
        assertEquals(9,turnOwner.getStars());
        assertEquals(0,turnOwner.getWins());
        assertEquals(NormaGoal.STARS, turnOwner.getNormaGoal());
        assertFalse(controller.normaCheck(turnOwner));
        controller.normaClear(turnOwner);
        assertEquals(1,turnOwner.getNormaLevel());

        turnOwner.increaseStarsBy(1);
        assertEquals(10,turnOwner.getStars());
        assertTrue(controller.normaCheck(turnOwner));
        controller.normaClear(turnOwner);
        assertEquals(2,turnOwner.getNormaLevel());

        turnOwner.increaseStarsBy(20);
        assertEquals(30,turnOwner.getStars());
        controller.normaClear(turnOwner);
        assertEquals(3,turnOwner.getNormaLevel());

        turnOwner.increaseStarsBy(40);
        assertEquals(70,turnOwner.getStars());
        controller.normaClear(turnOwner);
        assertEquals(4,turnOwner.getNormaLevel());

        turnOwner.increaseStarsBy(50);
        assertEquals(120,turnOwner.getStars());
        controller.normaClear(turnOwner);
        assertEquals(5,turnOwner.getNormaLevel());

        turnOwner.increaseStarsBy(80);
        assertEquals(200,turnOwner.getStars());
        controller.normaClear(turnOwner);
        assertEquals(6,turnOwner.getNormaLevel());

        turnOwner.increaseStarsBy(80);
        assertEquals(280,turnOwner.getStars());
        controller.normaClear(turnOwner);
        assertEquals(6,turnOwner.getNormaLevel());




    }
    @Test
    public void normaCheckWinsTest(){
        controller.setBoard(getBoard());
        controller.setTurnOwner(suguri);
        Player turnOwner = controller.getTurnOwner();
        assertEquals(suguri, turnOwner);

        controller.setPlayerPanel(turnOwner,testHomePanel);
        IPanel currentPanel = controller.getPlayerPanel(turnOwner);
        assertNotNull(currentPanel);

        //normaCheck Stars
        turnOwner.increaseStarsBy(9);
        assertEquals(9,turnOwner.getStars());
        assertEquals(0,turnOwner.getWins());
        assertEquals(NormaGoal.STARS, turnOwner.getNormaGoal());
        assertFalse(controller.normaCheck(turnOwner));
        controller.normaClear(turnOwner);
        assertEquals(1,turnOwner.getNormaLevel());

        turnOwner.increaseStarsBy(2000);
        controller.normaClear(turnOwner);
        assertEquals(2,turnOwner.getNormaLevel());
        turnOwner.reduceStarsBy(2000);

        turnOwner.increaseStarsBy(1);
        assertEquals(10,turnOwner.getStars());
        controller.normaClear(turnOwner);
        assertEquals(2,turnOwner.getNormaLevel());

        //normaCheck Wins

        controller.setCurrPlayerNormaGoal(NormaGoal.WINS);
        turnOwner.increaseWinsBy(2);
        assertEquals(2,turnOwner.getWins());
        controller.normaClear(turnOwner);
        assertEquals(3,turnOwner.getNormaLevel());

        turnOwner.increaseWinsBy(3);
        assertEquals(5,turnOwner.getWins());
        controller.normaClear(turnOwner);
        assertEquals(4,turnOwner.getNormaLevel());

        turnOwner.increaseWinsBy(4);
        assertEquals(9,turnOwner.getWins());
        controller.normaClear(turnOwner);
        assertEquals(5,turnOwner.getNormaLevel());

        turnOwner.increaseWinsBy(5);
        assertEquals(14,turnOwner.getWins());
        controller.normaClear(turnOwner);
        assertEquals(6,turnOwner.getNormaLevel());

        turnOwner.increaseWinsBy(8);
        assertEquals(22,turnOwner.getWins());
        controller.normaClear(turnOwner);
        assertEquals(6,turnOwner.getNormaLevel());

    }

    @Test
    public void setPlayerPanelTest(){
        assertEquals(testHomePanel,suguri.getCurrentPanel());
        assertTrue(testHomePanel.getPlayers().contains(suguri));
        controller.setPlayerPanel(suguri, testNeutralPanel2);
        assertFalse(testHomePanel.getPlayers().contains(suguri));
        assertTrue(testNeutralPanel2.getPlayers().contains(suguri));
        assertEquals(testNeutralPanel2,suguri.getCurrentPanel());

    }

    @Test
    public void endTurnTest(){
        controller2 = getController();
        createTestPlayers2();
        assertEquals("Suguri",testPlayers2.get(0).getName());
        assertEquals("Suguri",controller2.getTurnOwner().getName());
        assertEquals(testPlayers2.get(0),controller2.getTurnOwner());
        assertEquals(1,controller2.getChapter());
        controller2.endTurn();
        assertEquals(testPlayers2.get(1),controller2.getTurnOwner());
        assertEquals(1,controller2.getChapter());
        controller2.endTurn();
        assertEquals(testPlayers2.get(2),controller2.getTurnOwner());
        assertEquals(1,controller2.getChapter());
        controller2.endTurn();
        assertEquals(testPlayers2.get(3),controller2.getTurnOwner());
        assertEquals(1,controller2.getChapter());
        controller2.endTurn();
        assertEquals(testPlayers2.get(0),controller2.getTurnOwner());
        assertEquals(2,controller2.getChapter());
        controller2.endTurn();


    }
}
