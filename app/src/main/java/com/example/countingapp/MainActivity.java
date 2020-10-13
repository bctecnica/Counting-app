package com.example.countingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Initializes
    // Main activity
    public ConstraintLayout background;
    private Button plus;
    private Button minus;
    private EditText counter;
    final Handler handler = new Handler();
    int currentCount;
    // Popup window
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button popupClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ID links
        plus = findViewById(R.id.plusButton);
        minus = findViewById(R.id.minusButton);
        background = findViewById(R.id.layout);
        counter = findViewById(R.id.Number);

        // sound resources
        final MediaPlayer pop = MediaPlayer.create(this,R.raw.zapsplat_cartoon_bubble_pop_003_40275);
        final MediaPlayer suck = MediaPlayer.create(this,R.raw.zapsplat_cartoon_bubble_pop_006_40278);

        // --Plus button--
        // Increases the count by one but stops the count at 9999
        // Flashes the background green and plays sound when clicked
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCount = Integer.parseInt(counter.getText().toString());

                if(currentCount == 9999) {
                    numberLimitFlash();
                }else{
                    // If plus is clicked at 9999 limit
                    currentCount++;
                    pop.start();
                    counter.setText(currentCount + "");
                    background.setBackgroundColor(Color.parseColor("#51b46d"));
                    flash();
                }
            }
        });

        // --Minus button--
        // Decreases the count by one but stops the count going below 0
        // Flashes the background red and plays sound when clicked
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCount = Integer.parseInt(counter.getText().toString());

                if(currentCount == 0) {
                    numberLimitFlash();
                }else{
                    currentCount--;
                    suck.start();
                    counter.setText(currentCount + "");
                    background.setBackgroundColor(Color.parseColor("#ff5147"));
                    flash();
                }
            }
        });
    }

    // Info icon in action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.add("info");
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menuItem.setIcon(R.drawable.info_icon);
        return true;
    }

    // Opens info pane when icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        createInfoPopupWindow();
        Toast.makeText(getApplicationContext(),"info button clicked",Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    // Creates popup window
    public void createInfoPopupWindow(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View infoPopup = getLayoutInflater().inflate(R.layout.popup, null);
        popupClose = infoPopup.findViewById(R.id.closePopupButton);

        dialogBuilder.setView(infoPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        popupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
