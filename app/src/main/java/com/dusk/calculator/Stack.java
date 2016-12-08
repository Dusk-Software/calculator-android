package com.dusk.calculator;

import java.util.ArrayList;

/**
 * Created by Yannis on 11/26/2016.
 */

public class Stack<T> {

    private ArrayList<T> stack = new ArrayList<T>();
    private int size;

    public Stack(){
        size = 0;

    }

    public void push(T input){
        stack.add(input);
        size++;
    }

    public T pop(){
        T output = stack.get(size - 1);
        stack.remove(size -1);
        size--;
        return output;
    }

    public T top(){
        return stack.get(size-1);
    }

    public boolean isEmpty(){
        if(size == 0)
            return true;

        return false;
    }
    public int getsize(){
        return size;
    }
}
