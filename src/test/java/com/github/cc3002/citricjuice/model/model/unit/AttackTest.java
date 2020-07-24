package com.github.cc3002.citricjuice.model.model.unit;

import com.github.cc3002.citricliquid.model.unit.BossUnit;
import com.github.cc3002.citricliquid.model.unit.IUnit;
import com.github.cc3002.citricliquid.model.unit.Player;
import com.github.cc3002.citricliquid.model.unit.WildUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AttackTest {
    private Player suguri;
    private WildUnit testWildUnit;
    private BossUnit testBossUnit;
    private long testSeedAttacker;
    private long testSeedOpponent;
    private Random RandomAtkr;
    private Random RandomOpnt;


    private Player getPlayer() {return new Player("suguri", 4, 2, -1, 2);}
    private WildUnit getWildUnit(){ return new WildUnit("Chicken",3,-1,-1,1);}
    private BossUnit getBossUnit(){return new BossUnit("Store Manager",8,3,2,-1);}
    private int rollAtkr(){return RandomAtkr.nextInt(6)+1;}
    private int rollOpnt(){return RandomOpnt.nextInt(6)+1;}
    private int rollTest(){return new Random().nextInt(6)+1;}

    @BeforeEach
    public void setUp(){
         suguri = getPlayer();
         testWildUnit = getWildUnit();
         testBossUnit = getBossUnit();
         testSeedAttacker =  new Random().nextLong();
         testSeedOpponent =  new Random().nextLong();
         RandomAtkr = new Random();
         RandomAtkr.setSeed(testSeedAttacker);
         RandomOpnt = new Random();
         RandomOpnt.setSeed(testSeedOpponent);

        }


    @Test
    public void testConstructor(){
        assertEquals(testWildUnit,testWildUnit);
        assertEquals(testBossUnit,testBossUnit);
        assertFalse(testWildUnit.equals(null));
        assertFalse(testBossUnit.equals(null));
        assertNotSame(testWildUnit,getWildUnit());
        assertNotSame(testBossUnit,getBossUnit());
        assertEquals(getWildUnit(),testWildUnit);
        assertEquals(getBossUnit(),testBossUnit);

    }

    @RepeatedTest(20)
    public void testPlayerVsWildUnitDefend(){
        WildUnit Attacker = testWildUnit;
        Player Opponent = suguri;
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int fullAttack = (rollAtkr()+Attacker.getAtk());
        int fullDefense = (rollOpnt() + Opponent.getDef());
        int expectedHp = Math.max(0,Opponent.getCurrentHP() - Math.max(1, fullAttack - fullDefense));
        Opponent.defendAttack(Attacker, Attacker.roll() + Attacker.getAtk() );
        assertEquals(expectedHp,Opponent.getCurrentHP());
    }
    @RepeatedTest(20)
    public void testPlayerVsWildUnitEvade(){
        Player Opponent = suguri;
        WildUnit Attacker = testWildUnit;
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int fullAttack = (rollAtkr()+Attacker.getAtk());
        int fullDefense = (rollOpnt() + Opponent.getDef() );
        int expectedHp = fullDefense > fullAttack ? Opponent.getCurrentHP() : (Math.max(Opponent.getCurrentHP() - fullAttack,0) ) ;
        Opponent.evadeAttack(Attacker, Attacker.roll() + Attacker.getAtk() );
        assertEquals(expectedHp,Opponent.getCurrentHP());
        }

    @RepeatedTest(20)
    public void testPlayerVsBossUnitDefend() {
        Player Attacker = suguri;
        BossUnit Opponent = testBossUnit;
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int fullAttack = (rollAtkr()+Attacker.getAtk());
        int fullDefense = (rollOpnt() + Opponent.getDef() );
        int expectedHp = Math.max(0,Opponent.getCurrentHP() - Math.max(1,fullAttack - fullDefense));
        Opponent.defendAttack(Attacker, Attacker.roll() + Attacker.getAtk() );
        assertEquals(expectedHp,Opponent.getCurrentHP());
    }

    @RepeatedTest(20)
    public void testPlayerVsBossUnitEvade(){
        Player Attacker = suguri;
        BossUnit Opponent = testBossUnit;
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int fullAttack  = rollAtkr() + Attacker.getAtk();
        int fullDefense = rollOpnt() + Opponent.getDef();
        int expectedHp = fullDefense > fullAttack ? Opponent.getCurrentHP() : Math.max(Opponent.getCurrentHP() - fullAttack,0);
        Opponent.evadeAttack(Attacker, Attacker.roll() + Attacker.getAtk() );
        assertEquals(expectedHp,Opponent.getCurrentHP());
    }



    @Test
    public void KOTest(){
        assertEquals(getWildUnit(),testWildUnit);
        assertFalse(testWildUnit.isKO());
        testWildUnit.setCurrentHP(0);
        assertTrue(testWildUnit.isKO());

        assertEquals(getBossUnit(),testBossUnit);
        assertFalse(testBossUnit.isKO());
        testBossUnit.setCurrentHP(0);
        assertTrue(testBossUnit.isKO());

        assertEquals(getPlayer(),suguri);
        assertFalse(suguri.isKO());
        suguri.setCurrentHP(0);
        assertTrue(suguri.isKO());
    }

    /*
    *-----------------ATTACK TESTING -------------------------------------
     */

    //---------PLAYER ATTACK----------------

    @RepeatedTest(50)
    public void playerAttackWildUnitTest() {
        Player Attacker = suguri;
        WildUnit Opponent = testWildUnit;
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int threshold = rollOpnt();
        int fullAttack  = rollAtkr() + Attacker.getAtk();
        int fullDefense = rollOpnt() + Opponent.getDef();

        assertEquals(0,Attacker.getWins());
        assertEquals(0,Attacker.getStars());
        Opponent.increaseStarsBy(rollTest());

        int StarsTraded = Opponent.getStars();
        int expectedStars  = Attacker.getStars() + StarsTraded;
        int expectedStars2 = Opponent.getStars() - StarsTraded;
        int expectedWins = Attacker.getWins()+1;

        if (threshold < 4) {
            int expectedHp = Math.max(0,(Opponent.getCurrentHP() - Math.max(1, fullAttack - fullDefense)));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());

        }

        else {
            int expectedHp = (fullDefense > fullAttack) ? Opponent.getCurrentHP() : (Math.max(Opponent.getCurrentHP() - fullAttack, 0));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());

        }

        if(Opponent.isKO()){
                assertEquals(expectedStars, Attacker.getStars());
                assertEquals(expectedStars2, Opponent.getStars());
                assertEquals(expectedWins, Attacker.getWins());

            }


    }
    @RepeatedTest(50)
    public void playerAttackBossUnitTest() {
        Player Attacker = suguri;
        BossUnit Opponent = testBossUnit;
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int threshold = rollOpnt();
        int fullAttack  = rollAtkr() + Attacker.getAtk();
        int fullDefense = rollOpnt() + Opponent.getDef();

        assertEquals(0,Attacker.getWins());
        assertEquals(0,Attacker.getStars());
        Opponent.increaseStarsBy(rollTest());

        int StarsTraded = Opponent.getStars();
        int expectedStars = Attacker.getStars() + StarsTraded;
        int expectedStars2 = Opponent.getStars() - StarsTraded;
        int expectedWins = Attacker.getWins()+3;

        if (threshold < 4) {
            int expectedHp = Math.max(0,(Opponent.getCurrentHP() - Math.max(1, fullAttack - fullDefense)));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        else {
            int expectedHp = (fullDefense > fullAttack) ? Opponent.getCurrentHP() : (Math.max(Opponent.getCurrentHP() - fullAttack, 0));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        if(Opponent.isKO()){
            assertEquals(expectedStars, Attacker.getStars());
            assertEquals(expectedStars2, Opponent.getStars());
            assertEquals(expectedWins, Attacker.getWins());
        }


    }
    /*
    @RepeatedTest(100)
    public void playerAttackPlayerTest() {
  }
     */


//----------------WILDUNIT ATTACK--------------------

    @RepeatedTest(100)
    public void WildUnitAttackWildUnitTest() {
        WildUnit Attacker = testWildUnit;
        WildUnit Opponent = getWildUnit();
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int threshold = rollOpnt();
        int fullAttack  = rollAtkr() + Attacker.getAtk();
        int fullDefense = rollOpnt() + Opponent.getDef();

        assertEquals(0,Attacker.getWins());
        assertEquals(0,Attacker.getStars());
        Opponent.increaseStarsBy(rollTest());

        int StarsTraded = (int) Math.floor(Opponent.getStars()/2);
        int expectedStars  = Attacker.getStars() + StarsTraded;
        int expectedStars2 = Opponent.getStars() - StarsTraded;
        int expectedWins = Attacker.getWins()+1;

        if (threshold < 4) {
            int expectedHp = Math.max(0,(Opponent.getCurrentHP() - Math.max(1, fullAttack - fullDefense)));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        else {
            int expectedHp = (fullDefense > fullAttack) ? Opponent.getCurrentHP() : (Math.max(Opponent.getCurrentHP() - fullAttack, 0));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        if(Opponent.isKO()){
            assertEquals(expectedStars, Attacker.getStars());
            assertEquals(expectedStars2, Opponent.getStars());
            assertEquals(expectedWins, Attacker.getWins());
        }
    }

    @RepeatedTest(100)
    public void WildUnitAttackBossUnitTest() {
        WildUnit Attacker = testWildUnit;
        BossUnit Opponent = testBossUnit;
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int threshold = rollOpnt();
        int fullAttack  = rollAtkr() + Attacker.getAtk();
        int fullDefense = rollOpnt() + Opponent.getDef();

        assertEquals(0,Attacker.getWins());
        assertEquals(0,Attacker.getStars());
        Opponent.increaseStarsBy(rollTest());

        int StarsTraded = (int) Math.floor(Opponent.getStars()/2);
        int expectedStars  = Attacker.getStars() + StarsTraded;
        int expectedStars2 = Opponent.getStars() - StarsTraded;
        int expectedWins = Attacker.getWins()+3;

        if (threshold < 4) {
            int expectedHp = Math.max(0,(Opponent.getCurrentHP() - Math.max(1, fullAttack - fullDefense)));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        else {
            int expectedHp = (fullDefense > fullAttack) ? Opponent.getCurrentHP() : (Math.max(Opponent.getCurrentHP() - fullAttack, 0));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        if(Opponent.isKO()){
            assertEquals(expectedStars, Attacker.getStars());
            assertEquals(expectedStars2, Opponent.getStars());
            assertEquals(expectedWins, Attacker.getWins());
        }
    }
   /*
    @RepeatedTest(100)
    public void WildUnitAttackPlayerTest() {

    }
*/
    //-------------------BOSSUNIT ATTACK---------------------------

    @RepeatedTest(100)
    public void BossUnitAttackWildUnitTest() {
        BossUnit Attacker = testBossUnit;
        WildUnit Opponent = testWildUnit;
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int threshold = rollOpnt();
        int fullAttack  = rollAtkr() + Attacker.getAtk();
        int fullDefense = rollOpnt() + Opponent.getDef();

        assertEquals(0,Attacker.getWins());
        assertEquals(0,Attacker.getStars());
        Opponent.increaseStarsBy(rollTest());

        int StarsTraded = (int) Math.floor(Opponent.getStars()/2);
        int expectedStars  = Attacker.getStars() + StarsTraded;
        int expectedStars2 = Opponent.getStars() - StarsTraded;
        int expectedWins = Attacker.getWins()+1;

        if (threshold < 4) {
            int expectedHp = Math.max(0,(Opponent.getCurrentHP() - Math.max(1, fullAttack - fullDefense)));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        else {
            int expectedHp = (fullDefense > fullAttack) ? Opponent.getCurrentHP() : (Math.max(Opponent.getCurrentHP() - fullAttack, 0));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        if(Opponent.isKO()){
            assertEquals(expectedStars, Attacker.getStars());
            assertEquals(expectedStars2, Opponent.getStars());
            assertEquals(expectedWins, Attacker.getWins());
        }
    }

    @RepeatedTest(100)
    public void BossUnitAttackBossUnitTest() {
        BossUnit Attacker = testBossUnit;
        BossUnit Opponent = getBossUnit();
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        int threshold = rollOpnt();
        int fullAttack  = rollAtkr() + Attacker.getAtk();
        int fullDefense = rollOpnt() + Opponent.getDef();

        assertEquals(0,Attacker.getWins());
        assertEquals(0,Attacker.getStars());
        Opponent.increaseStarsBy(rollTest());

        int StarsTraded = (int) Math.floor(Opponent.getStars()/2);
        int expectedStars  = Attacker.getStars() + StarsTraded;
        int expectedStars2 = Opponent.getStars() - StarsTraded;
        int expectedWins = Attacker.getWins()+3;

        if (threshold < 4) {
            int expectedHp = Math.max(0,(Opponent.getCurrentHP() - Math.max(1, fullAttack - fullDefense)));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        else {
            int expectedHp = (fullDefense > fullAttack) ? Opponent.getCurrentHP() : (Math.max(Opponent.getCurrentHP() - fullAttack, 0));
            Attacker.attack(Opponent);
            assertEquals(expectedHp, Opponent.getCurrentHP());
        }

        if(Opponent.isKO()){
            assertEquals(expectedStars, Attacker.getStars());
            assertEquals(expectedStars2, Opponent.getStars());
            assertEquals(expectedWins, Attacker.getWins());
        }
    }
/*
    @RepeatedTest(100)
    public void BossUnitAttackPlayerTest() {

    }
*/


public void UnitAttackDeadUnit(IUnit Attacker, IUnit Opponent){
    Attacker.setSeed(testSeedAttacker);
    Opponent.setSeed(testSeedOpponent);
    Opponent.setCurrentHP(0);
    Assertions.assertThrows(AssertionError.class, () -> {
        Attacker.attack(Opponent);
    });
}

    @Test
    public void PlayerAttackDeadWildUnit(){
    UnitAttackDeadUnit(suguri,testWildUnit);
}

    @Test
    public void PlayerAttackDeadBossUnit(){
        UnitAttackDeadUnit(suguri,testBossUnit);
    }

    @Test
    public void PlayerAttackDeadPlayer(){
        UnitAttackDeadUnit(suguri,getPlayer());
    }

    @Test
    public void WildUnitAttackDeadWildUnit(){
        UnitAttackDeadUnit(testWildUnit,getWildUnit());
    }
    @Test
    public void WildUnitAttackDeadBossUnit(){
        UnitAttackDeadUnit(testWildUnit,testBossUnit);
    }
    @Test
    public void WildUnitAttackDeadPlayer(){
        UnitAttackDeadUnit(testWildUnit,suguri);
    }
    @Test
    public void BossUnitAttackDeadWildUnit(){
        UnitAttackDeadUnit(testBossUnit,testWildUnit);
    }
    @Test
    public void BossUnitAttackDeadBosUnit(){
        UnitAttackDeadUnit(testBossUnit,getBossUnit());
    }
    @Test
    public void BossUnitAttackDeadPlayer(){
        UnitAttackDeadUnit(testBossUnit,suguri);
    }

    public void DeadUnitAttackUnit(IUnit Attacker, IUnit Opponent){
        Attacker.setSeed(testSeedAttacker);
        Opponent.setSeed(testSeedOpponent);
        Attacker.setCurrentHP(0);
        Assertions.assertThrows(AssertionError.class, () -> {
            Attacker.attack(Opponent);
        });
    }
    @Test
    public void DeadPlayerAttackWildUnit(){
        DeadUnitAttackUnit(suguri,testWildUnit);
    }

    @Test
    public void DeadPlayerAttackBossUnit(){
        DeadUnitAttackUnit(suguri,testBossUnit);
    }

    @Test
    public void DeadPlayerAttackPlayer(){
        DeadUnitAttackUnit(suguri,getPlayer());
    }

    @Test
    public void DeadWildUnitAttackWildUnit(){
        DeadUnitAttackUnit(testWildUnit,getWildUnit());
    }
    @Test
    public void DeadWildUnitAttackBossUnit(){
        DeadUnitAttackUnit(testWildUnit,testBossUnit);
    }
    @Test
    public void DeadWildUnitAttackPlayer(){
        DeadUnitAttackUnit(testWildUnit,suguri);
    }
    @Test
    public void DeadBossUnitAttackWildUnit(){
        DeadUnitAttackUnit(testBossUnit,testWildUnit);
    }
    @Test
    public void DeadBossUnitAttackBosUnit(){
        DeadUnitAttackUnit(testBossUnit,getBossUnit());
    }
    @Test
    public void DeadBossUnitAttackPlayer(){
        DeadUnitAttackUnit(testBossUnit,suguri);
    }
}






