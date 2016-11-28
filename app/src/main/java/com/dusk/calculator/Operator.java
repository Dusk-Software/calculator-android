package com.dusk.calculator;

/**
 * Created by Yannis on 11/27/2016.
 */

public class Operator {
    private String Symbol;
    private int priotity;
    private int numberOfOperans = 2;
    private double value1, value2;


    public Operator (String input){
        Symbol = input;

        if(Symbol.equals("+")){
            priotity = 1;
        } else if (Symbol.equals("-")) {
            priotity = 1;
        }else if(Symbol.equals("*")){
            priotity = 2;
        }else if(Symbol.equals("/")){
            priotity = 3;
        } else if (Symbol.equals("^")) {
            priotity = 4;
        } else if (Symbol.equals("% of")){
            priotity = 5;
        }

    }

    public boolean HigherPriority(Operator input){
        if(priotity > input.priotity){
            return true;
        }
        return false;
    }

    public boolean Equals(String input){
        if(Symbol.equals(input))
            return true;

        return false;
    }

    public double proformOperation(double stNumber, double ndNumber){
        if(Symbol.equals("+")){
            return stNumber + ndNumber;
        } else if (Symbol.equals("-")) {
            return stNumber - ndNumber;
        }else if(Symbol.equals("*")){
            return stNumber * ndNumber;
        }else if(Symbol.equals("/")){
            return stNumber / ndNumber;
        } else if (Symbol.equals("^")) {
            return Math.pow(stNumber, ndNumber);
        }else if (Symbol.equals("% of")){
            return  (stNumber /100) * ndNumber;
        }
        return 0;
    }
}
