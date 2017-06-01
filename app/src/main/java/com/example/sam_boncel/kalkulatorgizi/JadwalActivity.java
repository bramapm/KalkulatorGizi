package com.example.sam_boncel.kalkulatorgizi;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import java.util.ArrayList;

public class JadwalActivity extends AppCompatActivity {

    public ArrayList<Jadwal> listJadwal;
    public ArrayAdapter<String> adapter;
    public User users_login;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        this.listJadwal = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView1);
        loadDataUsersLogin();
        getJadwal();
    }

    public void getJadwal(){
        FormData data = new FormData();
        data.add("method", "get_jadwal");
        data.add("id_user", users_login.getId_user());
        InternetTask uploadTask = new InternetTask("Jadwal", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if (jsonArray.length() > 0){
                            ArrayList<String> listpost = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length();i++){
                                Jadwal jadwal= new Jadwal(jsonArray.getJSONObject(i));
                                listJadwal.add(new Jadwal(jsonArray.getJSONObject(i)));
                                listpost.add(jadwal.getTanggal());
                            }
                            adapter = new ArrayAdapter<>(JadwalActivity.this, android.R.layout.simple_list_item_1, listpost);
                            listView.setAdapter(adapter);
                        }
                    }else{
                        //                            Snackbar.make(clContent, "Liked!", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //                       Snackbar.make(clContent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
                //                   Snackbar.make(clContent, internetTask.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
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
