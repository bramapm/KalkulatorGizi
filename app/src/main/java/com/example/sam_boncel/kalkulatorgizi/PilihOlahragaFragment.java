package com.example.sam_boncel.kalkulatorgizi;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PilihOlahragaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PilihOlahragaFragment extends Fragment {
    public ListView listView;
    TextView txtNama, txtKkal, txtKeterangan;
    EditText txtSearch;
    ImageView imageView;
    String hasilKal;
    public ArrayAdapter<String> adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Olahraga> listOlahraga;
    public User users_login;

    public PilihOlahragaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PilihOlahragaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PilihOlahragaFragment newInstance(String param1, String param2) {
        PilihOlahragaFragment fragment = new PilihOlahragaFragment();
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
        View rootView =  inflater.inflate(R.layout.fragment_pilih_olahraga, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        txtSearch = (EditText) rootView.findViewById(R.id.search);

        Bundle bundle = getArguments();
        if (bundle != null){
            hasilKal = bundle.getString("hasil");
            Log.d("hasil", String.valueOf(hasilKal));
        }

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Olahraga selectedOlahraga = listOlahraga.get(position);
                Intent i = new Intent(getContext(), SetOlahragaActivity.class);
                i.putExtra("id_olahraga", selectedOlahraga.getId_olahraga());
                i.putExtra("nama_olahraga", selectedOlahraga.getNama_olahraga());
                i.putExtra("kkal", selectedOlahraga.getKkal());
                i.putExtra("keterangan", selectedOlahraga.getKeterangan());
                i.putExtra("foto", selectedOlahraga.getFoto());
                i.putExtra("hasilKal", String.valueOf(hasilKal));
                startActivity(i);
            }
        });

        this.listOlahraga = new ArrayList<>();
        getOlahraga();
        return rootView;
    }
    public void getOlahraga(){
        FormData data = new FormData();
        data.add("method", "get_olahraga");
        InternetTask uploadTask = new InternetTask("Olahraga", data);
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
                                Olahraga olahraga = new Olahraga(jsonArray.getJSONObject(i));
                                listOlahraga.add(new Olahraga(jsonArray.getJSONObject(i)));
                                listpost.add(olahraga.getNama_olahraga().toString());
                            }
                            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, listpost);
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
