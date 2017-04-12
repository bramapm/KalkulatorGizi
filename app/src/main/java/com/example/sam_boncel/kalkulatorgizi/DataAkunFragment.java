package com.example.sam_boncel.kalkulatorgizi;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataAkunFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataAkunFragment extends Fragment {
    EditText txtUsername, txtPass, txtEmail, txtNama;
    Button btnSave;
    public User users_login;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DataAkunFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataAkunFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataAkunFragment newInstance(String param1, String param2) {
        DataAkunFragment fragment = new DataAkunFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_data_akun, container, false);
        txtUsername = (EditText) rootView.findViewById(R.id.txtUsername);
        txtPass = (EditText) rootView.findViewById(R.id.txtPassword);
        txtEmail = (EditText) rootView.findViewById(R.id.txtEmail);
        txtNama = (EditText) rootView.findViewById(R.id.txtNama);

        loadDataUsersLogin();
        txtUsername.setText(users_login.getUsername());
        txtPass.setText(users_login.getPassword());
        txtEmail.setText(users_login.getEmail());
        txtNama.setText(users_login.getNama_user());

        btnSave = (Button) rootView.findViewById(R.id.btnUpdate);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"Update Data Akun Sukses", Toast.LENGTH_SHORT).show();
                onUpdateAkunClick();
            }
        });
        return rootView;
    }

    public void onUpdateAkunClick() {
        if (is_parameters_update_valid()){
            FormData data = new FormData();
            data.add("method", "update_akun");
            data.add("id_user", users_login.getId_user());
            data.add("username", txtUsername.getText().toString());
            data.add("password", txtPass.getText().toString());
            data.add("email", txtEmail.getText().toString());
            data.add("nama_user",txtNama.getText().toString());
            InternetTask uploadTask = new InternetTask("Users", data);
            //Toast.makeText(getContext(),"Weheee", Toast.LENGTH_SHORT).show();
            uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
                @Override
                public void OnInternetTaskFinished(InternetTask internetTask) {
                    try {
                        JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                        if (jsonObject.get("code").equals(200)){
                            Toast.makeText(getContext(),"Update Sukses", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"Update Gagal", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                    }
                }

                @Override
                public void OnInternetTaskFailed(InternetTask internetTask) {
                }
            }
            );
            uploadTask.execute();
        }
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


    private boolean is_parameters_update_valid(){
        if (txtUsername.getText().toString().equals("")){
            txtUsername.requestFocus();
            txtUsername.setError("Please Input Your Username");
            return false;
        }else if (txtPass.getText().toString().equals("")) {
            txtPass.requestFocus();
            txtPass.setError("Please Input Your Password");
            return false;
        }else if(txtNama.getText().toString().equals("")){
            txtNama.requestFocus();
            txtNama.setError("Please Input Your Full Name");
            return false;
        }else if(txtEmail.getText().toString().equals("")){
            txtEmail.requestFocus();
            txtEmail.setError("Please Input Your Email");
            return false;
        }else
            return true;
    }
}
