package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class DetailMakananActivity extends AppCompatActivity {
    TextView txtNama_mkn, txtJenis, txtKkal, txtKarbo, txtProtein, txtLemak, txtKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);

        txtNama_mkn = (TextView) findViewById(R.id.nama_mkn);
        txtJenis = (TextView) findViewById(R.id.jenis);
        txtKkal = (TextView) findViewById(R.id.kkal);
        txtKarbo = (TextView) findViewById(R.id.karbo);
        txtProtein = (TextView) findViewById(R.id.protein);
        txtLemak = (TextView) findViewById(R.id.lemak);
        txtKeterangan = (TextView) findViewById(R.id.keterangan);
        showData();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (witdh * .80), (int) (height * .60));
    }

    public void showData() {
        Intent i = getIntent();
        String Nama_mkn = i.getStringExtra("nama_makanan");
        String jenis = i.getStringExtra("jenis");
        String kkal = i.getStringExtra("kkal");
        String karbo = i.getStringExtra("karbo");
        String protein = i.getStringExtra("protein");
        String lemak = i.getStringExtra("lemak");
        String keterangan = i.getStringExtra("keterangan");
        txtNama_mkn.setText(Nama_mkn);
        txtJenis.setText(jenis);
        txtKkal.setText(kkal);
        txtKarbo.setText(karbo);
        txtProtein.setText(protein);
        txtLemak.setText(lemak);
        txtKeterangan.setText(keterangan);
    }
}