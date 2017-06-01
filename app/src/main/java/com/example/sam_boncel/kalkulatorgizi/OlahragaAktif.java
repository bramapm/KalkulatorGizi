package com.example.sam_boncel.kalkulatorgizi;

import android.hardware.SensorEvent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class OlahragaAktif extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private CountDownTimer countDownTimer;
    TextView xcoor, ycoor, zcoor, totalcoor, progress;
    private float acelVal, acelLast, shake;
    String str = "";
    Double kal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olahraga_aktif);

        xcoor = (TextView)findViewById(R.id.xcoor);
        ycoor = (TextView)findViewById(R.id.ycoor);
        zcoor = (TextView)findViewById(R.id.zcoor);
        totalcoor = (TextView)findViewById(R.id.totalcoor);
        progress =(TextView)findViewById(R.id.progress);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

//        Toast.makeText(getApplicationContext(), String.valueOf(acelVal), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), String.valueOf(acelLast), Toast.LENGTH_SHORT).show();

        countDownTimer = new myCountDown(30000, 1000);
        progress.setText(String.valueOf(30000/1000));
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

            if (shake > 0 && shake <= 2) { //tidak melakukan apa" atau bergerak sangat pelan
                //progress.setText("Berhenti");
            countDownTimer.start();
            } else if (shake > 2 && shake <= 7){ //Menjalankan kondisi jika gerakan pelan terdeteksi
                //Log.d("shake2111", String.valueOf(shake));
//                progress.setText("Bergerak pelan");
                double kalori =0 , kkal =0;
                kal = kalori + (kkal / 0.5);
            } else if (shake > 7){ //Menjalankan kondisi jika gerakan keras terdeteksi
                Log.d("shake2", String.valueOf(shake));
//                progress.setText("Gerak Cepat");
                double kalori =0 , kkal =0;
                kal = kalori + (kkal);

            } else {

            }

            float total = event.values[0] + event.values[1] + event.values[2];
            totalcoor.setText("shake" + shake + "\n" + "total : " +total);

        } else {
            xcoor.setText("X : -");
            ycoor.setText("Y : -");
            zcoor.setText("Z : -" + "\n" + "Perangkat ini tidak mendukung sensor Accelerometer");
        }
    }

    public class myCountDown extends CountDownTimer {
        public myCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            progress.setText("" + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            progress.setText("Time's up!");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
