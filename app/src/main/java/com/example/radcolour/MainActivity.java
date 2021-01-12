package com.example.radcolour;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String address;
    public static String EMAIL_ADDRESS = "com.example.radcolour.EMAIL_ADDRESS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText email = findViewById(R.id.email_entry);
        TextView validEmail = findViewById(R.id.valid_email);
        Button submit = findViewById(R.id.pick_colour);

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean hasFocus){
               if(!hasFocus){
                   /*
                   On focus change hide the keyboard and check the
                   text field for a valid address.
                    */
                   hideKeyboard(view);
                   if(isValidEmail(address=email.getText().toString())){
                       validEmail.setText(R.string.valid_email);
                       submit.setVisibility(View.VISIBLE);
                   }else{
                       //If not valid, hide the button.
                       validEmail.setText(R.string.not_valid_email);
                       submit.setVisibility(View.INVISIBLE);
                   }
               }
           }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                /*
                Edit text change listener checks the text field for a valid
                email address anytime the content is changed.
                 */
               if(isValidEmail(address=email.getText().toString())){
                   validEmail.setText(R.string.valid_email);
                   submit.setVisibility(View.VISIBLE);
               }else{
                   //If not valid, hide the button.
                   validEmail.setText(R.string.not_valid_email);
                   submit.setVisibility(View.INVISIBLE);
               }

            }
        });



    }

    public boolean isValidEmail(String email){
        //Returns true if a valid email address string.
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    public void hideKeyboard(View view){
        //Hide the keyboard.
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }


    public void onSubmit(View view){
        Intent intent = new Intent(this, ColourPicker.class);
        intent.putExtra(EMAIL_ADDRESS, address);

        startActivity(intent);

    }






}