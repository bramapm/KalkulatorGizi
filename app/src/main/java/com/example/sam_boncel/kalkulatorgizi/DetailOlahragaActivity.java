package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (witdh * .80), (int) (height * .60));
        showData();
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
