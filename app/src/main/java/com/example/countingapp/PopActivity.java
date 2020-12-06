package com.example.countingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PopActivity extends Activity {

    EditText total;
    ImageButton emailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        emailButton = findViewById(R.id.emailButton);
        total = findViewById(R.id.totalCounter);

        // gets total number from main and set it to total on pop up
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

        // composes email to send to me from user with there suggestions
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bctecnica@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Counting app");
                try {
                    startActivity(Intent.createChooser(i, "Send e-mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(PopActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Used to set attributes of popup window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }
}