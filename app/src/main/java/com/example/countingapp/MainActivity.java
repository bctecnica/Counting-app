package com.example.countingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Initializes
    public ConstraintLayout background;
    private Button plus;
    private Button minus;
    private EditText counter;
    final Handler handler = new Handler();
    private int currentCount;
    private int totalCount;
    private int adCount;
    private SharedPreferences.Editor editor;
    private static final String PREFS_FILE = "com.example.countingapp.preferences";
    private static final String KEY_TOTALCOUNT = "KEY_TOTALCOUNT";
    private InterstitialAd mInterstitialAd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restores total count variable 
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        totalCount = sharedPreferences.getInt(KEY_TOTALCOUNT, 0);

        // Advertising set up
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // Loads initial ad
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5497069649516082/6272503968");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        // Sets up next ad to play after the initial one is closed
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                AdRequest adRequest = new AdRequest.Builder().build();
                mInterstitialAd.loadAd(adRequest);
            }
        });

        // ID links
        plus = findViewById(R.id.plusButton);
        minus = findViewById(R.id.minusButton);
        background = findViewById(R.id.layout);
        counter = findViewById(R.id.Number);

        // Hides action bar when device is in landscape orientation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }

        // sound resources
        final MediaPlayer popSound = MediaPlayer.create(this,R.raw.zapsplat_cartoon_bubble_pop_003_40275);
        final MediaPlayer suckSound = MediaPlayer.create(this,R.raw.zapsplat_cartoon_bubble_pop_006_40278);

        // --Plus button--
        // Increases the count by one but stops the count at 9999
        // Flashes the background green and plays sound when clicked
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkUserInput();
                adCheck();

                if(currentCount == 9999) {
                    numberLimitFlash();
                }else{
                    // If plus is clicked at 9999 limit
                    currentCount++;
                    totalCount++;
                    popSound.start();
                    counter.setText(currentCount + "");
                    background.setBackgroundResource(R.drawable.plus_flash);
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
                checkUserInput();
                adCheck();

                if(currentCount == 0) {
                    numberLimitFlash();
                }else{
                    currentCount--;
                    suckSound.start();
                    counter.setText(currentCount + "");
                    background.setBackgroundResource(R.drawable.minus_flash);
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

    // Opens info pane when action bar icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i = new Intent(getApplicationContext(), PopActivity.class);
        i.putExtra("TOTAL_COUNT", totalCount);
        startActivityForResult(i,1);
        return super.onOptionsItemSelected(item);
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

    // Used to make sure user input isn't empty then converts to an int and clears focus
    private void checkUserInput() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        String userInput = counter.getText().toString();
        if(userInput.equals("")) {
            currentCount = 0;
        }else{
            currentCount = Integer.parseInt(counter.getText().toString());
        }
        counter.clearFocus();
    }

    // Launches ad popup
    private void adCheck(){
        adCount++;
        if(adCount == 10){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                adCount = 0;
            }
        }
    }

    // Saves the total count when app is closed or user changes orientation
    @Override
    protected void onPause() {
        super.onPause();

        editor.putInt(KEY_TOTALCOUNT, totalCount);
        editor.apply();
    }

    // Clears total count if user resets it
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                totalCount = data.getIntExtra("total_cleared", 0);
            }
        }
    }

}
