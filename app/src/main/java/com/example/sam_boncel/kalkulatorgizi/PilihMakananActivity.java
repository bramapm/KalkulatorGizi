package com.example.sam_boncel.kalkulatorgizi;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sam_boncel.kalkulatorgizi.entities.Makanan;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PilihMakananActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_makanan);
    }
}
