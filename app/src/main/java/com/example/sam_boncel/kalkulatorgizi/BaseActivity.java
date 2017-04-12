package com.example.sam_boncel.kalkulatorgizi;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseActivity extends AppCompatActivity {
    public User users_login;

    @Override
    public void setContentView(int layout){
        super.setContentView(layout);
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
}
