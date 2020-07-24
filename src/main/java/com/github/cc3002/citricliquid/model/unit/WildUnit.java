package com.github.cc3002.citricliquid.model.unit;

/**
 * This class represents a Wild Unit in the game 99.7% Citric Liquid.
 *
 * @author Marcelo Jimenez
 */

public class WildUnit extends AbstractUnit {
    /**
     * Creates a new Wild Unit.
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
    public WildUnit(final String name,final int hp, final int atk, final int def, final int evd) {
        super(name,hp, atk, def, evd);    }
    /**
     * Send's an attack message to another unit.
     * also it calculates the fullAtk amount, that is the sum of
     * the Unit's roll and it's attack points.
     * @param unit
     */
    @Override
    public void attack(IUnit unit) {
        if (this.isKO()){throw new AssertionError("This WildUnit is Dead, Cannot Attack" );}
        else {
            int fullAtk = this.roll() + this.atk;
            unit.receiveWildAttack(this, fullAtk);
        }
    }
    /**
     * Receives an attack message from a Player, chooses randomly between defend or evade
     * and executes the corresponding method.
     * After, it check's if this unit is KO and modifies the stars and wins of the attacking player.
     * @param player is the attacking player
     * @param fullAtk is the sum of the Player's roll and it's attack points.
     */
    @Override
    public void receivePlayerAttack(Player player, int fullAtk) {
        if (this.isKO()){throw new AssertionError("This WildUnit is Dead, Cannot Be attacked" );}
        else {
            if (this.roll() < 4) {
                this.defendAttack(player, fullAtk);
            } else {
                this.evadeAttack(player, fullAtk);
            }
        }
        if(this.isKO()){
            player.increaseStarsBy(this.getStars());
            this.reduceStarsBy(this.getStars());
            player.increaseWinsBy(1);
        }
    }

    /**
     * Receives an attack message from a Wild Unit, chooses randomly between defend or evade
     * and executes the corresponding method.
     * After, it check's if this unit is KO and modifies the stars and wins of the attacking wild unit.
     * @param wildUnit is the attacking Wild Unit
     * @param fullAtk is the sum of the Wild Unit's roll and it's attack points.
     */
    @Override
    public void receiveWildAttack(WildUnit wildUnit, int fullAtk) {
        if (this.isKO()){throw new AssertionError("This WildUnit is Dead, Cannot Be attacked" );}
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
            wildUnit.increaseWinsBy(1);
        }

    }
    /**
     * Receives an attack message from a Boss Unit, chooses randomly between defend or evade
     * and executes the corresponding method.
     * After, it check's if this Boss unit is KO and modifies the stars and wins of the attacking wild unit.
     * @param bossUnit is the attacking boss Unit
     * @param fullAtk is the sum of the boss Unit's roll and it's attack points.
     */
    @Override
    public void receiveBossAttack(BossUnit bossUnit, int fullAtk) {
        if (this.isKO()){throw new AssertionError("This WildUnit is Dead, Cannot Be attacked" );}
        else {
            if (this.roll() < 4) {
                this.defendAttack(bossUnit, fullAtk);
            } else {
                this.evadeAttack(bossUnit, fullAtk);
            }
        }
        if(this.isKO()){
            bossUnit.increaseStarsBy((int) Math.floor(this.getStars()/2));
            this.reduceStarsBy((int) Math.floor(this.getStars()/2));
            bossUnit.increaseWinsBy(1);
        }
    }



}
