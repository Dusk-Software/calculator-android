package com.dusk.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

}

