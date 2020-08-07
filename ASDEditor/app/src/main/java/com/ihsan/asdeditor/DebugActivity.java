package com.ihsan.asdeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DebugActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);
        getSupportActionBar().setTitle("Debug Log");
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);

        final TextView log1 = findViewById(R.id.log);

        log1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("ASD Editor debug log", log1.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(), "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        try {
            Process process = Runtime.getRuntime().exec("logcat -d com.ihsan.asdeditor:d");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line + "\n");
            }
            final ScrollView sv = findViewById(R.id.scrollview1);
            sv.post(new Runnable() {
                @Override
                public void run() {
                    sv.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
            log1.setText(log.toString());
        }
        catch (IOException ignored) {}

        findViewById(R.id.clear_logs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Process process = Runtime.getRuntime().exec("logcat -c");
                    log1.setText("");
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(DebugActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}