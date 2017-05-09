package com.example.sam_boncel.kalkulatorgizi;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.entities.Makanan;
import com.example.sam_boncel.kalkulatorgizi.entities.Record_mkn;
import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputMakananFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputMakananFragment extends Fragment {
    public User users_login;
    public Makanan makanan;
    public Record_mkn record_mkn;
    private ArrayList<Makanan> listMakanan;
    public ArrayAdapter<String> adapter;
    ListView lv;
    public Button btnPagi, btnSiang, btnMlm, btnLain, btnProses;
    public TextView txtKaloributuh, txtKalorikonsumsi, txtPagi, txtSiang, txtMlm, txtLain;
    public EditText txtSearch;
    private TextView cobaUmur;

    String kat_waktu = "";
    String id_mkn = "";
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
//            sumKalMkn();

        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_input_makanan, container, false);
        TextView cobaUmur = (TextView) rootView.findViewById(R.id.cobaUmur);
        btnPagi = (Button)rootView.findViewById(R.id.btnPagi);
        btnPagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertdialog buat popup
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                kat_waktu = "pagi";
                Button btnSimpan = (Button) mView.findViewById(R.id.btnSimpan);
                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveRecordMkn();
                    }
                });
                lv = (ListView) mView.findViewById(R.id.listViewMkn);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                          Makanan selecetedMakanan = listMakanan.get(position);
                          String ss = selecetedMakanan.getId_makanan().toString();
                          Toast.makeText(getContext(), "Yang dipilih 3"+ ss, Toast.LENGTH_SHORT).show();
                          id_mkn = ss;
                      }
                  });
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
                kat_waktu = "siang";
                Button btnSimpan = (Button) mView.findViewById(R.id.btnSimpan);
                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveRecordMkn();
                    }
                });
                lv = (ListView) mView.findViewById(R.id.listViewMkn);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Makanan selecetedMakanan = listMakanan.get(position);
                        String ss = selecetedMakanan.getId_makanan().toString();
                        Toast.makeText(getContext(), "Yang dipilih 3"+ ss, Toast.LENGTH_SHORT).show();
                        id_mkn = ss;
                    }
                });
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
                kat_waktu = "malam";
                Button btnSimpan = (Button) mView.findViewById(R.id.btnSimpan);
                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveRecordMkn();
                    }
                });
                lv = (ListView) mView.findViewById(R.id.listViewMkn);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Makanan selecetedMakanan = listMakanan.get(position);
                        String ss = selecetedMakanan.getId_makanan().toString();
                        Toast.makeText(getContext(), "Yang dipilih 3"+ ss, Toast.LENGTH_SHORT).show();
                        id_mkn = ss;
                    }
                });
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
                kat_waktu = "lain";
                Button btnSimpan = (Button) mView.findViewById(R.id.btnSimpan);
                btnSimpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveRecordMkn();
                    }
                });
                lv = (ListView) mView.findViewById(R.id.listViewMkn);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Makanan selecetedMakanan = listMakanan.get(position);
                        String ss = selecetedMakanan.getId_makanan().toString();
                        Toast.makeText(getContext(), "Yang dipilih 3"+ ss, Toast.LENGTH_SHORT).show();
                        id_mkn = ss;
                    }
                });
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

        loadDataUsersLogin();

        sumKalMkn();
        sumKalMknTotal();
        txtKaloributuh = (TextView) rootView.findViewById(R.id.kaloributuh);
        txtKalorikonsumsi = (TextView) rootView.findViewById(R.id.kalorikonsumsi);
        txtPagi = (TextView) rootView.findViewById(R.id.txtPagi);
        txtSiang = (TextView) rootView.findViewById(R.id.txtSiang);
        txtMlm = (TextView) rootView.findViewById(R.id.txtMlm);
        txtLain = (TextView) rootView.findViewById(R.id.txtLain);

        //DecimalFormat aaa = new DecimalFormat("#.##");
        //Double keb = Double.valueOf(users_login.getKalori());
        //String.format();
        //txtKaloributuh.setText(String.format("%.2f", keb));
        txtKaloributuh.setText(users_login.getKalori());

        btnProses = (Button) rootView.findViewById(R.id.btnProses);
        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtKalorikonsumsi.getText().toString() == txtKaloributuh.getText().toString()){
                    Toast.makeText(getContext(), "Asupan Kalori Anda Baik", Toast.LENGTH_SHORT).show();
                }else if(Double.parseDouble(txtKalorikonsumsi.getText().toString()) >= Double.parseDouble(txtKaloributuh.getText().toString())){
                    showConfirmationOlg();
                }else if(Double.parseDouble(txtKalorikonsumsi.getText().toString()) <= Double.parseDouble(txtKaloributuh.getText().toString())){
                    showConfirmationMkn();
                }
            }
        });
        return rootView;
    }

    public void showConfirmationOlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final double hasil = Double.parseDouble(txtKalorikonsumsi.getText().toString()) - Double.parseDouble(txtKaloributuh.getText().toString());
        builder.setMessage("Kalori Anda Kelebihan " + hasil + " Silahkan Pilih Saran Olahraga");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar1.setTitle("Olahraga");
                OlahragaFragment olahragaFragment= new OlahragaFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        olahragaFragment).commit();
            }
        });

        builder.show();
    } //end showconfir

    public void showConfirmationMkn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        double hasil = Double.parseDouble(txtKalorikonsumsi.getText().toString()) - Double.parseDouble(txtKaloributuh.getText().toString());
        if (hasil < 0.0){
            hasil *= (-1);
        }
        builder.setMessage("Anda Kekurangan Kalori Sebanyak " + hasil + " Silahkan Pilih Saran Makanan");
        final double finalHasil = hasil;
        Log.d("KEM", "KON:"+String.valueOf(finalHasil));
        Log.d("KEM", "KON:"+String.valueOf(hasil));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar1.setTitle("Saran Makanan");
                SaranMakan SaranMknFragment = new SaranMakan();
                Bundle bundle = new Bundle();
                bundle.putDouble("hasil", finalHasil);
                SaranMknFragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        SaranMknFragment).commit();
            }
        });
        builder.show();
    } //end showconfir




    private void onCheckedChanged(CompoundButton button, boolean isCheck){
        int pos = lv.getPositionForView(button);
        if (pos != ListView.INVALID_POSITION){
            Makanan m = listMakanan.get(pos);
            m.setCheck(isCheck);

        Toast.makeText(getContext(), "Makanan yang dipilih" + m.getNama_makanan() + isCheck, Toast.LENGTH_SHORT).show();

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

    public void saveRecordMkn() {
            FormData data = new FormData();
            data.add("method", "insertMkn");
            data.add("id_user", users_login.getId_user());
            data.add("id_makanan", id_mkn); //id makanan yang di select
            data.add("kat_waktu", kat_waktu);
            data.add("tanggal", getTanggal());
            InternetTask uploadTask = new InternetTask("Record", data);
            uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
                @Override
                public void OnInternetTaskFinished(InternetTask internetTask) {
                    try {
                        JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                        if (jsonObject.get("code").equals(200)){
                            //btnRegister.setClickable(false);

                            //Snackbar.make(,"Registrasi Sukses", Snackbar.LENGTH_SHORT).show();
                            //Snackbar.make(this, "Registration Success", Snackbar.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"Gagal", Toast.LENGTH_SHORT).show();
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

    private void sumKalMkn(){
        FormData data = new FormData();
        data.add("method", "countKaloriMkn");
        data.add("id_user", users_login.getId_user());
        data.add("tanggal", getTanggal());
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        //btnRegister.setClickable(false);
                        JSONArray nv =jsonObject.getJSONArray("data");
                        txtPagi.setText(String.valueOf(nv.get(0)));
                        txtSiang.setText(String.valueOf(nv.get(1)));
                        txtMlm.setText(String.valueOf(nv.get(2)));
                        txtLain.setText(String.valueOf(nv.get(3)));
                        //Toast.makeText(getContext(),"Sukses" + nv.toString() , Toast.LENGTH_SHORT).show();
                        //Snackbar.make(,"Registrasi Sukses", Snackbar.LENGTH_SHORT).show();
                        //Snackbar.make(this, "Registration Success", Snackbar.LENGTH_SHORT).show();
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

    private void sumKalMknTotal(){
        FormData data = new FormData();
        data.add("method", "countKaloriMknTotal");
        data.add("id_user", users_login.getId_user());
        data.add("tanggal", getTanggal());
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){
                        //btnRegister.setClickable(false);
                        JSONArray nv = jsonObject.getJSONArray("data");
                        JSONObject jo = nv.getJSONObject(0);
                        String ss = jo.getString("kalori");
                        //String jo = nv.get(0).toString();
                        txtKalorikonsumsi.setText(ss);
                        //Toast.makeText(getContext(),"Sukses" + nv.toString() , Toast.LENGTH_SHORT).show();
                        //Snackbar.make(,"Registrasi Sukses", Snackbar.LENGTH_SHORT).show();
                        //Snackbar.make(this, "Registration Success", Snackbar.LENGTH_SHORT).show();
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

    private String getTanggal() {
        Date now = new Date();
        SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = frmt.format(now);
        return dateString;
    }
}
