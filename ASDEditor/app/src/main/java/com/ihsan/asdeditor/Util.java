package com.ihsan.asdeditor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Util {
    public static Hashtable<String, String> key2name = new Hashtable<>();
    private static final String TAG = "Util";
    public static SharedPreferences sp;

    public static void start(SharedPreferences sp) {
        Util.sp = sp;
    }
    
    public static void loge(String data) {
        if (sp.getInt("time_log", (int) System.currentTimeMillis() * 1000) + 86400 >= (int) System.currentTimeMillis() * 1000) {
            sp.edit()
                    .putString("log", "")
                    .putInt("time_log", (int) System.currentTimeMillis() * 1000 + 86400)
                    .apply();
        }
        sp.edit().putString(sp.getString("log", "") + "E: " + data + "\n", "").apply();
    }

    public static void logd(String data) {
        if (sp.getInt("time_log", (int) System.currentTimeMillis() * 1000) + 86400 >= (int) System.currentTimeMillis() * 1000) {
            sp.edit()
                    .putString("log", "")
                    .putInt("time_log", (int) System.currentTimeMillis() * 1000 + 86400)
                    .apply();
        }
        sp.edit().putString(sp.getString("log", "") + "D: " + data + "\n", "").apply();
    }

    public static void logw(String data) {if (sp.getInt("time_log", (int) System.currentTimeMillis() * 1000) + 86400 >= (int) System.currentTimeMillis() * 1000) {
        sp.edit()
                .putString("log", "")
                .putInt("time_log", (int) System.currentTimeMillis() * 1000 + 86400)
                .apply();
        }
        sp.edit().putString(sp.getString("log", "") + "W: " + data + "\n", "").apply();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String base64encrypt(String txt) {
        byte[] data = txt.getBytes();
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static String base64decrypt(String base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return new String(data);
    }

    public static ArrayList<Hashtable<String, String>> getSketchwareProjects() {
        ArrayList<Hashtable<String, String>> sketchwareProjects = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.sketchware/mysc/list/";
        Log.d(TAG, "getSketchwareProjects: " +path);
        Log.d(TAG, "getSketchwareProjects: listdir: " + listDir(path).toString());
        for (String pat: listDir(path)) {
            try {
                Log.d(TAG, "getSketchwareProjects: " + pat + "/project");
                logd("Decrypting " + pat);
                Hashtable<String, String> data = new Hashtable<>();
                JSONObject project = new JSONObject(decrypt(pat + "/project"));
                data.put("name", project.getString("my_app_name"));
                data.put("version", project.getString("sc_ver_name"));
                data.put("package", project.getString("my_sc_pkg_name"));
                data.put("coname", project.getString("my_ws_name"));
                data.put("id", project.getString("sc_id"));
                sketchwareProjects.add(data);
            } catch (Exception e) {
                Log.e(TAG, "getSketchwareProjects: " + e.toString());
                loge("ERROR DECRYPTING: " + e.toString());
            }
        }
        return sketchwareProjects;
    }

    public static String decrypt(String path) {
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");;
            byte[] bytes = "sketchwaresecure".getBytes();
            instance.init(2, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            RandomAccessFile randomAccessFile = new RandomAccessFile(path, "r");
            byte[] bArr = new byte[((int) randomAccessFile.length())];
            randomAccessFile.readFully(bArr);
            Log.d(TAG, "decrypt: Decrypted successfully");
            return new String(instance.doFinal(bArr));
        } catch (Exception e) {
            loge("ERROR DECRYPTING: " + e.toString());
            return "ERROR WHILE DECRYPTING: " + e.toString();
        }
    }

    public static String encrypt(String str, String path) {
        // The boolean is to determine if the process is successful or not
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = "sketchwaresecure".getBytes();
            instance.init(1, new SecretKeySpec(bytes, "AES"), new IvParameterSpec(bytes));
            byte[] doFinal = instance.doFinal(str.getBytes());
            final RandomAccessFile raf = new RandomAccessFile(path, "rw");
            raf.setLength(0L);
            raf.write(doFinal);
        } catch (Exception ignored) {}
        return "ERROR WHILE ENCRYPTING";
    }

    public static ArrayList<String> listDir(String str) {
        ArrayList<String> arrayList = new ArrayList<>();
        File file = new File(str);
        if (file.exists() && !file.isFile()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                arrayList.clear();
                for (File absolutePath : listFiles) {
                    arrayList.add(absolutePath.getAbsolutePath());
                }
            }
        }
        return arrayList;
    }
}