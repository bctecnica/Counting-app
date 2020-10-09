package com.example.countingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        // Increases the count by one per click
        // Flashes the background green when clicked
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCount = Integer.parseInt(counter.getText().toString());
                currentCount++;
                counter.setText(currentCount + "");
                background.setBackgroundColor(Color.parseColor("#51b46d"));
                flash();
            }
        });

        // --Minus button--
        // Decreases the count by one but stops the count going below 0
        // Flashes the background red when clicked and black when trying to go below zero
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentCount > 0) {
                    currentCount = Integer.parseInt(counter.getText().toString());
                    currentCount--;
                    counter.setText(currentCount + "");
                   background.setBackgroundColor(Color.parseColor("#ff5147"));
                   flash();

                }else{
                    // If minus is clicked when zero
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
            }
        });
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
