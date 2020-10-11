package com.example.countingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // Initializes
    public ConstraintLayout background;
    private Button plus;
    private Button minus;
    private EditText counter;
    final Handler handler = new Handler();
    int currentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ID links

        plus = findViewById(R.id.plusButton);
        minus = findViewById(R.id.minusButton);
        background = findViewById(R.id.layout);
        counter = findViewById(R.id.Number);

        // --Plus button--
        // Increases the count by one but stops the count at 9999
        // Flashes the background green when clicked
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCount = Integer.parseInt(counter.getText().toString());

                if(currentCount == 9999) {
                    counter.setText(currentCount + "");
                    numberLimitFlash();
                }else{
                    // If plus is clicked at 9999 limit
                    currentCount++;
                    counter.setText(currentCount + "");
                    background.setBackgroundColor(Color.parseColor("#51b46d"));
                    flash();
                }
            }
        });

        // --Minus button--
        // Decreases the count by one but stops the count going below 0
        // Flashes the background red when clicked
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCount = Integer.parseInt(counter.getText().toString());

                if(currentCount == 0) {
                    numberLimitFlash();
                }else{
                    currentCount--;
                    counter.setText(currentCount + "");
                    background.setBackgroundColor(Color.parseColor("#ff5147"));
                    flash();
                }
            }
        });
    }

    // Used to flash white when user hits min(0)/max(9999) limit
    private void numberLimitFlash() {
        background.setBackgroundColor(Color.parseColor("#ffffff"));
        counter.setTextColor(Color.parseColor("#000000"));
        plus.setTextColor(Color.parseColor("#000000"));
        minus.setTextColor(Color.parseColor("#000000"));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                background.setBackgroundColor(Color.BLACK);
                counter.setTextColor(Color.parseColor("#ffffff"));
                plus.setTextColor(Color.parseColor("#ffffff"));
                minus.setTextColor(Color.parseColor("#ffffff"));
            }
        }, 200);
    }

    // Used to create color flash effect
    private void flash() {
        handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        background.setBackgroundColor(Color.BLACK);
                    }
                }, 175);
    }

    }
