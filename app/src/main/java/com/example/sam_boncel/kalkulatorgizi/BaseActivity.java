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
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    public User users_login;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private GoogleApiClient mGoogleApiClient;

    @Override
    public void setContentView(int layout){
        super.setContentView(layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shNtif();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (mGoogleApiClient.isConnected()) {
                            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient);
                            //mGoogleApiClient.disconnect();
                            mGoogleApiClient.connect();
                            Intent i = new Intent(BaseActivity.this, LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                    }
                });
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
