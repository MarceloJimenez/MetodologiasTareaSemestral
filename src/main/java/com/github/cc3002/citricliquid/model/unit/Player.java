package com.github.cc3002.citricliquid.model.unit;

import com.github.cc3002.citricliquid.controller.Handlers.MultipleNextPanelsHandler;
import com.github.cc3002.citricliquid.controller.Handlers.PlayerWinsHandler;
import com.github.cc3002.citricliquid.model.NormaGoal;
import com.github.cc3002.citricliquid.model.board.IPanel;
import org.jetbrains.annotations.NotNull;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.Random;

/**
 * This class represents a player in the game 99.7% Citric Liquid.
 *
 * @author <a href="mailto:ignacio.slater@ug.uchile.cl">Ignacio Slater
 *     Muñoz</a>.
 * @version 1.0.6-rc.3
 * @since 1.0
 *
 * Modified by: Marcelo Jimenez.
 */
public class Player implements IUnit{
  private final Random random;
  private final String name;
  private final int maxHP;
  private int atk;
  private int def;
  private int evd;
  private int normaLevel;
  private int stars ;
  private int wins ;
  private int currentHP;
  private IPanel panel;
  private NormaGoal normaGoal;
  private IPanel home;

  public PropertyChangeSupport PlayerWinsNotification = new PropertyChangeSupport(this);
  public PropertyChangeSupport MultipleNextPanelsNotification = new PropertyChangeSupport(this);
  /**
   * Creates a new character.
   * @param name
   *     the character's name.
   * @param hp
   *     the initial (and max) hit points of the character.
   * @param atk
*     the base damage the character does.
   * @param def
*     the base defense of the character.
   * @param evd
   *  the base evasion of the character
   *
   */
  public Player(final String name, final int hp, final int atk, final int def, final int evd) {
    this.name = name;
    this.maxHP = currentHP = hp;
    this.atk = atk;
    this.def = def;
    this.evd = evd;
    random = new Random();
    normaLevel = 1;
    stars = 0;
    wins = 0;
    normaGoal = NormaGoal.STARS;

  }


  /**
   * Increases this player's star count by an amount.
   */
  public void increaseStarsBy(final int amount) {
    stars += amount;
  }

  /**
   * Reduces this player's star count by a given amount.
   * <p>
   * The star count will must always be greater or equal to 0
   */
  public void reduceStarsBy(final int amount) {
    stars = Math.max(0, stars - amount);
  }


  /**
   * Returns this player's star count.
   */
  public int getStars() {
    return stars;
  }


  /**
   * Increases this player's wins count by an amount.
   */
  public void increaseWinsBy(final int amount){wins += amount;}


  /**
   * Set's current panel of this player. And observes the amount of next panels.
   * @param aPanel
   */

  public void setCurrentPanel(IPanel aPanel) {
    this.panel = aPanel;

      if(aPanel.getNextPanels().size()>1){
        MultipleNextPanelsNotification.firePropertyChange(
                new PropertyChangeEvent(this,"Multiple next panels", null,aPanel.getNextPanels() )
        );
           }
  }
  /**
   * Returns the current panel of this player.
   */

  @NotNull
  public IPanel getCurrentPanel() {
    return this.panel;
  }

  /**
   * Returns this player's wins count
   */
  public int getWins(){return wins;}
  /**
   * Set's the seed for this player's random number generator.
   * <p>
   * The random number generator is used for taking non-deterministic decisions, this method is
   * declared to avoid non-deterministic behaviour while testing the code.
   */
  public void setSeed(final long seed) {
    random.setSeed(seed);
  }



  /**
   * Returns a uniformly distributed random value in [1, 6]
   */
  public int roll() {
    return random.nextInt(6) + 1;
  }

  /**
   * Returns the character's name.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the character's max hit points.
   */
  public int getMaxHP() {
    return maxHP;
  }

  /**
   * Returns the current character's attack points.
   */
  public int getAtk() {
    return atk;
  }
  /**
   * Sets the current character's attack points.
  */
  public void setAtk( int newAtk){this.atk = newAtk;}
  /**
   * Returns the current character's defense points.
   */
  public int getDef() {  return def; }
  /**
   * Sets the current character's defense points.
   */
  public void setDef(int newDef){this.def = newDef;}

  /**
   * Returns the current character's evasion points.
   */
  public int getEvd() { return evd;}

  /**
   * Sets the current character's evasion points.
   */
  public void setEvd(int newEvd){this.evd = newEvd;}

  /**
   * Returns the current norma level
   */
  public int getNormaLevel() {
    return normaLevel;
  }

  /**
   * Set's the current norma level
   */
  public void setNormaLevel(int level) {
     normaLevel = level;  }
  /**
   * Performs a norma clear action; the {@code norma} counter increases in 1.
   */
  public void normaClear() {
    normaLevel++;
    if (normaLevel >= 6) {
      PlayerWinsNotification.firePropertyChange(new PropertyChangeEvent(this, "This Player has won", null, this));
    }
  }

  /**
   * Returns the current hit points of the character.
   */
  public int getCurrentHP() {
    return currentHP;
  }

  /**
   * Sets the current character's hit points.
   * <p>
   * The character's hit points have a constraint to always be between 0 and maxHP, both inclusive.
   */
  public void setCurrentHP(final int newHP) {
    this.currentHP = Math.max(Math.min(newHP, maxHP), 0);

  }


  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Player)) {
      return false;
    }
    final Player player = (Player) o;
    return getMaxHP() == player.getMaxHP() &&
           getAtk() == player.getAtk() &&
           getDef() == player.getDef() &&
           getEvd() == player.getEvd() &&
           getNormaLevel() == player.getNormaLevel() &&
           getStars() == player.getStars() &&
           getCurrentHP() == player.getCurrentHP() &&
           getName().equals(player.getName());
  }

  /**
   * Returns a copy of this character.
   */
  public Player copy() {
    return new Player(name, maxHP, atk, def, evd);
  }
  /**
   * Send's an attack message to another unit.
   * also it calculates the fullAtk amount, that is the sum of
   * the Unit's roll and it's attack points.
   * @param unit
   */
  @Override
  public void attack(IUnit unit) {
    if (this.isKO()){throw new AssertionError("This Player is Dead, Cannot Attack" );}
    else {
      int fullAttack = this.roll() + this.getAtk();
      unit.receivePlayerAttack(this, fullAttack);
    }
  }
  /**
   * Executes the defense of an attack.
   * The Unit subtracts from his hit points the following amount :
   * max{1, roll atk + ATK − (roll def + DEF)}
   *
   * @param unit is the attacking Unit
   * @param fullAtk is the sum of the roll and the attack points of @param unit
   */
  @Override
  public void defendAttack(IUnit unit, int fullAtk) {

    this.setCurrentHP(this.currentHP - Math.max(1, fullAtk - (this.roll() +this.def)));

  }

  /**
   * Executes the evasion of an attack.
   * If the unit's defense points plus it's roll is mayor than fullAtk, then it
   * receives no harm, if not, it subtracts from its hit points the fullAtk amount.
   *
   * @param unit is the attacking unit.
   * @param fullAtk is the is the sum of the roll and the attack points of @param unit
   */
  @Override
  public void evadeAttack(IUnit unit, int fullAtk) {

    if (this.roll()+this.def > fullAtk)
    {this.setCurrentHP(currentHP); }
    else
      { this.setCurrentHP(currentHP-fullAtk);}

  }
  /**
   * Receives an attack message from a Player, chooses randomly between defend or evade
   * and executes the corresponding method.
   * After, it check's if this player is KO and modifies the stars and wins of the attacking player.
   * @param player is the attacking player
   * @param fullAtk is the sum of the Player's roll and it's attack points.
   */
  @Override
  public void receivePlayerAttack(Player player, int fullAtk) {
    if (this.isKO()){throw new AssertionError("This Player is Dead, Cannot Be attacked" );}
    else {
      if (this.roll() < 4) {
        this.defendAttack(player, fullAtk);
      } else {
        this.evadeAttack(player, fullAtk);
      }
    }

    if(this.isKO()){
      player.increaseStarsBy((int) Math.floor(this.getStars()/2));
      this.reduceStarsBy((int) Math.floor(this.getStars()/2) );
      player.increaseWinsBy(2);

    }
  }
  /**
   * Receives an attack message from a Wild Unit, chooses randomly between defend or evade
   * and executes the corresponding method.
   * After, it check's if player unit is KO and modifies the stars and wins of the attacking wild unit.
   * @param wildUnit is the attacking Wild Unit
   * @param fullAtk is the sum of the Wild Unit's roll and it's attack points.
   */


  @Override
  public void receiveWildAttack(WildUnit wildUnit, int fullAtk) {
    if (this.isKO()){throw new AssertionError("This Player is Dead, Cannot Be attacked" );}
    else {
      if (this.roll() < 4) {
        this.defendAttack(wildUnit, fullAtk);
      } else {
        this.evadeAttack(wildUnit, fullAtk);
      }
    }
    if(this.isKO()){
      wildUnit.increaseStarsBy((int) Math.floor(this.getStars()/2));
      this.reduceStarsBy((int) Math.floor(this.getStars()/2));
      wildUnit.increaseWinsBy(2);
    }
  }
  /**
   * Receives an attack message from a Boss Unit, chooses randomly between defend or evade
   * and executes the corresponding method.
   * After, it check's if this player is KO and modifies the stars and wins of the attacking wild unit.
   * @param bossUnit is the attacking boss Unit
   * @param fullAtk is the sum of the boss Unit's roll and it's attack points.
   */
  @Override
  public void receiveBossAttack(BossUnit bossUnit, int fullAtk) {
    if (this.isKO()){throw new AssertionError("This Player is Dead, Cannot Be attacked" );}
    else {
      if (this.roll() < 4) {
        this.defendAttack(bossUnit, fullAtk);
      } else {
        this.evadeAttack(bossUnit, fullAtk);
      }
    }
    if (this.isKO()) {
      bossUnit.increaseStarsBy((int) Math.floor(this.getStars() / 2));
      this.reduceStarsBy((int) Math.floor(this.getStars() / 2));
      bossUnit.increaseWinsBy(2);
    }
  }

  /**
   * Check's if the current hit point values of the player are equal to zero.
   */

  @Override
  public boolean isKO() {
    return currentHP <= 0;
  }

/**
 * Set's the norma Goal of this player.
 * @param goal
 */
  public void setNormaGoal(NormaGoal goal) {
    this.normaGoal = goal;
  }

  /**
   * Returns the NormaGoal of this Player
   * @return normaGoal
   */
  public NormaGoal getNormaGoal() {
    return normaGoal;
  }
  /**
   * Set's the player's home panel.
   * @param panel
   */
  public void setHome(IPanel panel) {
    this.home =panel;
  }

  /**
   * Get's the player´s home panel
   * @return
   */
  public IPanel getHome() { return this.home;  }


  /**
   * Add's a Win observer
   */
  public void addWinObserver(PlayerWinsHandler handler){

    PlayerWinsNotification.addPropertyChangeListener(handler);
  }


  /**
   * Add's a Win observer
   */
  public void addPanelObserver(MultipleNextPanelsHandler handler){

   MultipleNextPanelsNotification.addPropertyChangeListener(handler);
  }

}