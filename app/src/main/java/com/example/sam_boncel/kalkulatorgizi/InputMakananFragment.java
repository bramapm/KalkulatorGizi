package com.example.sam_boncel.kalkulatorgizi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
 * Use the {@link InputMakananFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputMakananFragment extends Fragment {
    public User users_login;
    private ArrayList<Makanan> listMakanan;
    public ArrayAdapter<String> adapter;
    ListView lv;
    public Button btnPagi, btnSiang, btnMlm, btnLain, btnProses;
    public TextView txtKaloributuh, txtKalorikonsumsi, txtPagi, txtSiang, txtMlm, txtLain;
    public EditText txtSearch;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public InputMakananFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputMakananFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputMakananFragment newInstance(String param1, String param2) {
        InputMakananFragment fragment = new InputMakananFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_input_makanan, container, false);
        btnPagi = (Button)rootView.findViewById(R.id.btnPagi);
        btnPagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertdialog buat popup
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                lv = (ListView) mView.findViewById(R.id.listViewaa);
                txtSearch = (EditText) mView.findViewById(R.id.search);
                //buat search
                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    } //akhir search
                });
                listMakanan = new ArrayList<>();
                getMakanan();
                mBuilder.setView(mView);// * popup
                AlertDialog dialog = mBuilder.create();// * popup
                dialog.show(); // *akhir popup
//                Log.d("A", "A");
            }
        });
        btnSiang = (Button)rootView.findViewById(R.id.btnSiang);
        btnSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                lv = (ListView) mView.findViewById(R.id.listViewaa);
                txtSearch = (EditText) mView.findViewById(R.id.search);
                //buat search
                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    } //akhir search
                });
                listMakanan = new ArrayList<>();
                getMakanan();
                mBuilder.setView(mView);// * popup
                AlertDialog dialog = mBuilder.create();// * popup
                dialog.show(); // *akhir popup
            }
        });
        btnMlm = (Button)rootView.findViewById(R.id.btnMlm);
        btnMlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                lv = (ListView) mView.findViewById(R.id.listViewaa);
                txtSearch = (EditText) mView.findViewById(R.id.search);
                //buat search
                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    } //akhir search
                });
                listMakanan = new ArrayList<>();
                getMakanan();
                mBuilder.setView(mView);// * popup
                AlertDialog dialog = mBuilder.create();// * popup
                dialog.show(); // *akhir popup
            }
        });
        btnLain = (Button) rootView.findViewById(R.id.btnLain);
        btnLain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                lv = (ListView) mView.findViewById(R.id.listViewaa);
                txtSearch = (EditText) mView.findViewById(R.id.search);
                //buat search
                txtSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    } //akhir search
                });
                listMakanan = new ArrayList<>();
                getMakanan();
                mBuilder.setView(mView);// * popup
                AlertDialog dialog = mBuilder.create();// * popup
                dialog.show(); // *akhir popup
            }
        });
        btnProses = (Button) rootView.findViewById(R.id.btnProses);

        loadDataUsersLogin();
        txtKaloributuh = (TextView) rootView.findViewById(R.id.kaloributuh);
        txtKalorikonsumsi = (TextView) rootView.findViewById(R.id.kalorikonsumsi);
        txtPagi = (TextView) rootView.findViewById(R.id.txtPagi);
        txtSiang = (TextView) rootView.findViewById(R.id.txtSiang);
        txtMlm = (TextView) rootView.findViewById(R.id.txtMlm);
        txtLain = (TextView) rootView.findViewById(R.id.txtLain);

        txtKaloributuh.setText(users_login.getKalori());
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


    public void getMakanan(){
        FormData data = new FormData();
        data.add("method", "get_makanan");
        InternetTask uploadTask = new InternetTask("Makanan", data);
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
                                Makanan makanan = new Makanan(jsonArray.getJSONObject(i));
                                listMakanan.add(new Makanan(jsonArray.getJSONObject(i)));
                                listpost.add(makanan.getNama_makanan().toString()+"\n"+ makanan.getKkal().toString()+"kkal");
                            }
                            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listpost);
                            lv.setAdapter(adapter);
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
}
