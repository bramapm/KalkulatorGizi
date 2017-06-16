package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        imageView = (ImageView) findViewById(R.id.imageView1);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (witdh * .80), (int) (height * .60));
        showData();
    }

    public void showData() {
        Intent i = getIntent();
        String id = i.getStringExtra("id_olahraga");
        String Nama_olg = i.getStringExtra("nama_olahraga");
        String foto = i.getStringExtra("foto");
        String kkal = i.getStringExtra("kkal");
        String keterangan = i.getStringExtra("keterangan");
        Picasso.with(this).load("http://192.168.43.20/KalkulatorGiziService/assets/upload/Olahraga/"+foto).into(imageView);
        Log.d("FOTO", foto);
        txtNama.setText(Nama_olg);
        txtKkal.setText(kkal);
        txtKeterangan.setText(keterangan);
    }
}
