package com.zybooks.lightsout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class ColorActivity<colorId> extends AppCompatActivity {

    public static final String EXTRA_COLOR = "com.zybooks.lightsout.color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        // Get the color ID from MainActivity
        Intent intent= getIntent();
        int colorId = intent.getIntExtra(EXTRA_COLOR, R.color.yellow);

        // Select the radio button matching the color ID
        int radioId = R.id.radio_yellow;
        if (colorId == R.color.red) {
            radioId = R.id.radio_red;
        } else if (colorId == R.color.orange) {
            radioId = R.id.radio_orange;
        } else if (colorId == R.color.green) {
            radioId = R.id.radio_green;
        }

        RadioButton radio = findViewById(radioId);
        radio.setChecked(true);
    } // end onCreate


    public void onColorSelected(View view) {

        // get the color id from MainActivity
        int colorID = R.color.yellow;

        if (view.getId() == R.id.radio_red) {
            colorID = R.color.red;
        } else if (view.getId() == R.id.radio_orange) {
            colorID = R.color.orange;
        } else if (view.getId() == R.id.radio_green) {
            colorID = R.color.green;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_COLOR, colorID);
        setResult(RESULT_OK, intent);
        finish();

    } // end onColorSelected
}