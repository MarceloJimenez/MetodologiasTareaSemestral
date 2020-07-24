package com.github.cc3002.citricliquid.model.unit;
import java.util.Random;
/**
 * Abstract class that represents the common features and methods of a unit in the game 99.7% Citric Liquid.
 *
 * @author Marcelo Jimenez
 */
public abstract class AbstractUnit implements IUnit{
    protected String name;
    protected final Random random;
    protected int maxHP;
    protected int currentHP;
    protected int atk;
    protected int def;
    protected int evd;
    protected int stars;
    protected int wins;
    /**
     * Creates a new Unit.
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
     */

    public AbstractUnit(String name, int hp, int atk, int def, int evd) {
        this.name = name;
        this.maxHP = currentHP = hp;
        this.atk = atk;
        this.def = def;
        this.evd = evd;
        random = new Random();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractUnit that = (AbstractUnit) o;
        return maxHP == that.maxHP &&
                atk == that.atk &&
                def == that.def &&
                evd == that.evd &&
                name.equals(that.name);
    }

    /**
     * Returns the current hit points of the character.
     */
    @Override
    public int getCurrentHP() {
        return this.currentHP;
    }
    /**
     * Sets the current character's hit points.
     * <p>
     * The character's hit points have a constraint to always be between 0 and maxHP, both inclusive.
     */
    @Override
    public void setCurrentHP(final int newHP) {
        this.currentHP = Math.max(Math.min(newHP, maxHP), 0);
    }

    /**
     * Returns the current character's defense points.
     */
    @Override
        public int getDef() {return this.def;}


    /**
     * Returns the current character's attack points.
     */

    @Override
    public int getAtk() {
        return this.atk;
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

        if (this.roll()+this.def > fullAtk){
            this.setCurrentHP(currentHP);
        }
        else{
            this.setCurrentHP(currentHP-fullAtk);

        }
    }

    /**
     * Returns a uniformly distributed random value in [1, 6]
     */

    public int roll() {
        return random.nextInt(6) + 1;
    }
    /**
     * Set's the seed for this player's random number generator.
     * <p>
     * The random number generator is used for taking non-deterministic decisions, this method is
     * declared to avoid non-deterministic behaviour while testing the code.
     */
    @Override
    public void setSeed(long seed) {
           random.setSeed(seed);
        }

    /**
     * Check's if the current hit point values of the unit are equal to zero.
     */
    public boolean isKO() {return currentHP <= 0;}


    /**
     * Increases this units's star count by an amount.
     */
    public void increaseStarsBy(final int amount) { stars += amount; }

    /**
     * Reduces this units's star count by a given amount.
     * <p>
     * The star count will must always be greater or equal to 0
     */
    public void reduceStarsBy(final int amount) {
        stars = Math.max(0, stars - amount);
    }

    /**
     * Returns the name of the Unit
     * @return
     */

    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns the maximun HP of the unit
     * @return
     */

    @Override
    public int getMaxHP() {
        return this.maxHP;
    }

    /**
     * Returns the evade points of the unit
     * @return
     */

    @Override
    public int getEvd() {
        return this.evd;
    }

    /**
     * Returns this units's star count.
     */
    public int getStars() {
        return stars;
    }

    /**
     * Returns this unit's wins count.
     */
    public int getWins() {
        return wins;
    }

    /**
     * Increases this units's star count by an amount.
     */
    public void increaseWinsBy(final int amount) { wins += amount; }


}


