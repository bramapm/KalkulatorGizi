package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sam_boncel.kalkulatorgizi.entities.Jadwal;
import com.example.sam_boncel.kalkulatorgizi.entities.Olahraga;
import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.sam_boncel.kalkulatorgizi.R.id.kalorimkn;
import static com.example.sam_boncel.kalkulatorgizi.R.id.txtIdOlahraga;
import static com.example.sam_boncel.kalkulatorgizi.R.id.txtNamaOlahraga;

public class DetailJadwal extends AppCompatActivity {

    TextView txtIdOlahraga, txtTanggal, txtKalori, txtNamaOlahraga;
    Button btnJadwal;
    User users_login;
    String nama, idOlahraga, kalori, kal_olahraga, tanggal, idJadwal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);
        txtIdOlahraga = (TextView) findViewById(R.id.txtIdOlahraga);
        txtTanggal = (TextView) findViewById(R.id.txtTanggal);
        txtKalori = (TextView) findViewById(R.id.txtKalori);
        txtNamaOlahraga = (TextView) findViewById(R.id.txtNamaOlahraga);
        btnJadwal = (Button) findViewById(R.id.btnJadwal);
        loadDataUsersLogin();

        Intent i = getIntent();
        idJadwal = i.getStringExtra("id_jadwal");
        final String id2= i.getStringExtra("id_olahraga");
        final String tgl = i.getStringExtra("tanggal");
        final String kal = i.getStringExtra("kalori");

        // hasil menit dari kal
        //DecimalFormat df = new DecimalFormat("0.00");
        //final String hasilAkhir = df.format(waktu);
        //txtIdOlahraga.setText(id_olahraga);
        //txtTanggal.setText(tanggal);
        //txtKalori.setText(kalori);
        Log.d("posi", idJadwal);
        Log.d("posi", users_login.getId_user());
        Log.d("posi", id2);
        Log.d("posi", tgl);
        Log.d("posi", kal);

        FormData data = new FormData();
        data.add("method", "get_jadwal");
        data.add("id_user", users_login.getId_user());
        data.add("id_jadwal", idJadwal);
        InternetTask uploadTask = new InternetTask("Jadwal", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)) {
                        JSONArray nv = jsonObject.getJSONArray("data");
                        JSONObject jo = nv.getJSONObject(0);

                        final String nama        = jo.getString("nama_olahraga");
                        final String idOlahraga  = jo.getString("id_olahraga");
                        final String kalori      = jo.getString("kalori");
                        final String kal_olahraga = jo.getString("kal_olaraga");

                        Log.d("posi", nama);
                        Log.d("posi", tanggal);
                        Log.d("posi", kal);
                        Log.d("posi", idOlahraga);
                        if (!(jo == null || nv == null)) {
                            txtIdOlahraga.setText(idOlahraga);
                            txtNamaOlahraga.setText(nama);
                            txtTanggal.setText(tanggal);
                            txtKalori.setText(kal);
                        }
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
            }
        });
        uploadTask.execute();

        btnJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), OlahragaAktif.class);
                i.putExtra("id_olahraga", idOlahraga);
                i.putExtra("nama_olahraga", nama);
                i.putExtra("hasilKal", kalori);
                i.putExtra("kkal", kal_olahraga);
                startActivity(i);
            }
        });
    } // end onCreate

    public boolean loadDataUsersLogin(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("data_private", 0);
        String data = sharedPref.getString("data", "");
        Log.d("cihuy", data);
        if (data != ""){
            Log.d("karepmu B", "sakkarepmu B");
            Gson gson = new GsonBuilder()
                    .disableHtmlEscaping()
                    .setPrettyPrinting()
                    .enableComplexMapKeySerialization()
                    .create();
            users_login = gson.fromJson(data, User.class);
            return true;
        }else{
            return false;
        }
    }
}
