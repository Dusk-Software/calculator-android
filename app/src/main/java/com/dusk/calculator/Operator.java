package com.dusk.calculator;

/**
 * Created by Yannis on 11/27/2016.
 */

public class Operator {
    private String symbol;
    private int priority;
    private int numberOfOperands = 2;


    public Operator (String input){
        symbol = input;

        if(symbol.equals("+")){
            priority = 1;
        } else if (symbol.equals("-")) {
            priority = 2;
        }else if(symbol.equals("*")){
            priority = 2;
        }else if(symbol.equals("/")){
            priority = 3;
        } else if (symbol.equals("^")) {
            priority = 4;
        } else if (symbol.equals("% of")){
            priority = 5;
        }

    }

    public boolean isHigherPriority(Operator input){
        if(priority > input.priority){
            return true;
        }
        return false;
    }

    public boolean Equals(String input){
        if(symbol.equals(input))
            return true;

        return false;
    }

    public double performOperation(double stNumber, double ndNumber){
        if(symbol.equals("+")){
            return stNumber + ndNumber;
        } else if (symbol.equals("-")) {
            return stNumber - ndNumber;
        }else if(symbol.equals("*")){
            return stNumber * ndNumber;
        }else if(symbol.equals("/")){
            return stNumber / ndNumber;
        } else if (symbol.equals("^")) {
            return Math.pow(stNumber, ndNumber);
        }else if (symbol.equals("% of")){
            return  (stNumber /100) * ndNumber;
        }
        return 0;
    }
}
