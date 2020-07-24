package com.github.cc3002.citricliquid.controller;

import com.github.cc3002.citricliquid.controller.Handlers.*;
import com.github.cc3002.citricliquid.controller.States.InvalidTransitionException;
import com.github.cc3002.citricliquid.controller.States.StartState;
import com.github.cc3002.citricliquid.controller.States.State;
import com.github.cc3002.citricliquid.gui.AlertBox;
import com.github.cc3002.citricliquid.gui.ChoosePanelBox;
import com.github.cc3002.citricliquid.gui.MainView;
import com.github.cc3002.citricliquid.model.NormaGoal;
import com.github.cc3002.citricliquid.model.board.*;
import com.github.cc3002.citricliquid.model.unit.BossUnit;
import com.github.cc3002.citricliquid.model.unit.Player;
import com.github.cc3002.citricliquid.model.unit.WildUnit;

import java.util.*;

/**
 * Controller Class that handles the Model and the Visual part of the game.
 *
 * @author Marcelo Jiménez
 */
public class GameController {

    private ArrayList<IPanel> panels;
    private ArrayList<Player> players;
    private int chapter;
    public  Player winner = null;
    private Player turnOwner;
    private PlayerWinsHandler PlayerWinsHandler = new PlayerWinsHandler(this);
    private NormaClearHandler NormaClearHandler = new NormaClearHandler(this);
    private PlayersMeetHandler PlayersMeetHandler = new PlayersMeetHandler(this);
    private PlayerHomeHandler PlayerHomeHandler = new PlayerHomeHandler(this);
    private MultipleNextPanelsHandler MultipleNextPanelsHandler = new MultipleNextPanelsHandler(this);
    private State state;
    private int movesLeft;
    private MainView view;

    /**
     * Creates a New Game Controller Object
     */
    public GameController() {
        super();
        panels = new ArrayList<>();
        players = new ArrayList<>();
        setState(new StartState());
        chapter = 1;

    }

    /**
     * Commands the creation of a new Bonus Panel and adds it to the game
     * @param id the id of the panel
     * @return Bonus Panel
     */
    public IPanel createBonusPanel(int id) {
        IPanel newPanel = new BonusPanel(id);
        addNewPanel(newPanel);
        newPanel.addObserver(PlayersMeetHandler);
        return newPanel;
    }

    /**
     * Commands the creation of a new Boss Panel and adds it to the game
     * @param id the id of the panel
     * @return Boss Panel
     */
    public IPanel createBossPanel(int id) {
        IPanel newPanel = new BossPanel(id);
        addNewPanel(newPanel);
        newPanel.addObserver(PlayersMeetHandler);
        return newPanel;
    }

    /**
     * Commands the creation of a new Drop Panel and adds it to the game
     * @param id the id of the panel
     * @return DropPanel
     */
    public IPanel createDropPanel(int id) {
        IPanel newPanel = new DropPanel(id);
        addNewPanel(newPanel);
        newPanel.addObserver(PlayersMeetHandler);
        return newPanel;
    }

    /**
     * Commands the creation of a new Encounter Pan and adds it to the game
     * @aram id the id of the panel
     * @return EncounterPanel
     */
    public IPanel createEncounterPanel(int id) {
        IPanel newPanel = new EncounterPanel(id);
        addNewPanel(newPanel);
        newPanel.addObserver(PlayersMeetHandler);
        return newPanel;
    }

    /**
     * Commands the creation of a new Home Paneland adds it to the game
     * @param id the id of the panel
     * @return HomePanel
     */
    public HomePanel createHomePanel(int id) {
        HomePanel newPanel = new HomePanel(id);
        addNewPanel(newPanel);
        newPanel.addObserver(PlayersMeetHandler);
        newPanel.addNormaObserver(NormaClearHandler);
        newPanel.addPlayerHomeObserver(PlayerHomeHandler);
        return newPanel;
    }

    /**
     * Commands the creation of a new Neutral Panel and adds it to the game
     * @param id the id of the panel
     * @return NeutralPanel
     */
    public IPanel createNeutralPanel(int id) {
        IPanel newPanel = new NeutralPanel(id);
        addNewPanel(newPanel);
        newPanel.addObserver(PlayersMeetHandler);
        return newPanel;
    }


    /**
     * Commands the creation of a new Player in a specific Panel and adds it to the game and panel
     *
     * @param name      the character's name.
     * @param hitPoints the initial (and max) hit points of the character.
     * @param attack    the base damage the character does.
     * @param defense   the base defense of the character.
     * @param evasion   the base evasion of the character
     * @param panel     current panel of the Player
     * @return Player
     */
    public Player createPlayer(String name, int hitPoints, int attack, int defense, int evasion, IPanel panel) {
        Player player = new Player(name, hitPoints, attack, defense, evasion);
        player.setCurrentPanel(panel);
        panel.addPlayer(player);
        players.add(player);
        setTurnOwner(players.get(0));
        player.addWinObserver(PlayerWinsHandler);
        player.addPanelObserver(MultipleNextPanelsHandler);
        return player;
    }

    /**
     * Commands the creation of a new Wild Unit
     *
     * @param name      the character's name.
     * @param hitPoints the initial (and max) hit points of the character.
     * @param attack    the base damage the character does.
     * @param defense   the base defense of the character.
     * @param evasion   the base evasion of the character
     * @return WildUnit
     */
    public WildUnit createWildUnit(String name, int hitPoints, int attack, int defense, int evasion) {
        return new WildUnit(name, hitPoints, attack, defense, evasion);
    }

    /**
     * Commands the creation of a new Boss Unit
     *
     * @param name      the character's name.
     * @param hitPoints the initial (and max) hit points of the character.
     * @param attack    the base damage the character does.
     * @param defense   the base defense of the character.
     * @param evasion   the base evasion of the character
     * @return BossUnit
     */
    public BossUnit createBossUnit(String name, int hitPoints, int attack, int defense, int evasion) {
        return new BossUnit(name, hitPoints, attack, defense, evasion);
    }

    /**
     * Adds a new next panel to "origin" panel.
     */
    public void setNextPanel(IPanel origin, IPanel target, NPPos position) {
        if (!origin.equals(target)){
            origin.addNextPanel(target);
            origin.setNPPosition(target,position);
        }
    }

    /**
     * get's the next panel of "origin" panel.
     */
    public Set<IPanel> getNextPanel(IPanel origin) {
        return origin.getNextPanels();
    }

    /**
     * Adds a new panel to the game
     */
    public void addNewPanel(IPanel newPanel) {
        panels.add(newPanel);
    }

    /**
     * Returns the list of panels in the game.
     */
    public ArrayList<IPanel> getPanels() {
        return panels;
    }

    public void setBoard(ArrayList<IPanel> board) {
        this.panels = board;
    }

    /**
     * Returns the current panel of the player
     * @param player
     * @return panel
     */
    public IPanel getPlayerPanel(Player player) {
        return player.getCurrentPanel();
    }

    /**
     * Set's the current panel of the player
     * @param player
     */
    public void setPlayerPanel(Player player, IPanel panel) {
                IPanel previousPanel = player.getCurrentPanel();
        previousPanel.removePlayer(player);
        player.setCurrentPanel(panel);
        panel.addPlayer(player);
    }

    /**
     * Returns the owner of the current Turn
     * @return Player
     */
    public Player getTurnOwner() {return this.turnOwner;}

    /**
     * Set's the owner of the current turn
     * @param player
     */
    public void setTurnOwner(Player player) {
        this.turnOwner = player;
    }

    /**
     * Set's the Norma Goal of the current Player
     * @param goal
     */
    public void setCurrPlayerNormaGoal(NormaGoal goal) {
        turnOwner.setNormaGoal(goal);
    }

    /**
     * Set's the HomePanel of the given Player
     * @param player panel
     */
    public void setPlayerHome(Player player, IPanel panel) {
        player.setHome(panel);
    }

    /**
     * Get's the HomePanel of the given Player
     * @param player panel
     */
    public IPanel getPlayerHome(Player player) {
        return player.getHome();
    }

    /**
     * Returns the current Chapter of the game
     * @return int chapter.
     */
    public int getChapter() {
        return this.chapter;
    }

    /**
     * set's the current chapter
     * @param chapter
     */
    public void setChapter(int chapter) {
        this.chapter = chapter;
    }
    /**
     * Ends the turn of the Current Player, changes TurnOwner and checks if there is a new Chapter.
     */
    public void endTurn() {
        int index = players.indexOf(getTurnOwner());
        if(index == players.size()-1) {
            setTurnOwner(players.get(0));
            chapter++;
        }
        else{
            index++;
            setTurnOwner(players.get(index));
        }
    }

    /**
     * Check if the player gathers the requirements to do a Norma Clear
     * @param player
     * @return
     */
    public Boolean normaCheck(Player player){
        NormaGoal NG = player.getNormaGoal();
        int stars =  player.getStars();
        int wins  =  player.getWins();
        int norma =  player.getNormaLevel();

        if (norma == 5){
            if (NG == NormaGoal.STARS && stars>=200){return true;  }
            if (NG == NormaGoal.WINS  && wins >=14 ){return true;  }
            else return false;}
        if (norma == 4){
            if (NG == NormaGoal.STARS && stars>=120){return true;  }
            if (NG == NormaGoal.WINS  && wins >= 9 ){return true;  }
            else return false;}
        if (norma == 3){
            if (NG == NormaGoal.STARS && stars>=70 ){return true;  }
            if (NG == NormaGoal.WINS  && wins >= 5 ){return true;  }
            else return false;}
        if (norma == 2){
            if (NG == NormaGoal.STARS && stars>=30 ){return true;  }
            if (NG == NormaGoal.WINS  && wins >= 2 ){return true;  }
            else return false;}
        if (norma == 1){
            if (NG == NormaGoal.STARS && stars>=10 ){return true;  }
            if (NG == NormaGoal.WINS ){throw new AssertionError("First Norma Goal its always a Star Goal");  }
            else return false;}

        return false;
    }

    /**
     * Increases the Norma Counter of the player and checks if the game is over
     * @param player
     */
    public void normaClear(Player player) {
        if (normaCheck(player)) {
            player.normaClear();
        }
        if (player.getNormaLevel()<1 && player.getNormaLevel()>6){
            throw  new AssertionError("Norma fuera de rango (1-6");
        }
    }

    /**
     * Returns the winner of the game
     * @return
     */
    public Player getWinner() {
        return winner;
    }


    /**
     * Gets's a Player by is's position in the player list.
     * @param i
     * @return Player
     */
    public Player getPlayer(int i) { return players.get(i);}

    /**
     * Returns the list of Players
     * @return
     */
    public ArrayList<Player> getListOfPlayers() {return this.players;}

    /**
     * returns the roll  of the turn owner
     * @return
     */
    private int turnOwnerRoll() {return turnOwner.roll();}

    //------MOVE PLAYER METHODS----------------------------------------------
    /**
     * Makes the player move a random [1,6] number of panels and updates the current panel of the player
     */
    public void movePlayer(){

        if(movesLeft>0){
            movesLeft = movePlayer(movesLeft);
        }
        else{
            int move = turnOwnerRoll();
            movesLeft= movePlayer(move);
        }
            goToPanelEffect();
            applyPanelEffect(this.getPlayerPanel(this.getTurnOwner()));

    }

    public int movePlayer(int move) {
        goToMovePlayer();
        if (move > 0) {
            IPanel currPanel = this.getPlayerPanel(this.getTurnOwner());
            Iterator<IPanel> i = currPanel.getNextPanels().iterator();
            if (currPanel.getNextPanels().size() > 1) {
                this.setPlayerPanel(this.getTurnOwner(), currPanel);
                return move;
            }
            if (currPanel == turnOwner.getHome()){
                return move;
            }
            if (checkOtherPlayer()) {
                return move;
            }
            if (currPanel.getNextPanels().size() == 1) {
                AlertBox.display("nada",move + currPanel.toString() );
                movesLeft = move -1;
                NPPos pos = turnOwner.getCurrentPanel().getNPPosition().get(0);
                view.moveIcon(pos.name());
                this.setPlayerPanel(this.getTurnOwner(), i.next());
                move = movePlayer(move - 1);
            }
        }
        movesLeft = move;
        return move;
    }


    /**
     * Returns the moves lefts by the player
     * @return
     */
    public int getMovesLeft() {
        return  movesLeft;
    }
    /**
     * set's the number of moves lefts
     * @param i
     */
    public void setMovesLeft(int i) {
        movesLeft = i;
    }

    /**
     * Activates the effect of the panel in which the player landed
     * @param playerPanel
     */
    public void applyPanelEffect(IPanel playerPanel) {
        playerPanel.activatedBy(getTurnOwner());
    }

    /**
     * Checks if there is another Player in the current Panel and ask the user if he wants to battle him
     * @return boolean
     */
    public boolean checkOtherPlayer() {
        Player turnOwner = this.getTurnOwner();
        for (Player player: this.getListOfPlayers()) {
            if(player!= turnOwner && this.getPlayerPanel(player)==this.getPlayerPanel(turnOwner)){
                return true;
            }
        }
        return false;
    }
    //--------------------------------OBSERVER METHODS-----------------
    /**
     * Does a NormaCheck and an Norma Clear if necessary
     */
    public void onNormaClear(Player player){ normaClear(player); }

    /**
     * Handles the state transition
     * @param oldValue
     * @param newValue
     */
    public void onMeetPlayer(ArrayList<Player> oldValue, Player newValue) {
        if (this.atMovePlayer()){
            goToIDLEBattle();
            try {
                state.doYourThing();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the Player Home Property Change Notification,
     * @param player
     */
    public void onPlayerHome(Player player) {
        goToIDLEHome();
    }

    /**
     * Handles the player win property change Notification
     * @param winner
     */
    public void onPlayerWins(Player winner) {
        this.winner = winner;
    }

    /**
     * Handles the multiple next panels property change notification
     * @param nextPanels
     */
    public void onMultipleNextPanels(Set<IPanel> nextPanels) {

        goToIDLENextPanel();
        ChoosePanelBox choosy = new ChoosePanelBox(this);
        choosy.display(nextPanels,"Next Panel", "Choose the Next Panel");
    }




    //-----------------------------------STATE CONTROL ---------------
    /**
     * Set's the current state of the controller
     * @param state
     */
    public void setState(final State state) {
        this.state = state;
        state.setController(this);
    }

    /**
     * Debug methods to check the current state of controller.
     * @return boolean
     */
    public boolean atStart(){return state.atStart();}
    public boolean atRecovery(){return state.atRecovery();}
    public boolean atMovePlayer(){return state.atMovePlayer();}
    public boolean atIDLEBattle(){return state.atIDLEBattle();}
    public boolean atIDLEHome(){return state.atIDLEHome();}
    public boolean atIDLENextPanel(){return state.atIDLENextPanel();}
    public boolean atPanelEffect(){return state.atPanelEffect();}
    public boolean atEndTurn(){return state.atEndTurn();}


    /**
     * Activates the actions of the current state
     */

    public void stateDoYourThing() {
        state.doYourThing();
    }


    /**
     * Attempts to go to IDLENextPanel
     */
    private void goToIDLENextPanel() {
        try{
            state.goToIDLENextPanel();
        } catch (InvalidTransitionException e){
            e.printStackTrace();
        }
    }
    /**
     * Attempts to go to IDLEHome
     */
    private void goToIDLEHome() {
        try{
            state.goToIDLEHome();
        } catch (InvalidTransitionException e) {
            e.printStackTrace();

        }
    }
    /**
     * Attempts to go to IDLEBattle
     */
    private void goToIDLEBattle() {
        try{
            state.goToIDLEBattle();
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        }
    }
    /**
     * Attempts to go to Move Player state
     */
    private void goToMovePlayer() {
        try{
            state.goToMovePlayer();
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to go to Move Player state
     */
    private void goToPanelEffect() {
        try{
            state.goToPanelEffect();
        } catch (InvalidTransitionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that will be called by the view, begins the turn of the player
     */
    public void beginTurn() {
        try{
            state.doYourThing();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setView(MainView view ){
        this.view = view;
    }

    public MainView getView() {
        return this.view;
    }
}






