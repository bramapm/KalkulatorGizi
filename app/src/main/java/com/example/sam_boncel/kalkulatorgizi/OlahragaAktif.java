package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.x;

public class OlahragaAktif extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private CountDownTimer countDownTimer;
    TextView xcoor, ycoor, zcoor, totalcoor, progress, txtKalori, txtWaktu, txtKalTerbakar, txtNamaOlahraga;
    Button btnOlahraga;
    private float acelVal, acelLast, shake;
    String str = "";
    Double hasilKal, kkal, waktu;
    String id, Nama_olg, keterangan;
    double kkaljd = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olahraga_aktif);

        xcoor = (TextView)findViewById(R.id.xcoor);
        ycoor = (TextView)findViewById(R.id.ycoor);
        zcoor = (TextView)findViewById(R.id.zcoor);
        txtKalori = (TextView)findViewById(R.id.txtKalori);
        txtKalTerbakar = (TextView)findViewById(R.id.txtKalTerbakar);
        txtWaktu = (TextView)findViewById(R.id.txtwaktu);
        txtNamaOlahraga = (TextView)findViewById(R.id.txtNamaOlahraga);
        totalcoor = (TextView)findViewById(R.id.totalcoor);
        progress =(TextView)findViewById(R.id.progress);
        btnOlahraga = (Button)findViewById(R.id.btnOlahragaAktif);

        Intent a = getIntent();
        // Ambil String data Intent dari setOlahragaActivity
        id = a.getStringExtra("id_olahraga");
        kkal = Double.parseDouble(a.getStringExtra("kkal"));
        Nama_olg = a.getStringExtra("nama_olahraga");
        keterangan = a.getStringExtra("keterangan");
        hasilKal = Double.parseDouble(a.getStringExtra("hasilKal"));
        waktu = Double.parseDouble(a.getStringExtra("waktu"));

        showData();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_NORMAL);
        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        btnOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), reportOlahragaAktif.class);
                i.putExtra("id_olahraga", id);
                i.putExtra("nama_olahraga", Nama_olg);
                i.putExtra("kkalTerbakar", txtKalTerbakar.getText().toString());
                i.putExtra("waktu", waktu);
                startActivity(i);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //cek jenis sensor
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float xc = event.values[0];
            float yc = event.values[1];
            float zc = event.values[2];
            float step = event.values.length;

            xcoor.setText("X : " +xc* -1);
            ycoor.setText("Y : " +yc* -1);
            zcoor.setText("Z : " +zc* -1 + step);

            acelLast = acelVal;
            acelVal = ((float) Math.sqrt((double) (xc*xc) + (yc*yc) + (zc*zc)));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            double a = kkal / 60;
//            Log.d("shake3", String.valueOf(a));
//
//            DecimalFormat df = new DecimalFormat("0.00");
//            final String kkaldetik = df.format(a);
//            String abc = df.format(kkaljd);
//
//            final double satu = Double.parseDouble(String.valueOf(kkaldetik));
//            final double dua = Double.parseDouble(String.valueOf(abc));
            String ab = txtKalTerbakar.getText().toString();

            if (Double.parseDouble(ab) > hasilKal){
                countKal(false);
//                txtKalTerbakar.setText("0.0");
            } else {
                countKal(true);
//                if (shake > 0 && shake <= 2) { //tidak melakukan apa" atau bergerak sangat pelan
//
//                } else if (shake > 2 && shake <= 7) { // Menjalankan kondisi jika gerakan pelan terdeteksi
//
//                } else if (shake > 7) { //Menjalankan kondisi jika gerakan keras terdeteksi
//                    Log.d("shake2", String.valueOf(shake));
//                    Log.d("shake3", String.valueOf(satu));
//                    Log.d("shake3", String.valueOf(kkaljd));
//                    kkaljd = satu + dua;
//                    txtKalTerbakar.setText(String.valueOf(kkaljd));
//                } else {
//                }
            }

            float total = event.values[0] + event.values[1] + event.values[2];
            totalcoor.setText("shake" + shake + "\n" + "total : " +total);

        } else {
            xcoor.setText("X : -");
            ycoor.setText("Y : -");
            zcoor.setText("Z : -" + "\n" + "Perangkat ini tidak mendukung sensor Accelerometer");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void showData(){
        txtNamaOlahraga.setText(Nama_olg);
        txtKalori.setText(String.valueOf(hasilKal));
        txtKalTerbakar.setText("0");
        txtWaktu.setText(String.valueOf(waktu));
    }

    public void countKal(boolean b){
        double a = kkal / 60;
        DecimalFormat df = new DecimalFormat("0.00");
        final String kkaldetik = df.format(a);
        String abc = df.format(kkaljd);

        final double satu = Double.parseDouble(String.valueOf(kkaldetik));
        final double dua = Double.parseDouble(String.valueOf(abc));

        if (b == true) {
            if (shake > 0 && shake <= 2) { //tidak melakukan apa" atau bergerak sangat pelan

            } else if (shake > 2 && shake <= 7) { // Menjalankan kondisi jika gerakan pelan terdeteksi

            } else if (shake > 7) { //Menjalankan kondisi jika gerakan keras terdeteksi
                Log.d("shake2", String.valueOf(shake));
                Log.d("shake3", String.valueOf(satu));
                Log.d("shake3", String.valueOf(kkaljd));
                kkaljd = satu + dua;
                txtKalTerbakar.setText(String.valueOf(kkaljd));
            } else {
            }
        }
    }
}
