package com.dusk.calculator;

/**
 * Created by Yannis on 11/27/2016.
 */

public class Parse {
    private String Equation;
    private int StopPoint =0;
    private int index = 0;
    private char indexValue;
    private Stack<Double> NumberStack = new Stack<Double>();
    private Stack<Operator> OperatorStack = new Stack<Operator>();

    public  Parse(String input){
        Equation = input;
        removeBlackSpace();
        FindNextOperator();
        //return NumberStack.pop();
    }

    public String calculate(){
        //return Equation;
        return NumberStack.pop().toString();
    }
    private void FindNextOperator(){
        //int index=0;

        do{
            indexValue = Equation.charAt(index);
            if(!(Character.isDigit(indexValue))){
                if(indexValue == '('){
                    FoundOpenBrace();
                }else if(indexValue == ')' ){
                    FoundCloseBrace();
                }else{
                    FoundOperator();
                }
            }else {
                if(index == Equation.length()-1 && !OperatorStack.isEmpty()){
                    String number = Equation.substring(StopPoint, index+1);
                    NumberStack.push(Double.parseDouble(number));
                    while(!OperatorStack.isEmpty()){
                        double ndNumber = NumberStack.pop();
                        double stNumber = NumberStack.pop();
                        NumberStack.push(OperatorStack.pop().proformOperation(stNumber,ndNumber));
                    }

                    if(OperatorStack.isEmpty())
                        return;
                }
            }

            index++;
        }while(index < Equation.length());
    }

    private void FoundOpenBrace(){
        //for (5 + 7) or 7 + (4 - 2)
        // just push in ')'
        if(index == 0 || !Character.isDigit(Equation.charAt(index-1))){
            OperatorStack.push(new Operator(Character.toString(indexValue)));
            //for 20 (78 + 9)
        }else if(Character.isDigit(Equation.charAt(index-1))){
            String number = Equation.substring(StopPoint, index);
            NumberStack.push(Double.parseDouble(number));
            OperatorStack.push(new Operator("*"));
            OperatorStack.push(new Operator(Character.toString(indexValue)));
        }
        StopPoint = index+1;
    }

    private void FoundCloseBrace(){
        String number = Equation.substring(StopPoint, index);
        // double stNumber = NumberStack.pop();
        double ndNumber = Double.parseDouble(number);
        Operator Operation = OperatorStack.pop();

        while(!Operation.Equals("(")){
            double stNumber = NumberStack.pop();
            ndNumber = Operation.proformOperation(stNumber,ndNumber);
            Operation = OperatorStack.pop();
        }
        NumberStack.push(ndNumber);
        StopPoint = index+1;
    }

    public void FoundOperator(){
        Operator Operation = new Operator(Character.toString(indexValue));
        String number = Equation.substring(StopPoint, index);
        System.out.println(indexValue);
        if(OperatorStack.isEmpty() || Operation.HigherPriority(OperatorStack.top())){
            if(Character.isDigit(Equation.charAt(index-1))){
                OperatorStack.push(Operation);
                NumberStack.push(Double.parseDouble(number));
            }else{
                OperatorStack.push(Operation);
            }

        }else{

            double stNumber = NumberStack.pop();
            double ndNumber = Double.parseDouble(number);
            Operator preOperation = OperatorStack.pop();

            NumberStack.push(preOperation.proformOperation(stNumber,ndNumber));
            OperatorStack.push(Operation);
        }
        StopPoint = index+1;
    }

    private void removeBlackSpace(){
        int index=0;

        do{
            if(Equation.charAt(index) == ' '){
                Equation = Equation.substring(0,index) + Equation.substring(index+1,Equation.length());
                index--;
            }
            index++;
        }while(index != Equation.length());
    }
}
