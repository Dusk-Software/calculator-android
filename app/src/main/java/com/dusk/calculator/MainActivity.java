package com.dusk.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    EditText numericInput;
    TextView Equation;
    Button pressedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numericInput = (EditText) findViewById(R.id.editText2);
        Equation = (TextView) findViewById(R.id.textView);
    }
    public void NumericButtonOnClickListener(View v) {
        pressedButton = (Button) findViewById(v.getId());
        numericInput.append(pressedButton.getText());
    }

    public void OperationButtonOnClickListener(View v){
        pressedButton = (Button) findViewById(v.getId());
        Equation.append(numericInput.getText());
        Equation.append(" ");
        Equation.append(pressedButton.getText());
        numericInput.setText(" ");
    }

    public void ANSButtonOnClickListener(View v){
        Equation.append(numericInput.getText());
        Parse test = new Parse(Equation.getText().toString());
        Equation.setText(test.calculate());
    }

    public void ACButtonOnClickListener(View v){
        Equation.setText("");
    }

    public void DELOnClickListener(View v){
        String NewInput = numericInput.getText().toString();
        if(NewInput.length() >0){
            NewInput = NewInput.subSequence(0, NewInput.length()-1).toString();
            numericInput.setText(NewInput);
        }
    }
}

