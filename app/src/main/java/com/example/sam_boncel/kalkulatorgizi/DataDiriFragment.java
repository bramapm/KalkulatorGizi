package com.example.sam_boncel.kalkulatorgizi;


import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sam_boncel.kalkulatorgizi.entities.User;
import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;
import com.example.sam_boncel.kalkulatorgizi.other.CircleTransform;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataDiriFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataDiriFragment extends Fragment {
    public User users_login;
    EditText txtUsername, txtEmail, txtNama, txtTinggi, txtBerat, txtTTL, txtJK, txtUmur;
    private RadioGroup genderRadio;
    String radioButton = "";
    String date ="";
    ImageView imgProfile;
    ImageButton imgBtn;
    private int mYear, mMonth, mDay;
    Button btnSave;
    int hasil = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DataDiriFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DataDiriFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DataDiriFragment newInstance(String param1, String param2) {
        DataDiriFragment fragment = new DataDiriFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_data_diri, container, false);
        imgProfile = (ImageView) rootView.findViewById(R.id.img_profile);
        txtEmail = (EditText) rootView.findViewById(R.id.txtEmail);
        txtNama = (EditText) rootView.findViewById(R.id.txtNama);
        txtUmur = (EditText) rootView.findViewById(R.id.txtUmur);
        txtJK = (EditText) rootView.findViewById(R.id.txtJK);
        txtTTL = (EditText) rootView.findViewById(R.id.txtTTL);
        txtTinggi = (EditText) rootView.findViewById(R.id.txtTinggi);
        txtBerat = (EditText) rootView.findViewById(R.id.txtBerat);
        imgBtn = (ImageButton) rootView.findViewById(R.id.imgBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date = year + "-" + (monthOfYear+ 1) + "-" + dayOfMonth;
                        txtTTL.setText(date);
                            }
                        }, mYear, mMonth, mDay);
                Log.d("hay", String.valueOf(mYear) + String.valueOf(mMonth) + String.valueOf(mDay));
                datePickerDialog.show();
            }
        });
        Log.d("hay", String.valueOf(mYear) + String.valueOf(mMonth) + String.valueOf(mDay));
//        rdPria = (RadioButton)rootView.findViewById(R.id.rb_pria);
//        rdWanita = (RadioButton)rootView.findViewById(R.id.rb_wanita);

        loadDataUsersLogin();
        String urlProfileImg = users_login.getFoto().toString();;
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
        txtEmail.setText(users_login.getEmail());
        txtNama.setText(users_login.getNama_user());
        txtTinggi.setText(users_login.getTinggi());
        txtBerat.setText(users_login.getBerat());
        txtJK.setText(users_login.getJk());
        txtTTL.setText(users_login.getTtl());
        txtUmur.setText(users_login.getUmur());

        btnSave = (Button) rootView.findViewById(R.id.btnUpdate);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdateAkunClick();
            }
        });
        return rootView;
    }


    public void onUpdateAkunClick() {
        if (is_parameters_update_valid()){
            String tinggi, berat, umur, jenisk;
            double tb, be, um;
            double hasila = 0.0;

            tinggi  = txtTinggi.getText().toString();
            berat   = txtBerat.getText().toString();
            umur    = txtUmur.getText().toString();
            jenisk  = txtJK.getText().toString();


            tb = Double.parseDouble(tinggi);
            be = Double.parseDouble(berat);
            um = Double.parseDouble(umur);

            if ( jenisk.equals("L")) {
                hasila = 66.42 + (13.75 * be) + (5 *tb) + (6.78 * um);
            } else if ( jenisk.equals("P")) {
                hasila = 655.1 + (9.65 * be) + (5 *tb) + (4.68 * um);
            } else {
                Toast.makeText(getContext(),"Data Jenis Kelamin Kosong!!", Toast.LENGTH_SHORT).show();
            }

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            String umurS = "";
            String as = date;
            Log.d("haha", String.valueOf(year));
            Log.d("haha", String.valueOf(as));

            umurS = as.substring(0,4);
            Log.d("haha", String.valueOf(umurS));

            int hasil = year - Integer.parseInt(umurS);
            Log.d("haha", String.valueOf(hasil));

            final FormData data = new FormData();
            data.add("method", "update_akun");
            data.add("id_user", users_login.getId_user());
            data.add("email", txtEmail.getText().toString());
            data.add("nama_user",txtNama.getText().toString());
            data.add("jk",txtJK.getText().toString());
            data.add("ttl",txtTTL.getText().toString());
            data.add("tinggi",txtTinggi.getText().toString());
            data.add("berat",txtBerat.getText().toString());
            data.add("umur", String.valueOf(hasil));
            data.add("kalori", String.valueOf(hasila));
            InternetTask uploadTask = new InternetTask("Users", data);
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
//        if (txtUsername.getText().toString().equals("")){
//            txtUsername.requestFocus();
//            txtUsername.setError("Please Input Your Username");
//            return false;
//        }else if(txtNama.getText().toString().equals("")){
//            txtNama.requestFocus();
//            txtNama.setError("Please Input Your Full Name");
//            return false;
//        }else if(txtEmail.getText().toString().equals("")){
//            txtEmail.requestFocus();
//            txtEmail.setError("Please Input Your Email");
//            return false;
//        }else
            return true;
    }
}
