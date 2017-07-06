package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class reportOlahragaAktif extends AppCompatActivity {

    TextView txtNamaOlahraga, txtKalTerbakar, txtWaktu, txt2;
    Button btnCentang;
    String id_olahraga, nama_olahraga, kkalTerbakar;
    Double waktu;
    public User users_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_olahraga_aktif);

        txtNamaOlahraga = (TextView) findViewById(R.id.txtNamaOlahraga);
        txtKalTerbakar = (TextView) findViewById(R.id.txtKalTerbakar);
        txtWaktu= (TextView) findViewById(R.id.txtWaktu);
        txt2= (TextView) findViewById(R.id.txt2);
        btnCentang= (Button) findViewById(R.id.btnCentang);

        loadDataUsersLogin();
        Intent i = getIntent();
        id_olahraga = i.getStringExtra("id_olahraga");
        nama_olahraga = i.getStringExtra("nama_olahraga");
        kkalTerbakar = i.getStringExtra("kkalTerbakar");
        waktu = Double.parseDouble(i.getStringExtra("waktu"));

        txtNamaOlahraga.setText(nama_olahraga);
        txtKalTerbakar.setText(kkalTerbakar);
        txtWaktu.setText(String.valueOf(waktu));

        btnCentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //waktu = "1";
                Log.d("tesss", id_olahraga + nama_olahraga + kkalTerbakar + String.valueOf(waktu));
                saveRecordOlg();
            }
        });
    }

    public void saveRecordOlg() { //save record hari ini
        FormData data = new FormData();
        data.add("method", "insertOlg");
        data.add("id_user", users_login.getId_user());
        data.add("id_olahraga", id_olahraga);
        data.add("tanggal", getTanggal());
        data.add("kalori", kkalTerbakar);
        data.add("waktu", String.valueOf(waktu));
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        Toast.makeText(getApplicationContext(),"Sukses Insert Data", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(),"Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //Snackbar.make(clContent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
                //Snackbar.make(clContent, internetTask.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
        uploadTask.execute();
    }

    private String getTanggal() {
        Date now = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = frmt.format(now);
        return dateString;
    }

    public boolean loadDataUsersLogin(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("data_private", 0);
        Log.d("karepmu A", "sakkarepmu A");
        String data = sharedPref.getString("data", "");
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
