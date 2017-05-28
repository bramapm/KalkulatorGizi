package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailRecordActivity extends AppCompatActivity {
    TextView tv;
    User users_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_record);
        tv = (TextView) findViewById(R.id.percobaan);
        get_record();
        //loadText();
    }

    private void get_record(){
        FormData data = new FormData();
        data.add("method", "get_recordMknDay");
        data.add("tanggal", "2017-05-24");
        data.add("id_user", users_login.getId_user().toString());
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray nv =jsonObject.getJSONArray("data");
                        Log.d("haha", String.valueOf(nv.length()));

                        String s = "";
                        for (int x=0; x<nv.length(); x++){
                            JSONObject jo = nv.getJSONObject(x);
                            s += "Data ke " + x +"\n";
                        }
                        tv.setMovementMethod(new ScrollingMovementMethod());
                        tv.setText(s);
                    }else{
                        Toast.makeText(getApplicationContext(),"Gagal get Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
            }
        });
        uploadTask.execute();
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
