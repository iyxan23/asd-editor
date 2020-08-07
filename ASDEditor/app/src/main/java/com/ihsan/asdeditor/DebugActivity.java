package com.ihsan.asdeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        getSupportActionBar().setTitle("Debug Log");
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);

        TextView log = findViewById(R.id.log);
        log.setText(sp.getString("log", "Empty log"));
    }
}