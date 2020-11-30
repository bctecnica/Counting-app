package com.example.countingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class PopActivity extends Activity {

    Button closeBtn;
    EditText total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        // gets total number from main and set it to total on pop up
        total = findViewById(R.id.totalCounter);
        int totalPassed = getIntent().getIntExtra("TOTAL_COUNT",0);
        total.setText(""+ totalPassed);

        // Used to clear total count when tapped
        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total.setText("0");
                Intent intent = new Intent();
                intent.putExtra("total_cleared", 0);
                setResult(RESULT_OK, intent);
            }
        });

        // Close button
        closeBtn = findViewById(R.id.closeButton);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Used to set attributes of popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.7));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }
}