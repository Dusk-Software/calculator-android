package com.dusk.calculator;

/**
 * Created by Yannis on 11/27/2016.
 */

public class Parse {
    protected String equation;
    protected int stop_point = 0;
    protected int index = 0;
    protected char index_value;
    private String name;
    protected boolean Computed_Function = false;
    protected Function Func;
    protected int check = 0;
    protected Stack<Double> number_stack = new Stack<Double>();
    protected Stack<Operator> operator_stack = new Stack<Operator>();

    public  Parse(String input){
        equation = input;
        PreParse();
    }

    public Parse(){

    }

    public String calculate(){
        if(isValidEquation()){
            ScanIndex();
            return number_stack.pop().toString();
        }
        return "Invalid Entry";
    }

    protected boolean isValidEquation(){
        if(InvalidBrace())
            return false;
        if(InvalidOperation())
            return false;
        if(InvalidSyntax())
            return false;
        return true;
    }

    protected boolean InvalidBrace(){
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

    protected boolean InvalidOperation(){
        char NextChar;

        for(int x = 0; x < equation.length()-1;x++){

            if(!(isDigit(equation.charAt(x)) || equation.charAt(x)== '.' || equation.charAt(x)==')')){
                NextChar = equation.charAt(x+1);
                //protection against (*6) & (9 + / 4) etc
                if(NextChar == '*' && NextChar == '/' && NextChar ==')' && !(isDigit(NextChar))){
                    System.out.println("invalid Operation on " + equation.charAt(x) + NextChar);
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean InvalidSyntax(){
        for(index = 0; index < equation.length()-1;index++){
            if(!(isDigit(equation.charAt(index)) || equation.charAt(index)== '.'
                    || equation.charAt(index)==')' || equation.charAt(index)== '(' || equation.charAt(index) == '/'
                    || equation.charAt(index)== '*' || equation.charAt(index)== '+' || equation.charAt(index)== '-'
                    || equation.charAt(index)== '^' || equation.charAt(index)=='%'
                    || (isFunction() && equation.charAt(index)== '('))){
                System.out.println("invalid Syntax on " + equation.charAt(index));
                index = 0;
                return true;
            }
        }
        index = 0;
        return false;
    }

    protected void ScanIndex(){

        do{
            index_value = equation.charAt(index);
            if(!(isDigit(index_value) || index_value =='.')){
                if(isFunction()){
                    FoundFunction();
                }else if(index_value == '('){
                    FoundOpenBrace();
                }else if(index_value == ')' ){
                    FoundCloseBrace();
                }else{
                    if(index != stop_point || (index > 0 && equation.charAt(index-1) == ')')
                            || Computed_Function || equation.charAt(index+1) == '('){
                        if(Computed_Function){

                            Computed_Function = false;
                            System.out.println("OK found; "+ index_value);
                        }
                        System.out.println("index: "+ index +" " +index_value);
                        FoundOperator();
                    }

                }
            }else{
                //System.out.println(index + " is " + index_value);
                if(index == equation.length()-1 && !operator_stack.isEmpty()){
                    String number = equation.substring(stop_point, index+1);
                    number_stack.push(parseDouble(number));
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
        if(!operator_stack.isEmpty()){
            while (!operator_stack.isEmpty()){
                double ndNumber = number_stack.pop();
                double stNumber;
                if(number_stack.getsize() == 0){
                    stNumber = 0;
                }else{
                    stNumber = number_stack.pop();
                }
                number_stack.push(operator_stack.pop().performOperation(stNumber,ndNumber));
            }
        }else if(stop_point ==0){
            String number = equation.substring(stop_point, index);
            number_stack.push(parseDouble(number));
        }

        System.out.println(number_stack.getsize());
    }

    protected boolean isFunction(){
        if(equation.length() - index <=3){
            return false;
        }
        if(equation.substring(index, index+3).equalsIgnoreCase("sin")){
            name = equation.substring(index,index+3);
            check = index;
            index+=3;
            return true;
        }else if(equation.substring(index, index+3).equalsIgnoreCase("cos")){
            name = equation.substring(index,index+3);
            check = index;
            index+=3;
            return true;
        }else if(equation.substring(index, index+3).equalsIgnoreCase("tan")){
            name = equation.substring(index,index+3);
            check = index;
            index+=3;
            return true;
        }else if(equation.substring(index, index+4).equalsIgnoreCase("sinh")){
            name = equation.substring(index,index+4);
            check = index;
            index+=4;
            return true;
        }else if(equation.substring(index, index+4).equalsIgnoreCase("cosh")){
            name = equation.substring(index,index+4);
            check = index;
            index+=4;
            return true;
        }else if(equation.substring(index, index+4).equalsIgnoreCase("tanh")){
            name = equation.substring(index,index+4);
            check = index;
            index+=4;
            return true;
        }else if(equation.substring(index, index+3).equalsIgnoreCase("log")){
            name = equation.substring(index,index+3);
            check = index;
            index+=3;
            return true;
        }else if(equation.substring(index, index+2).equalsIgnoreCase("ln")){
            name = equation.substring(index,index+2);
            check = index;
            index+=2;
            return true;
        }
        return false;
    }

    protected void FoundFunction(){

        if(check > 0 && isDigit(equation.charAt(check-1))){
            String number = equation.substring(stop_point, check);
            number_stack.push(parseDouble(number));
            operator_stack.push(new Operator("*"));
            //operator_stack.push(new Operator(Character.toString(index_value)));
        }else if(check > 0 && equation.charAt(check-1) == '-'){
            String number = equation.substring(stop_point, check);
            number_stack.push(-1.0);
            operator_stack.push(new Operator("*"));
            //operator_stack.push(new Operator(Character.toString(index_value)));
        }

        Func = new Function(name);
        if(equation.charAt(index) == '('){
            index++;
            int start = index;

            FindMatchingCloseBrace();
            String sub = equation.substring(start, index);

            Func.addOperan(parseDouble(new Parse(sub).calculate()));
            System.out.println("Here" + index + " in "+ equation.length());
            number_stack.push(Func.performOperation());
            stop_point = index;
            Computed_Function = true;
            //index--;
        }else {
            //Function_is_waiting = true;
        }

    }

    protected void FoundOpenBrace(){
        //for (5 + 7) or 7 + (4 - 2)
        // just push in ')'
        if(index == 0 || !isDigit(equation.charAt(index-1))){
            operator_stack.push(new Operator(Character.toString(index_value)));
            //for 20 (78 + 9)
        }else if(isDigit(equation.charAt(index-1))){
            String number = equation.substring(stop_point, index);
            number_stack.push(parseDouble(number));
            operator_stack.push(new Operator("*"));
            operator_stack.push(new Operator(Character.toString(index_value)));
        }
        stop_point = index+1;
    }

    protected void FoundCloseBrace(){
        String number = equation.substring(stop_point, index);
        double ndNumber;
        // double stNumber = number_stack.pop();
        if(!isDigit(equation.charAt(index-1))){
            ndNumber = number_stack.pop();
        }else
            ndNumber = parseDouble(number);

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


        String number = equation.substring(stop_point, index);
        if(index_value == '-' && equation.charAt(index+1) != '('){
            index_value = '+';
            stop_point = index;
        }else
            stop_point = index+1;

        Operator Operation = new Operator(Character.toString(index_value));

        // System.out.println(index_value);
        if(operator_stack.isEmpty() || Operation.isHigherPriority(operator_stack.top()) ){
            if(index > 0 && (isDigit(equation.charAt(index-1)) || equation.charAt(index-1)=='π')){
                operator_stack.push(Operation);
                number_stack.push(parseDouble(number));
                //System.out.println(number_stack.top().toString());
            }else
                operator_stack.push(Operation);
        }else if(index > 0 && equation.charAt(index-1) == ')'){
            double stNumber;
            double ndNumber;

            while(!operator_stack.isEmpty() && operator_stack.top().isHigherPriority(Operation)){
                ndNumber = number_stack.pop();

                if(number_stack.getsize() == 0){
                    stNumber = 0;
                }else{
                    stNumber = number_stack.pop();
                }
                Operator preOperation = operator_stack.pop();

                number_stack.push(preOperation.performOperation(stNumber,ndNumber));
            }
            operator_stack.push(Operation);
        }else{
            double stNumber;
            if(number_stack.getsize() == 0){
                stNumber = 0;
            }else{
                stNumber = number_stack.pop();
            }

            double ndNumber = parseDouble(number);
            Operator preOperation = operator_stack.pop();

            number_stack.push(preOperation.performOperation(stNumber,ndNumber));
            while(!operator_stack.isEmpty() && operator_stack.top().isHigherPriority(Operation)){
                ndNumber = number_stack.pop();
                stNumber = number_stack.pop();
                preOperation = operator_stack.pop();

                number_stack.push(preOperation.performOperation(stNumber,ndNumber));
            }
            operator_stack.push(Operation);
        }
        // stop_point = index+1;
    }

    protected void FindMatchingCloseBrace(){
        int brace = 1;
        while (brace >=1){
            if(equation.charAt(index) == '(')
                brace++;
            if(equation.charAt(index) == ')')
                brace--;
            index++;
        }
        index--;
    }

    protected void PreParse(){
        int index=0;
        do{
            if(equation.charAt(index) == ' '){
                equation = equation.substring(0,index) + equation.substring(index+1,equation.length());
                index--;
            }
            index++;
        }while(index != equation.length());
        index = 0;
        do{
            if(index+1 != equation.length() && equation.charAt(index)=='+' && (equation.charAt(index+1)=='-' || equation.charAt(index+1)=='+')){
                equation = equation.substring(0,index) + equation.substring(index+1,equation.length());
                index--;
            }if(index+1 != equation.length() && equation.charAt(index)=='-' && equation.charAt(index+1)=='-'){
                equation = equation.substring(0,index) +"+" +equation.substring(index+2,equation.length());
                index--;
            }if(index+1 != equation.length() && equation.charAt(index)=='-'&& equation.charAt(index+1)=='+'){
                equation = equation.substring(0,index+1) + equation.substring(index+2,equation.length());
                index--;
            }if(equation.charAt(index)=='%' && equation.substring(index+1, index+3).equalsIgnoreCase("of")){
                equation = equation.substring(0,index+1) + equation.substring(index+3,equation.length());
            }
            index++;

        }while(index != equation.length());
    }

    protected double parseDouble(String input){
        if (input.equals("π")){
            return Math.PI;
        }
        if(input.equals("e")){
            return Math.E;
        }
        return Double.parseDouble(input);
    }

    protected boolean isDigit(char x){
        if(x == 'π'){
            return true;
        }
        if(x == 'e'){
            return true;
        }
        return Character.isDigit(x);
    }
}

