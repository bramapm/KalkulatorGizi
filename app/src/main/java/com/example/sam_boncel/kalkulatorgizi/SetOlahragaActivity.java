package com.example.sam_boncel.kalkulatorgizi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SetOlahragaActivity extends AppCompatActivity {
    TextView txtNama, txtKkal, txtKeterangan, txtTgl;
    ImageView imageView;
    Button btnSet, btnTgl;
    private int mYear, mMonth, mDay;
    public User users_login;
    public String id = "";
    public String hasilKal = "";
    public String dateString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_olahraga);
        txtNama = (TextView) findViewById(R.id.nama_olg);
        txtKkal = (TextView) findViewById(R.id.kkal);
        txtKeterangan = (TextView) findViewById(R.id.keterangan);
        imageView = (ImageView) findViewById(R.id.imageView);
        TextView txtUser = (TextView) findViewById(R.id.user);
        btnSet = (Button) findViewById(R.id.btnSet);
        btnTgl = (Button) findViewById(R.id.btnTgl);
        txtTgl = (TextView) findViewById(R.id.tgl);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int witdh = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (witdh * .80), (int) (height * .70));

        Date now = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        dateString = frmt.format(now);
        txtTgl.setText(dateString);

        Log.d("hasil2", txtTgl.getText().toString());
        Log.d("hasil2", dateString.toString());
        btnSet.setText("Lakukan Sekarang");

        showData();
        loadDataUsersLogin();
        txtUser.setText(users_login.getId_user());
    }
    public void showData() {
        Intent i = getIntent();
        // Ambil String data Intent dari PilihOlahragaFragment
        id = i.getStringExtra("id_olahraga");
        String Nama_olg = i.getStringExtra("nama_olahraga");
        String kkal = i.getStringExtra("kkal");
        String keterangan = i.getStringExtra("keterangan");
        hasilKal = i.getStringExtra("hasilKal");

        Log.d("hasil", kkal);
        Log.d("hasil", hasilKal);

        // hasil menit dari kal
        double hasil = Double.parseDouble(String.valueOf(hasilKal)) / Double.parseDouble(String.valueOf(kkal));
        Log.d("hasil1", String.valueOf(hasil));

        txtNama.setText(Nama_olg);
        txtKkal.setText(kkal);
        txtKeterangan.setText(keterangan);
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

    public void saveJadwal() {
        FormData data = new FormData();
        data.add("method", "insertJadwal");
        data.add("id_user", users_login.getId_user());
        data.add("id_olahraga", id);//id olahraga yang di select
        data.add("tanggal", txtTgl.getText().toString());
        data.add("kalori", hasilKal.toString()); //menyimpan kal kelebihan saat proses perhitungan kal harian
        InternetTask uploadTask = new InternetTask("Jadwal", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        //btnRegister.setClickable(false);
                        Toast.makeText(getApplicationContext(),"Sukses", Toast.LENGTH_SHORT).show();
                        //Snackbar.make(,"Registrasi Sukses", Snackbar.LENGTH_SHORT).show();
                        //Snackbar.make(this, "Registration Success", Snackbar.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //Snackbar.make(clContent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
                //Snackbar.make(clContent, internetTask.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
        uploadTask.execute();
    }

    public void btn_onClick(View v){

        if (v == btnTgl){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtTgl.setText(year + "-" + 0 +(monthOfYear + 1) + "-" + dayOfMonth);

                            if (txtTgl.getText().toString().equals(dateString.toString())){
                                btnSet.setText("Lakukan Sekarang");
                            } else {
                                btnSet.setText("Set Jadwal");
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }else if(v == btnSet){
            if (btnSet.getText().equals("Lakukan Sekarang")){
                // Melakukan Olhraga sekarang, Sensor diaktifkan, menuju ke activity
                // OlahragaAktif
                Intent i = new Intent(getApplicationContext(), OlahragaAktif.class);
                startActivity(i);
            } else {
                //Save Olahraga di jadwal
                saveJadwal();
            }
        }
    }
}
