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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {
    TextView txtNama, txtKalori;
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

        loadDataUsersLogin();

        txtNama.setText(users_login.getNama_user());
        txtKalori.setText("Kebutuhan kalori anda\n" + users_login.getKalori() + "\n per hari");

        if ((users_login.getKalori().toString().length() <= 1)) {
            Toast.makeText(getContext(), "Data 0 atau null", Toast.LENGTH_SHORT).show();
            showConfirmation();
        } else {
            Toast.makeText(getContext(), "Sudah ada Data", Toast.LENGTH_SHORT).show();
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
        builder.setMessage("Silahkan Update Data Anda!");
        builder.setPositiveButton("Ya!", new DialogInterface.OnClickListener() {
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
}
