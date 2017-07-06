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
import android.text.method.ScrollingMovementMethod;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputMakananFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputMakananFragment extends Fragment{
    public User users_login;
    public Makanan makanan;
    public Record_mkn record_mkn;
    private ArrayList<Makanan> listMakanan;
    public ArrayAdapter<String> adapter;
    ListView lv;
    public Button btnPagi, btnSiang, btnMlm, btnLain, btnProses;
    public TextView txtKaloributuh, txtKalorikonsumsi, txtPagi, txtSiang, txtMlm, txtLain;
    public EditText txtSearch;
    private TextView detail1, row1, detail2, row2, detail3, row3, detail4, row4;

    String kat_waktu = "";
    String id_mkn = "";
    String s;
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
        detail1 = (TextView) rootView.findViewById(R.id.detail1);
        row1 = (TextView) rootView.findViewById(R.id.row1);
        detail2 = (TextView) rootView.findViewById(R.id.detail2);
        row2 = (TextView) rootView.findViewById(R.id.row2);
        detail3 = (TextView) rootView.findViewById(R.id.detail3);
        row3 = (TextView) rootView.findViewById(R.id.row3);
        detail4 = (TextView) rootView.findViewById(R.id.detail4);
        row4 = (TextView) rootView.findViewById(R.id.row4);

        txtKaloributuh = (TextView) rootView.findViewById(R.id.kaloributuh);
        txtKalorikonsumsi = (TextView) rootView.findViewById(R.id.kalorikonsumsi);
        txtPagi = (TextView) rootView.findViewById(R.id.txtPagi);
        txtSiang = (TextView) rootView.findViewById(R.id.txtSiang);
        txtMlm = (TextView) rootView.findViewById(R.id.txtMalam);
        txtLain = (TextView) rootView.findViewById(R.id.txtLain);
        row1.setVisibility(View.GONE);
        row2.setVisibility(View.GONE);
        row3.setVisibility(View.GONE);
        row4.setVisibility(View.GONE);
        loadDataUsersLogin();


        btnPagi = (Button)rootView.findViewById(R.id.btnPagi);
        btnPagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertdialog buat popup
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                kat_waktu = "pagi";
                lv = (ListView) mView.findViewById(R.id.listViewMkn);
                mBuilder.setView(mView);// * popup
                final AlertDialog dialog = mBuilder.create();// * popup
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                       sumKalMkn();
                        sumKalMknTotal();
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
                dialog.show(); // *akhir popup
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Makanan selecetedMakanan = listMakanan.get(position);
                        String ss = selecetedMakanan.getId_makanan().toString();
                        id_mkn = ss;
                        saveRecordMkn();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnSiang = (Button)rootView.findViewById(R.id.btnSiang);
        btnSiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                kat_waktu = "siang";
                lv = (ListView) mView.findViewById(R.id.listViewMkn);
                mBuilder.setView(mView);// * popup
                final AlertDialog dialog = mBuilder.create();// * popup
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sumKalMkn();
                        sumKalMknTotal();
                    }
                });
                //buat search
                txtSearch = (EditText) mView.findViewById(R.id.search);
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
                dialog.show(); // *akhir popup
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Makanan selecetedMakanan = listMakanan.get(position);
                        String ss = selecetedMakanan.getId_makanan().toString();
                        id_mkn = ss;
                        saveRecordMkn();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnMlm = (Button)rootView.findViewById(R.id.btnMalam);
        btnMlm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                kat_waktu = "malam";
                lv = (ListView) mView.findViewById(R.id.listViewMkn);
                mBuilder.setView(mView);// * popup
                final AlertDialog dialog = mBuilder.create();// * popup
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sumKalMkn();
                        sumKalMknTotal();
                    }
                });
                //buat search
                txtSearch = (EditText) mView.findViewById(R.id.search);
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
                dialog.show(); // *akhir popup
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Makanan selecetedMakanan = listMakanan.get(position);
                        String ss = selecetedMakanan.getId_makanan().toString();
                        id_mkn = ss;
                        saveRecordMkn();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnLain = (Button) rootView.findViewById(R.id.btnLain);
        btnLain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater(savedInstanceState).inflate(R.layout.activity_pilih_makanan, null);
                kat_waktu = "lain";
                lv = (ListView) mView.findViewById(R.id.listViewMkn);
                mBuilder.setView(mView);// * popup
                final AlertDialog dialog = mBuilder.create();// * popup
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sumKalMkn();
                        sumKalMknTotal();
                    }
                });
                //buat search
                txtSearch = (EditText) mView.findViewById(R.id.search);
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
                dialog.show(); // *akhir popup
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Makanan selecetedMakanan = listMakanan.get(position);
                        String ss = selecetedMakanan.getId_makanan().toString();
                        id_mkn = ss;
                        saveRecordMkn();
                        dialog.dismiss();
                    }
                });
            }
        });

        sumKalMkn();
        sumKalMknTotal();

        detail1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      //row.setText("oke klikked");
                      kat_waktu = "pagi";
                      if (detail1.getText().equals("Klik untuk detail")) {
                          getDetail(kat_waktu);
                          row1.setMovementMethod(new ScrollingMovementMethod());
                          row1.setText(s);
                          row1.setVisibility(View.VISIBLE);
                          detail1.setText("Tutup");
                      } else {
                          row1.setVisibility(View.GONE);
                          detail1.setText("Klik untuk detail");
                      }
                  }
              });

        detail2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //row.setText("oke klikked");
                kat_waktu = "siang";
                if (detail2.getText().equals("Klik untuk detail")) {
                    getDetail(kat_waktu);
                    row2.setMovementMethod(new ScrollingMovementMethod());
                    row2.setText(s);
                    row2.setVisibility(View.VISIBLE);
                    detail2.setText("Tutup");
                } else {
                    row2.setVisibility(View.GONE);
                    detail2.setText("Klik untuk detail");
                }
            }
        });

        detail3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //row.setText("oke klikked");
                kat_waktu = "malam";
                if (detail3.getText().equals("Klik untuk detail")) {
                    getDetail(kat_waktu);
                    row3.setMovementMethod(new ScrollingMovementMethod());
                    row3.setText(s);
                    row3.setVisibility(View.VISIBLE);
                    detail3.setText("Tutup");
                } else {
                    row3.setVisibility(View.GONE);
                    detail3.setText("Klik untuk detail");
                }
            }
        });

        detail4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //row.setText("oke klikked");
                kat_waktu = "lain";
                if (detail4.getText().equals("Klik untuk detail")) {
                    getDetail(kat_waktu);
                    row4.setMovementMethod(new ScrollingMovementMethod());
                    row4.setText(s);
                    row4.setVisibility(View.VISIBLE);
                    detail4.setText("Tutup");
                } else {
                    row4.setVisibility(View.GONE);
                    detail4.setText("Klik untuk detail");
                }
            }
        });

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
//    End OncreatView

    public void showConfirmationOlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final double hasil = Double.parseDouble(txtKalorikonsumsi.getText().toString()) - Double.parseDouble(txtKaloributuh.getText().toString());

        DecimalFormat df = new DecimalFormat("0.00");
        final String hasilAkhir = df.format(hasil);

        builder.setMessage("Kalori Anda Kelebihan " + hasilAkhir + " Silahkan Pilih Saran Olahraga");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar1.setTitle("Olahraga");
                PilihOlahragaFragment pilihOlahragaFragment = new PilihOlahragaFragment();
                Bundle bundle = new Bundle();
                bundle.putString("hasil", hasilAkhir);
                pilihOlahragaFragment.setArguments(bundle);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                manager.beginTransaction().replace(
                        R.id.relativelayout_for_fragment,
                        pilihOlahragaFragment).commit();
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

        DecimalFormat df = new DecimalFormat("0.00");
        final String hasilAkhir = df.format(hasil);

        builder.setMessage("Anda Kekurangan Kalori Sebanyak " + hasilAkhir + " Silahkan Pilih Saran Makanan");
//        Log.d("KEM", "KON:"+String.valueOf(finalHasil));
        Log.d("KEM", "KON:"+String.valueOf(hasil));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toolbar toolbar1 = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar1.setTitle("Saran Makanan");
                SaranMakan SaranMknFragment = new SaranMakan();
                Bundle bundle = new Bundle();
                bundle.putString("hasil", hasilAkhir);
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
                            Toast.makeText(getContext(),"Sukses Insert Data", Toast.LENGTH_SHORT).show();

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

    public void getDetail(String wkt){
        FormData data = new FormData();
        data.add("method", "getMknWaktu");
        data.add("kat_waktu", wkt);
        data.add("tanggal", getTanggal().toString());
        data.add("id_user", users_login.getId_user().toString());
        InternetTask uploadTask = new InternetTask("Record", data);
        uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
            @Override
            public void OnInternetTaskFinished(InternetTask internetTask) {
                try {
                    JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                    if (jsonObject.get("code").equals(200)){

                        JSONArray nv =jsonObject.getJSONArray("data");
                        JSONObject Jo = nv.getJSONObject(0);
                        s = "";
                        for (int x=0; x<nv.length(); x++){
                            JSONObject jo = nv.getJSONObject(x);
                            //s += jo.getString("tanggal");

                            s += ((x+1) + ". ");

                            s += jo.getString("nama_makanan");

                            s += (" konsumsi ");
                            s += jo.getString("kalori");
                            s += ("kal " + "\n");
                        }
                    }else{
                        Toast.makeText(getContext(),"Gagal get Data", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {

                }
//                kat_waktu = "";
            }

            @Override
            public void OnInternetTaskFailed(InternetTask internetTask) {
            }
        });
        uploadTask.execute();
    }

}
