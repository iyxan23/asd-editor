package com.ihsan.asdeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import de.markusressel.kodeeditor.library.view.CodeEditorLayout;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = "EditorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        final CodeEditorLayout cv = findViewById(R.id.codeeditor);
        cv.setLanguageRuleBook(new JavaRuleBook());
        Intent i = getIntent();
        final String id = i.getStringExtra("id");
        final String line = i.getStringExtra("line");
        final String data = Util.decrypt(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.sketchware/data/" + id + "/logic");
        final String[] splitted_data = data.split("\\r?\\n");
        Log.d(TAG, "onCreate: " + Arrays.toString(splitted_data));
        Util.logd(Arrays.toString(splitted_data));
        assert line != null;
        String code = splitted_data[Integer.parseInt(line)];
        JSONObject code_obj = new JSONObject();
        try {
            code_obj = new JSONObject(code);
            cv.setText((String) code_obj.getJSONArray("parameters").get(0));
        } catch (JSONException e) {
            e.printStackTrace();
            Util.loge(e.toString());
            Toast.makeText(this, "ERROR While getting Block data", Toast.LENGTH_LONG);
            finish();
        }

        FloatingActionButton fab = findViewById(R.id.save);
        code_obj.remove("parameters");
        final JSONObject finalCode_obj = code_obj;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RandomAccessFile raf = new RandomAccessFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.sketchware/data/" + id + "/logic", "rw");
                    String linedata = finalCode_obj.toString().substring(0, finalCode_obj.toString().length() - 1).concat(", \"parameters\": [\"" + cv.getText().replaceAll(System.lineSeparator(), "\\\\n").replaceAll("\"", "\\\"") + "\"]}");
                    Log.d(TAG, "onClick: linedata: " + linedata);
                    Util.logd(" Linedata: " + linedata);
                    splitted_data[Integer.parseInt(line)] = linedata;
                    Log.d(TAG, "onClick: splitteddataresult: " + Arrays.toString(splitted_data));
                    Util.logd(" splitresult: " + Arrays.toString(splitted_data));
                    String result = "";
                    for (String data: splitted_data) {
                        result = result.concat(data).concat("\n");
                    }
                    raf.setLength(0L);
                    Log.d(TAG, "onClick: result:" + result);
                    Util.logd(" result: " + result);
                    Util.encrypt(result, Environment.getExternalStorageDirectory().getAbsolutePath() + "/.sketchware/data/" + id + "/logic");
                    Toast.makeText(EditorActivity.this, "Saved", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditorActivity.this, "Error while saving", Toast.LENGTH_LONG).show();
                    Util.loge(e.toString());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
    }
}