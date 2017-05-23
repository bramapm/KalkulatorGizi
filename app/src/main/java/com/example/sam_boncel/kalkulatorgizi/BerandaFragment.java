package com.example.sam_boncel.kalkulatorgizi;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.entities.Makanan;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {
    TextView txtNama, txtKalori, txtMkn, txtOlg, kalorimkn, kaloriolg;
    public User users_login;
    public String klikTgl = "a";
    public BerandaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);
        txtNama = (TextView)rootView.findViewById(R.id.txt_nama);
        txtKalori = (TextView)rootView.findViewById(R.id.txt_kalori);
        txtMkn = (TextView)rootView.findViewById(R.id.txtMkn);
        txtOlg = (TextView)rootView.findViewById(R.id.txtOlg);
        kalorimkn = (TextView)rootView.findViewById(R.id.kalorimkn);
        kaloriolg = (TextView)rootView.findViewById(R.id.kaloriolg);

        loadDataUsersLogin();

        txtNama.setText(users_login.getNama_user());
        txtKalori.setText(users_login.getKalori());

        if ((users_login.getKalori().toString().length() <= 1)) {
            Toast.makeText(getContext(), "Data 0 atau null", Toast.LENGTH_SHORT).show();
            showConfirmation();
        } else {
            Toast.makeText(getContext(), "Sudah ada Data", Toast.LENGTH_SHORT).show();
            showInputMakanan();
        }

        CalendarView calendarView=(CalendarView) rootView.findViewById(R.id.kalender);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                Toast.makeText(getContext(),year +"-"+ (month+1) +"-"+ dayOfMonth, 0).show();
                klikTgl = year +"-"+ (month+1) +"-"+ dayOfMonth;
                Log.d("tes", klikTgl);
                kalOnClick();
                //txtMkn.setText(klikTgl.toString());


            }
        });


        txtOlg.setText("pagi");


        return rootView;
    }

    public void kalOnClick(){
        FormData data = new FormData();
        data.add("method", "countKaloriMknTotal");
        data.add("id_user", users_login.getId_user());
        data.add("tanggal", klikTgl);
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray nv =jsonObject.getJSONArray("data");
                        JSONObject jo = nv.getJSONObject(0);
                        String ss = jo.getString("kalori");
                        kalorimkn.setText(ss);
                    }else{
                        Toast.makeText(getContext(),"Gagal get Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    //Snackbar.make(clContent, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
                //Snackbar.make(clContent, internetTask.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });
        uploadTask.execute();
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

    public void getMakanan(){
        FormData data = new FormData();
        data.add("method", "get_recordMkn");
        InternetTask uploadTask = new InternetTask("Makanan", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                    }else{

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

    public void showConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Silahkan Update Data Anda");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar1.setTitle("Data Diri");
                DataDiriFragment dataDiriFragment = new DataDiriFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        dataDiriFragment).commit();
            }
        });

        builder.show();
    } //end showconfir

    public void showInputMakanan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Sudah makan apa saja hari ini?");
        builder.setPositiveButton("Input", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar1.setTitle("Input Makanan");
                InputMakananFragment imFragment = new InputMakananFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        imFragment).commit();
            }
        });

        builder.show();
    } //en
}
