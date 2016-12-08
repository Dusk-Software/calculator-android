package com.dusk.calculator;

/**
 * Created by Yannis on 12/8/2016.
 */

public class Function extends Operator{
    private int noOfOperan;
    private double[] Operans;
    private String name;
    private int size=0;

    public Function(String input){
        if(input.equalsIgnoreCase("sin")){
            noOfOperan = 1;
            Operans = new double[noOfOperan];
        }else if(input.equalsIgnoreCase("cos")){
            noOfOperan = 1;
            Operans = new double[noOfOperan];
        }else if(input.equalsIgnoreCase("tan")){
            noOfOperan = 1;
            Operans = new double[noOfOperan];
        }else if(input.equalsIgnoreCase("sinh")){
            noOfOperan = 1;
            Operans = new double[noOfOperan];
        }else if(input.equalsIgnoreCase("cosh")){
            noOfOperan = 1;
            Operans = new double[noOfOperan];
        }else if(input.equalsIgnoreCase("tanh")){
            noOfOperan = 1;
            Operans = new double[noOfOperan];
        }else if(input.equalsIgnoreCase("log")){
            noOfOperan = 1;
            Operans = new double[noOfOperan];
        }
        name = input;
    }


    public void addOperan(double input){
        Operans[size] = input;
        size++;
    }

    public double performOperation(){
        if(name.equalsIgnoreCase("sin")){
            return Math.sin(Operans[0]);
        }else if(name.equalsIgnoreCase("cos")){
            return Math.cos(Operans[0]);
        }else if(name.equalsIgnoreCase("tan")){
            return Math.tan(Operans[0]);
        }else if(name.equalsIgnoreCase("sinh")){
            return Math.sinh(Operans[0]);
        }else if(name.equalsIgnoreCase("cosh")){
            return Math.cosh(Operans[0]);
        }else if(name.equalsIgnoreCase("tanh")){
            return Math.tanh(Operans[0]);
        }else if(name.equalsIgnoreCase("log")){
            return Math.log(Operans[0]);
        }
        size--;
        return 0.0;
    }
}
