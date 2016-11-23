package com.dusk.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView numericInput;
    Button pressedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numericInput = (TextView) findViewById(R.id.textInput);
    }

    public void NumericButtonOnClickListener(View v) {
        pressedButton = (Button) findViewById(v.getId());
        numericInput.append(pressedButton.getText());
    }


}

