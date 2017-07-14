package com.example.sam_boncel.kalkulatorgizi;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.SensorEvent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class OlahragaAktif extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private CountDownTimer countDownTimer;
    TextView totalcoor, progress, txtKalori,txtKalTerbakar, txtNamaOlahraga;
    TextView txtJam;
    Button btnOlahraga;
    private float acelVal, acelLast, shake;
    String str = "";
    Double hasilKal, kkal, waktu;
    String id, Nama_olg, keterangan;
    double kkaljd = 0.0;

    int buttonState, lapsCount;
    String laps;
    private Handler mHandler;
    private boolean mStarted;
    long start_time;
    long old_degree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olahraga_aktif);
        txtKalori = (TextView)findViewById(R.id.txtKalori);
        txtKalTerbakar = (TextView)findViewById(R.id.txtKalTerbakar);
        txtNamaOlahraga = (TextView)findViewById(R.id.txtNamaOlahraga);
        totalcoor = (TextView)findViewById(R.id.totalcoor);
        progress =(TextView)findViewById(R.id.progress);
        btnOlahraga = (Button)findViewById(R.id.btnOlahragaAktif);
        txtJam =(TextView)findViewById(R.id.txtJam);

        Intent a = getIntent();
        // Ambil String data Intent dari setOlahragaActivity
        id = a.getStringExtra("id_olahraga");
        kkal = Double.parseDouble(a.getStringExtra("kkal"));
        Nama_olg = a.getStringExtra("nama_olahraga");
        keterangan = a.getStringExtra("keterangan");
        hasilKal = Double.parseDouble(a.getStringExtra("hasilKal"));
        waktu = Double.parseDouble(a.getStringExtra("waktu"));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        showData();

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_NORMAL);


        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        mHandler = new Handler();
        buttonState = 1;
        laps = "";
        lapsCount = 0;
        btnOlahraga.setText("MULAI");
        countKal(false);
        btnOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countKal(true);
                if (buttonState == 1){
                    mStarted = true;
                    mHandler.postDelayed(mRunnable, 10L);
                    start_time = System.currentTimeMillis();
                    laps = "";
                    buttonState = 2;
                    btnOlahraga.setText("Berhenti");
                }else if (buttonState == 2){
                    //txtJam.setText(String.format("%02d:%02d", 0, 0));
                    Intent i = new Intent(getApplicationContext(), reportOlahragaAktif.class);
                    i.putExtra("id_olahraga", id);
                    i.putExtra("nama_olahraga", Nama_olg);
                    i.putExtra("kkalTerbakar", txtKalTerbakar.getText().toString());
                    i.putExtra("waktu", txtJam.getText().toString());
                    startActivity(i);
                    mStarted = false;
                    countKal(false);
                }
            }
        });
    }

    final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mStarted){
                long millis = (System.currentTimeMillis() - start_time);
                long seconds = millis / 1000;
                txtJam.setText(String.format("%02d:%02d", seconds / 60, seconds % 60));
                mHandler.postDelayed(mRunnable, 10L);
            }
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        //cek jenis sensor
        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float xc = event.values[0];
            float yc = event.values[1];
            float zc = event.values[2];
            float step = event.values.length;

            acelLast = acelVal;
            acelVal = ((float) Math.sqrt((double) (xc*xc) + (yc*yc) + (zc*zc)));
            float delta = acelVal - acelLast;
            shake = shake * 0.9f + delta;

            double a = kkal / 60;
            if (buttonState == 2){
                countKal(true);
            } else {
                countKal(false);
            }

            String ab = txtKalTerbakar.getText().toString();
//            if (Double.parseDouble(ab) > hasilKal){
//                countKal(false);
//            } else {
//            }

            float total = event.values[0] + event.values[1] + event.values[2];
            totalcoor.setText("shake" + shake + "\n" + "total : " +total);

        } else {
            txtKalTerbakar.setText("Perangkat tidak mendukung sensor gerak");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void showData(){
        txtNamaOlahraga.setText(Nama_olg);
        txtKalori.setText(String.valueOf(hasilKal));
        txtKalTerbakar.setText("0");
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

            } else if (shake > 12) { //Menjalankan kondisi jika gerakan keras terdeteksi
                Log.d("shake2", String.valueOf(shake));
                Log.d("shake3", String.valueOf(satu));
                Log.d("shake3", String.valueOf(kkaljd));
                kkaljd = satu + dua;
                txtKalTerbakar.setText(String.valueOf(kkaljd));
            }  else {

            }
        }
    }

}
