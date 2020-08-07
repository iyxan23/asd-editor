package com.ihsan.asdeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    private int debug = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().hide();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.colorAccentDark));

        Button vbucks = findViewById(R.id.btnvbucks);
        vbucks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.youtube.com/watch?v=oHg5SJYRHA0";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.created).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                debug++;
                if (debug == 10) {
                    Intent i = new Intent(AboutActivity.this, DebugActivity.class);
                    startActivity(i);
                    debug = 0;
                }
            }
        });
    }
}