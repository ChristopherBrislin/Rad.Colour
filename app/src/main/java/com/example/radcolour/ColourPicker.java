package com.example.radcolour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ColourPicker extends AppCompatActivity {

    private String emailAddress;
    private int codeType = 0;

    final int Hex = 1;
    final int RGB = 0;
    final int HSV = 2;

    int red ;
    int green ;
    int blue ;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colour_picker);

        //Access all the controls

        SeekBar redSlider = findViewById(R.id.red_slider);
        SeekBar greenSlider = findViewById(R.id.green_slider);
        SeekBar blueSlider = findViewById(R.id.blue_slider);


        Spinner codeSelector = findViewById(R.id.code_type);

        Intent intent = getIntent();
        emailAddress = intent.getStringExtra(MainActivity.EMAIL_ADDRESS);



        //Set a random initial colour, adjust the sliders accordingly an update the display.
        red = (int) (Math.random()*255);
        green = (int) (Math.random()*255);
        blue = (int) (Math.random()*255);


        redSlider.setProgress(red);
        blueSlider.setProgress(blue);
        greenSlider.setProgress(green);
        updateColour(codeType);

        //Item selected listener for the code type spinner
        codeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Get the index of the selected item and update the code value on selection
                codeType = codeSelector.getSelectedItemPosition();
                updateColour(codeType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

        //Slider listeners: on any change to the progress bar, the values and colour are updated

        redSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = redSlider.getProgress();
                green = greenSlider.getProgress();
                blue = blueSlider.getProgress();
                updateColour(codeType);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        greenSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = redSlider.getProgress();
                green = greenSlider.getProgress();
                blue = blueSlider.getProgress();
                updateColour(codeType);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blueSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = redSlider.getProgress();
                green = greenSlider.getProgress();
                blue = blueSlider.getProgress();
                updateColour(codeType);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void addToClipboard(View view){

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getResources().getString(R.string.clip_label), returnColour()[codeType]);
        clipboard.setPrimaryClip(clip);

        Toast toast = Toast.makeText(this, R.string.toast_success, Toast.LENGTH_SHORT);

        toast.show();
    }

    public void sendEmail(View view){
        //Construct the strings locally for readability
        String rgb = returnColour()[RGB];
        String hex = returnColour()[Hex];
        String hsv = returnColour()[HSV];

        //Main email body String constructed using format %s
        String body = String.format(getResources().getString(R.string.email_body), rgb, hex, hsv);
        String subject = getResources().getString(R.string.email_subject);

        //Construct email from URI for maximum client compatibility:

        /********************************
         * Author: cketti
         * Date: Jan 8, 2016
         * Title: Android: Sending Email using Intents
         * Code Version: 1.0
         * Source: https://cketti.de/2016/01/08/sending-email-using-intents/
         ********************************/

        String mailto = "mailto:" + emailAddress +
                "?cc=" +
                "&subject=" + Uri.encode(subject) +
                "&body=" + Uri.encode(body);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(mailto));






        try{
            startActivity(emailIntent);
        }catch(android.content.ActivityNotFoundException ex){
            Toast toast = Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT);

            toast.show();
        }


    }

    public String[] returnColour(){

        String[] colourCodes = new String[3];

        float[] hsv = new float[3];
        Color colour = new Color();
        colour.RGBToHSV(red,green,blue,hsv);

        //RGB string
        colourCodes[0] = (red +", " + green +", " + blue);
        //Hex string
        colourCodes[1] = String.format("#%06X", (0xFFFFFF & (colour.rgb(red,green,blue))));

        //HSV string
        int hue = (int) hsv[0];
        int sat = (int) (hsv[1]*100);
        int val = (int) (hsv[2]*100);

        colourCodes[2] = hue + "°, " + sat + "%, " + val +"%";


        return colourCodes;
    }

    public void updateColour(int type){

        TextView value = findViewById(R.id.generated_code);

        ConstraintLayout background = findViewById(R.id.background);
        background.setBackgroundColor(Color.rgb(red,green,blue));

        value.setText(returnColour()[type]);






    }





}