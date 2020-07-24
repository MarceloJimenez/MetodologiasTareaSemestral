package com.github.cc3002.citricliquid.model.unit;

import com.github.cc3002.citricliquid.model.board.IPanel;

/**
 * This Interface list's all the common methods of Boss and Wild Units and player character's.
 *
 * @author Marcelo Jimenez
 */

public interface IUnit {


    int roll();

    void setSeed(final long seed);

    void attack(IUnit unit);

    void defendAttack(IUnit unit, int fullAtk);

    void evadeAttack(IUnit unit, int fullAtk);

    void receivePlayerAttack(Player player, int fullAttack);

    void receiveWildAttack(WildUnit wildUnit, int fullAtk);

    void receiveBossAttack(BossUnit bossUnit, int fullAtk);

    boolean isKO();

    int getCurrentHP();

    void setCurrentHP(int hp);

    void increaseStarsBy(final int amount);

    void reduceStarsBy(final int amount);

    String getName();

    int getDef();

    int getStars();

    int getAtk();

    int getWins();

    void increaseWinsBy(int amount);

    int getMaxHP();

    int getEvd();
}

