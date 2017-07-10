package com.example.sam_boncel.kalkulatorgizi;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */

public class DetailRecordFragment extends Fragment {
    public User users_login;
    TextView tv, tgl, txt_total, txt_totalolg, rowolg;
    String tanggal = "";
    String kalori = "";

    public DetailRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_record, container, false);
        tv = (TextView) rootView.findViewById(R.id.row);
        tgl = (TextView) rootView.findViewById(R.id.tanggal);
        txt_total = (TextView) rootView.findViewById(R.id.txt_total);

        txt_totalolg = (TextView) rootView.findViewById(R.id.txt_totalolg);
        rowolg= (TextView) rootView.findViewById(R.id.rowolg);
        //Log.d("haha", users_login.getId_user());
        //Log.d("haha", users_login.getId_user());
        loadDataUsersLogin();
        get_recordmkn();
        get_recordolg();
        //loadText();
        return rootView;
    }

    public void get_recordmkn(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            tanggal = bundle.getString("tgl");
            kalori = bundle.getString("kalori");
        }
        Log.d("haha", tanggal);
        Log.d("haha", kalori);

        FormData data = new FormData();
        data.add("method", "get_recordMknDay");
        data.add("tanggal", tanggal);
        data.add("id_user", users_login.getId_user());
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray nv =jsonObject.getJSONArray("data");
                        //Log.d("haha", String.valueOf(nv.length()));
                        JSONObject Jo = nv.getJSONObject(0);
                        String s = "";
                        tgl.setText(Jo.getString("tanggal"));
                        for (int x=0; x<nv.length(); x++){
                            JSONObject jo = nv.getJSONObject(x);
                            //s += jo.getString("tanggal");
                            s += ((x+1) + ". makan ");
                            s += jo.getString("kat_waktu");

                            s += (": ");
                            s += jo.getString("nama_makanan");

                            s += (" konsumsi ");
                            s += jo.getString("kalori");
                            s += ("kal " + "\n");
                        }
                        tv.setMovementMethod(new ScrollingMovementMethod());
                        tv.setText(s);
                    }else{
                        Toast.makeText(getContext(),"Gagal get Data", Toast.LENGTH_SHORT).show();
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

    public void get_recordolg(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            tanggal = bundle.getString("tgl");
        }
        FormData data = new FormData();
        data.add("method", "get_recordOlgDay");
        data.add("id_user", users_login.getId_user());
        data.add("tanggal", tanggal);
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray nv =jsonObject.getJSONArray("data");
                        //Log.d("haha", String.valueOf(nv.length()));
                        JSONObject Jo = nv.getJSONObject(0);
                        String s = "";
                        //tgl.setText(Jo.getString("tanggal"));
                        for (int x=0; x<nv.length(); x++){
                            JSONObject jo = nv.getJSONObject(x);
                            //s += jo.getString("tanggal");
                            s += ("Olahraga : ");
                            s += jo.getString("nama_olahraga");
                            s += ("\n");

                            s += (" membakar : ");
                            s += jo.getString("kalori");
                            s += ("kal ");
                            s += ("\n");

                            s += ("Durasi : ");
                            s += jo.getString("waktu");
                            s += ("\n");
                        }
                        rowolg.setMovementMethod(new ScrollingMovementMethod());
                        rowolg.setText(s);
                    }else{
                        Toast.makeText(getContext(),"Gagal get Data", Toast.LENGTH_SHORT).show();
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

    private void loadText() {
        String s = "";
        for (int x=0; x<100; x++){
            s += "Line ke " + String.valueOf(x) +"\n";
        }
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(s);
    }

    public boolean loadDataUsersLogin(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("data_private", 0);
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
