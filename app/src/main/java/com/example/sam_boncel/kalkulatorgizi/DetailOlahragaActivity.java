package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DetailOlahragaActivity extends AppCompatActivity {
    TextView txtNama, txtKkal, txtKeterangan;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_olahraga);

        txtNama = (TextView) findViewById(R.id.nama_olg);
        txtKkal = (TextView) findViewById(R.id.kkal);
        txtKeterangan = (TextView) findViewById(R.id.keterangan);
        imageView = (ImageView) findViewById(R.id.imageView);

        showData();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (witdh * .80), (int) (height * .60));
    }

    public void showData() {
        Intent i = getIntent();
        String Nama_olg = i.getStringExtra("nama_olahraga");
        String kkal = i.getStringExtra("kkal");
        String keterangan = i.getStringExtra("keterangan");
        txtNama.setText(Nama_olg);
        txtKkal.setText(kkal);
        txtKeterangan.setText(keterangan);
    }
}
