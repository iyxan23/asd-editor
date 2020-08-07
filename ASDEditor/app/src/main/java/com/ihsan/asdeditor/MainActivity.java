package com.ihsan.asdeditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import java.util.ArrayList;
import java.util.Hashtable;

import static com.ihsan.asdeditor.Util.getSketchwareProjects;

public class MainActivity extends AppCompatActivity {

    ArrayList<Hashtable<String, String>> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setExitTransition(new Slide());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);

        Util.start(sp);

        storagePerms();

        RecyclerView rv = findViewById(R.id.rv_projs);
        rv.setAdapter(new ProjectsAdapter(data, this));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("WARNING")
                .setMessage("This app is still in development, any bug may corrupt your project's logic. For safety purposes, please backup your project before using this app, I really recommend using SH Recovery.")
                .setPositiveButton("I know what i'm doing!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Wait, let me backup first", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAndRemoveTask();
                    }
                })
                .setNeutralButton("Never show this again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sp.edit().putBoolean("dialog_warn_1_never", true).apply();
                    }
                });
        if (!sp.getBoolean("dialog_warn_1_never", false)) {
            builder.show();
        }
    }

    private void storagePerms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
            } else {
                data = getSketchwareProjects();
            }
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent ind) {
        super.onActivityReenter(resultCode, ind);
        storagePerms();
        RecyclerView rv = findViewById(R.id.rv_projs);
        rv.setAdapter(new ProjectsAdapter(data, this));
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            data = getSketchwareProjects();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_item_actionbar:
                Intent i = new Intent(this, AboutActivity.class);
                startActivity(i);
                return true;
            case R.id.settings_item_actionbar:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}