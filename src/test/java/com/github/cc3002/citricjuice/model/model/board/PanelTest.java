package com.github.cc3002.citricjuice.model.model.board;

import com.github.cc3002.citricliquid.model.board.*;
import com.github.cc3002.citricliquid.model.unit.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="mailto:ignacio.slater@ug.uchile.cl">Ignacio Slater M.</a>.
 * @version 1.0.6-rc.1
 * @since 1.0
 */
class PanelTest {
  private final static String PLAYER_NAME = "Suguri";
  private final static int BASE_HP = 4;
  private final static int BASE_ATK = 1;
  private final static int BASE_DEF = -1;
  private final static int BASE_EVD = 2;
  private HomePanel testHomePanel;
  private NeutralPanel testNeutralPanel;
  private BonusPanel testBonusPanel;
  private DropPanel testDropPanel;
  private EncounterPanel testEncounterPanel;
  private BossPanel testBossPanel;
  private Player suguri;
  private long testSeed;

  @BeforeEach
  public void setUp() {
    testBonusPanel = new BonusPanel(1);
    testBossPanel = new BossPanel(1);
    testDropPanel = new DropPanel(1);
    testEncounterPanel = new EncounterPanel(1);
    testHomePanel = new HomePanel(1);
    testNeutralPanel = new NeutralPanel(1);
    testSeed = new Random().nextLong();
    suguri = new Player(PLAYER_NAME, BASE_HP, BASE_ATK, BASE_DEF, BASE_EVD);
  }


  @Test
  public void constructorTest(){
    final var expectedBonusPanel = new BonusPanel(1);
    final var expectedBossPanel = new BossPanel(1);
    final var expectedDropPanel = new DropPanel(1);
    final var expectedEncounterPanel = new EncounterPanel(1);
    final var expectedHomePanel = new HomePanel(1);
    final var expectedtNeutralPanel = new NeutralPanel(1);
    assertEquals(expectedBonusPanel,testBonusPanel);
    assertEquals(expectedBossPanel,testBossPanel);
    assertEquals(expectedDropPanel,testDropPanel);
    assertEquals(expectedEncounterPanel,testEncounterPanel);
    assertEquals(expectedHomePanel,testHomePanel);
    assertEquals(expectedtNeutralPanel,testNeutralPanel);

  }

  @Test
  public void nextPanelTest() {
    assertTrue(testNeutralPanel.getNextPanels().isEmpty());
    final var expectedPanel1 = new NeutralPanel(2);
    final var expectedPanel2 = new NeutralPanel(3 );

    testNeutralPanel.addNextPanel(expectedPanel1);
    assertEquals(1, testNeutralPanel.getNextPanels().size());

    testNeutralPanel.addNextPanel(expectedPanel2);
    assertEquals(2, testNeutralPanel.getNextPanels().size());

    testNeutralPanel.addNextPanel(expectedPanel2);
    assertEquals(2, testNeutralPanel.getNextPanels().size());

    assertEquals(Set.of(expectedPanel1, expectedPanel2),
                 testNeutralPanel.getNextPanels());
  }

  @Test
  public void homePanelTest() {
    assertEquals(suguri.getMaxHP(), suguri.getCurrentHP());
    testHomePanel.activatedBy(suguri);
    assertEquals(suguri.getMaxHP(), suguri.getCurrentHP());

    suguri.setCurrentHP(1);
    testHomePanel.activatedBy(suguri);
    assertEquals(2, suguri.getCurrentHP());
  }

  @Test
  public void neutralPanelTest() {
    final var expectedSuguri = suguri.copy();
    testNeutralPanel.activatedBy(suguri);
    assertEquals(expectedSuguri, suguri);
  }


  // region : Consistency tests
  @RepeatedTest(100)
  public void bonusPanelConsistencyTest() {
    int expectedStars = 0;
    assertEquals(expectedStars, suguri.getStars());
    final var testRandom = new Random(testSeed);
    suguri.setSeed(testSeed);
    for (int normaLvl = 1; normaLvl <= 6; normaLvl++) {
      final int roll = testRandom.nextInt(6) + 1;
      testBonusPanel.activatedBy(suguri);
      expectedStars += roll * Math.min(3, normaLvl);
      assertEquals(expectedStars, suguri.getStars(),
                   "Test failed with seed: " + testSeed);
      suguri.normaClear();
    }
  }

  @RepeatedTest(100)
  public void dropPanelConsistencyTest() {
    int expectedStars = 30;
    suguri.increaseStarsBy(30);
    assertEquals(expectedStars, suguri.getStars());
    final var testRandom = new Random(testSeed);
    suguri.setSeed(testSeed);
    for (int normaLvl = 1; normaLvl <= 6; normaLvl++) {
      final int roll = testRandom.nextInt(6) + 1;
      testDropPanel.activatedBy(suguri);
      expectedStars = Math.max(expectedStars - roll * normaLvl, 0);
      assertEquals(expectedStars, suguri.getStars(),
                   "Test failed with seed: " + testSeed);
      suguri.normaClear();
    }


  }
  @Test
  public void addRemoveSteadyPlayerTest() {
    testHomePanel.addPlayer(suguri);
    assertTrue(testHomePanel.getPlayers().contains(suguri));
    testHomePanel.removePlayer(suguri);
    assertFalse(testHomePanel.getPlayers().contains(suguri));
    //throw new AssertionError("QUE NO SE TE OLVIDE");
  }
  @Test
  public void nextPlayerPositionTest(){
    testHomePanel.addNextPanel(testNeutralPanel);
    testHomePanel.setNPPosition(testNeutralPanel, NPPos.DOWN);

    assertEquals(NPPos.DOWN, testHomePanel.getNPPosition(testNeutralPanel));

  }

  // endregion
}