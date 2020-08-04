package com.ihsan.asdeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import io.github.kbiakov.codeview.CodeView;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        CodeView cv = findViewById(R.id.code_view);
        Intent i = getIntent();
        final String id = i.getStringExtra("id");
        String line = i.getStringExtra("line");
        String data = Util.decrypt(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.sketchware/data/" + id + "/logic");
        String[] splitted_data = data.split("[\\r\\n]+");
        assert line != null;
        String code = splitted_data[Integer.parseInt(line)];
        try {
            JSONObject code_obj = new JSONObject(code);
            cv.setCode((String) code_obj.getJSONArray("parameters").get(0), "java");
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "ERROR While getting Block data", Toast.LENGTH_LONG);
            finish();
        }

        FloatingActionButton fab = findViewById(R.id.save);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RandomAccessFile raf = new RandomAccessFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.sketchware/data/" + id + "/logic", "rw");
                    raf.setLength(0L);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}