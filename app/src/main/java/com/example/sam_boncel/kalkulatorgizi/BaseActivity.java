package com.example.sam_boncel.kalkulatorgizi;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class BaseActivity extends AppCompatActivity {
    public User users_login;

    @Override
    public void setContentView(int layout){
        super.setContentView(layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shNtif();
    }

    public void saveDataUsersLogin(String data){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("data_private", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data", data);
        editor.commit();
    }


    public void deleteDataUsersLogin(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("data_private", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("data");
        editor.commit();
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

    public void shNtif() {


        //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_makanan) //ikon notification
                .setContentTitle("Judul")//judul konten
                .setContentIntent(pendingIntent)//memanggil object pending intent
                .setAutoCancel(true)//untuk menswipe atau menghapus notification
                .setContentText("Semoga baroka"); //isi text

        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NM.notify(0, builder.build());
    }
}
