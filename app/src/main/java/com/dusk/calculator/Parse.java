package com.dusk.calculator;

/**
 * Created by Yannis on 11/27/2016.
 */

public class Parse {
    private String equation;
    private int stop_point = 0;
    private int index = 0;
    private char index_value;
    private Stack<Double> number_stack = new Stack<Double>();
    private Stack<Operator> operator_stack = new Stack<Operator>();

    public  Parse(String input){
        equation = input;
        removeBlackSpace();
    }

    public String calculate(){
        if(isValidEquation()){
            FindNextOperator();
            return number_stack.pop().toString();
        }
        return "Invalid Entry";
    }

    private boolean isValidEquation(){
        if(InvalidBrace())
            return false;
        if(InvalidOperation())
            return false;
        if(InvalidSyntax())
            return false;
        return true;
    }

    private boolean InvalidBrace(){
        int brace = 0;
        for(int x = 0; x < equation.length();x++){
            if(equation.charAt(x) == '(')
                brace++;
            if(equation.charAt(x) == ')')
                brace--;
            if(brace <0)
                return true;
        }
        if(brace !=0)
            return true;

        return false;
    }

    private boolean InvalidOperation(){
        char NextChar;

        for(int x = 0; x < equation.length()-1;x++){

            if(!(Character.isDigit(equation.charAt(x)) || equation.charAt(x)== '.' || equation.charAt(x)==')' ||equation.charAt(x)=='π')){
                NextChar = equation.charAt(x+1);
                //protection against (*6) & (9 + / 4) etc
                if(!(Character.isDigit(NextChar) || NextChar == '+' || NextChar == '-' || NextChar =='(')){
                    System.out.println("invalid Operation on " + equation.charAt(x) + NextChar);
                    return true;
                }
            }



        }

        return false;
    }

    private boolean InvalidSyntax(){
        for(int x = 0; x < equation.length()-1;x++){
            if(!(Character.isDigit(equation.charAt(x)) || equation.charAt(x)== '.'
                    || equation.charAt(x)==')' || equation.charAt(x)== '(' || equation.charAt(x) == '/'
                    || equation.charAt(x)== '*' || equation.charAt(x)== '+' || equation.charAt(x)== '-'
                    || equation.charAt(x)== '^')){
                return true;
            }
        }
        return false;
    }

    private void FindNextOperator(){

        do{
            index_value = equation.charAt(index);
            if(!(Character.isDigit(index_value) || index_value =='.')){
                if(index_value == '('){
                    FoundOpenBrace();
                }else if(index_value == ')' ){
                    FoundCloseBrace();
                }else{
                    if(index != stop_point || equation.charAt(index-1) == ')')
                        FoundOperator();
                }
            }else {
                if(index == equation.length()-1 && !operator_stack.isEmpty()){
                    String number = equation.substring(stop_point, index+1);
                    number_stack.push(Double.parseDouble(number));
                    while(!operator_stack.isEmpty()){
                        double ndNumber = number_stack.pop();
                        double stNumber = number_stack.pop();
                        number_stack.push(operator_stack.pop().performOperation(stNumber,ndNumber));
                    }

                    if(operator_stack.isEmpty())
                        return;
                }
            }

            index++;
        }while(index < equation.length());
    }

    private void FoundOpenBrace(){
        //for (5 + 7) or 7 + (4 - 2)
        // just push in ')'
        if(index == 0 || !Character.isDigit(equation.charAt(index-1))){
            operator_stack.push(new Operator(Character.toString(index_value)));
            //for 20 (78 + 9)
        }else if(Character.isDigit(equation.charAt(index-1))){
            String number = equation.substring(stop_point, index);
            number_stack.push(Double.parseDouble(number));
            operator_stack.push(new Operator("*"));
            operator_stack.push(new Operator(Character.toString(index_value)));
        }
        stop_point = index+1;
    }

    private void FoundCloseBrace(){
        String number = equation.substring(stop_point, index);
        double ndNumber;
        // double stNumber = number_stack.pop();
        if(!Character.isDigit(equation.charAt(index-1))){
            ndNumber = number_stack.pop();
        }else
            ndNumber = Double.parseDouble(number);

        Operator Operation = operator_stack.pop();

        while(!Operation.Equals("(")){
            double stNumber = number_stack.pop();
            ndNumber = Operation.performOperation(stNumber,ndNumber);
            Operation = operator_stack.pop();
        }
        number_stack.push(ndNumber);
        stop_point = index+1;
    }

    public void FoundOperator(){
        Operator Operation = new Operator(Character.toString(index_value));
        String number = equation.substring(stop_point, index);
        System.out.println(index_value);
        if(operator_stack.isEmpty() || Operation.isHigherPriority(operator_stack.top())){
            if(Character.isDigit(equation.charAt(index-1))){
                operator_stack.push(Operation);
                number_stack.push(Double.parseDouble(number));
            }else{
                if(equation.charAt(index-1)=='π') {
                    number_stack.push(Math.PI);
                }
                operator_stack.push(Operation);
            }

        }else{

            double stNumber = number_stack.pop();
            double ndNumber = Double.parseDouble(number);
            Operator preOperation = operator_stack.pop();

            number_stack.push(preOperation.performOperation(stNumber,ndNumber));
            operator_stack.push(Operation);
        }
        stop_point = index+1;
    }

    private void removeBlackSpace(){
        int index=0;

        do{
            if(equation.charAt(index) == ' '){
                equation = equation.substring(0,index) + equation.substring(index+1,equation.length());
                index--;
            }
            index++;
        }while(index != equation.length());
    }
}
