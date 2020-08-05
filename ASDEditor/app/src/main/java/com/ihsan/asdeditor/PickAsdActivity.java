package com.ihsan.asdeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PickAsdActivity extends AppCompatActivity {

    private static final String TAG = "PickAsdActivity";

    ArrayList<Integer> lines = new ArrayList<>();
    ArrayList<Integer> asdlines = new ArrayList<>();
    ArrayList<JSONObject> datas = new ArrayList<>();
    ArrayList<String> currents = new ArrayList<>();

    String id = "";
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_asd);
        // Parse the sketchware logic data structure
        Intent i = getIntent();
        id = i.getStringExtra("id");
        name = i.getStringExtra("name");
        update();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        lines.clear();
        asdlines.clear();
        datas.clear();
        currents.clear();
        update();
    }

    private void update() {
        TextView prjname = findViewById(R.id.projectName);
        prjname.setText(name);
        String data = Util.decrypt(Environment.getExternalStorageDirectory() + "/.sketchware/data/" + id + "/logic");
        Log.d(TAG, "onCreate: " + Environment.getExternalStorageDirectory() + "/.sketchware/data/" + id + "/logic");
        String[] splitted_data = data.split("\\r?\\n");
        boolean ignore = false;
        int index = 0;
        int lineint = 0;
        String current = "";
        for (String line: splitted_data) {
            if (line.contains(".java_var")) {
                ignore = true;
            }
            if (!ignore && line.length() != 0) {
                if (line.charAt(0) != '@') {
                    try {
                        Log.d(TAG, "onCreate: line: " + line);
                        if (line.equals("ERROR WHILE DECRYPTING")) {
                            Toast.makeText(this, "ERROR WHILE DECRYPTING", Toast.LENGTH_LONG).show();
                        }
                        JSONObject object = new JSONObject(line);
                        if (!object.isNull("opCode")) {
                            if (object.getString("opCode").equals("addSourceDirectly")) {
                                asdlines.add(index);
                                lines.add(lineint);
                                datas.add(object);
                                currents.add(current);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error while decrypting, you may tell the developer for this: "  + e.getMessage(), Toast.LENGTH_LONG)
                        .show();
                    }
                    lineint++;
                } else {
                    current = line.substring(1);
                    lineint = 0;
                }
            }
            index++;
        }

        if (lines.size() == 0) {
            findViewById(R.id.rv_pick_asd).setVisibility(View.GONE);
        } else {
            findViewById(R.id.no_asd_detected).setVisibility(View.GONE);
        }

        RecyclerView rv = findViewById(R.id.rv_pick_asd);
        rv.setAdapter(new ASDAdapter(lines, datas, currents, this, asdlines, id));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
    }
}