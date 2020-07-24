package com.github.cc3002.citricliquid.controller.States;

/**
 * Invalid Transition Class is thrown whenever there is an attempt to go from a state to another that can't be reached
 * @author Marcelo Jimenez
 */
public class InvalidTransitionException extends Exception {
    public InvalidTransitionException(String message){
        super(message);
    }

}
