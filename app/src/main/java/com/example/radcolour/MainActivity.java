package com.example.radcolour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email = (EditText)findViewById(R.id.email_entry);
        TextView validEmail = (TextView)findViewById(R.id.valid_email);
        Button submit = (Button) findViewById(R.id.pick_colour);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener(){
           @Override
           public void onFocusChange(View view, boolean hasFocus){
               if(!hasFocus){
                   hideKeyboard(view);
                   if(isValidEmail(email.getText().toString())){
                       validEmail.setText(R.string.valid_email);
                       submit.setVisibility(View.VISIBLE);
                   }else{
                       validEmail.setText(R.string.not_valid_email);
                       submit.setVisibility(View.INVISIBLE);
                   }
               }
           }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               if(isValidEmail(email.getText().toString())){
                   validEmail.setText(R.string.valid_email);
                   submit.setVisibility(View.VISIBLE);
               }else{
                   validEmail.setText(R.string.not_valid_email);
                   submit.setVisibility(View.INVISIBLE);
               }

            }
        });



    }

    public boolean isValidEmail(String email){

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


    public void onSubmit(View view){

    }






}