package com.example.sam_boncel.kalkulatorgizi;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.MultiAutoCompleteTextView;
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
    TextView txtNama, txtKalori, namaOlg, namaMkn, kaloriMkn, kaloriOlg;
    CalendarView kalender;
    public User users_login;
    public BerandaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_beranda, container, false);
        txtNama = (TextView)rootView.findViewById(R.id.txt_nama);
        txtKalori = (TextView)rootView.findViewById(R.id.txt_kalori);
        namaOlg = (TextView)rootView.findViewById(R.id.txtOlg);
        namaMkn = (TextView)rootView.findViewById(R.id.txtMkn);
        kaloriMkn = (TextView)rootView.findViewById(R.id.kalorimkn);
        kaloriOlg = (TextView)rootView.findViewById(R.id.kaloriolg);

//        kalender = (CalendarView) rootView.findViewById(R.id.kalender);
//        kalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//
//            }
//        });
//        dateDisplay.setText("Tanggal: ");
//        getRecordOlg();
//        getRecordMkn();
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
        return rootView;
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

    public void showConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Silahkan Update Data Anda");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar1.setTitle("Pengaturan");
                PengaturanFragment pengaturanFragment = new PengaturanFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        pengaturanFragment).commit();
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


    public void getRecordOlg(){
        FormData data = new FormData();
        data.add("method", "get_recordOlg");
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray nv = jsonObject.getJSONArray("data");
                        JSONObject jo = nv.getJSONObject(0);
                        String ss = jo.getString("id_olahraga");
                        String sq = jo.getString("kalori");
                        namaOlg.setText(ss);
                        kaloriOlg.setText(sq);
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

    public void getRecordMkn(){
        FormData data = new FormData();
        data.add("method", "get_recordOlg");
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        JSONArray nv = jsonObject.getJSONArray("data");
                        JSONObject jo = nv.getJSONObject(0);
                        String ss = jo.getString("id_makanan");
                        String sq = jo.getString("kalori");
                        namaMkn.setText(ss);
                        kaloriMkn.setText(sq);
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
}
