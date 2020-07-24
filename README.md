<!-- 1.0.3-b1 -->
# 99.7% Citric Liquid by Marcelo Jimenez

 CC3002's *99.7% Citric Juice* Project.

The project consists in creating a (simplified) clone of the game **100% Orange Juice**
developed by [Orange_Juice](http://daidai.moo.jp) and distributed by 
[Fruitbat Factory](https://fruitbatfactory.com).

The project follows a MCV logic, which stands for Model, Control and Visual.

In the folder src/main/java/com.github.cc3002/citricliquid you will find the packages containing the Controller classes 
and the model classes. The visual part is not yet implemented.

###The Model Classes

These are the basic structure of the game. They are divided in two packages: **board** and **unit**.
#### The Board
The board package contains all the classes that represent the different panels in the game.
The IPanel interface declares all the methods common to the panel classes, every panel class inherits from the 
*AbstractPanel* class which implements the *IPanel* interface.

Every panel has an id given at the moment of creation to differentiate them from the rest.

Every panel has a record of how many players are standing in them and has methods to add and remove them.

Every panel has a record of the panels adjacent to them and has methods to add and remove them.

Almost all panels have a property that is activated when a player lands on them or by the effect of a card, form more
 information about this topic read the *[CC3002] Proyecto - 2020O - Bergel, Graboloza, Slater & Vallejos (99.7% Citric Liquid).pdf*
 (it's in spanish though hope that you are bilingual or you are screwed .-.)
 
#### The Units
The unit package contains all the classes that represent the different units in the game.
there are three kind:

**Player**
It's the unit used by the client to play the game.

**Wild Unit**
It's a weak unit that can be confronted by a Player unit in an Encounter Panel.
In the battle there is a number of stars at stake, and each win brings the player closer to victory.

**Boss Unit**
It's a strong unit that appears once in the game and can be confronted by a player in a BossPanel.


These three classes implement the *IUnit* interface, where you will find all the methods that they share in common.
The Wild and Boss unit inherit from the *AbstractUnit* class.

Each one of these classes has a bunch of methods that I will not go in detail because I am delivering this homework 
really really late and actually I haven't even finished it yet so sorry but if you wanna know more about the code you 
will just have to figure it out by yourself.

###### Norma Goal

Norma goal its a small enum class that holds the two possible settings to do a norma
clear, that it's basically a Level Up. If you get to Norma 6 you win the game. The two possible
ways to do a Norma Clear is gathering stars or killing other units (collecting wins). That's it no big deal

###The Controller Classes

The controller classes control the flow of the game. They use the model classes and methods in order to apply the 
rules and logic of the game.

Inside the controller package there are a GameController class, a HomePanelHandler and another package of States.

#### GameController Class

it's the main controller class, has all the methods to enforce the rules and actions required by the game.
It also implement's methods used by the observers of the model , and can take actions whenever it receives an event from 
them.

### States Classes

The state classes are used to control the flow of the game, each one of them can call an specific amount of 
next states.

They have a doYourThing() method in which the state executes some methods from the GameController class in order to 
make the game happen.

### Handlres

These classes are the observers of the model and can call a GameController method whenever they receive a message from 
the model.

Currently there is just one handler, but in the future, when i have finally the time to finish this never ending torture
there will be more.
 