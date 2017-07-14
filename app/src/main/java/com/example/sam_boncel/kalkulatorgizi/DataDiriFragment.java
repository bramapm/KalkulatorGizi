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
import android.support.annotation.IdRes;
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
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DataDiriFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DataDiriFragment extends Fragment {
    public User users_login;
    EditText txtUsername, txtEmail, txtNama, txtTinggi, txtBerat, txtTTL, txtJK, txtUmur;
    private RadioGroup genderRadio;
    RadioButton radioButton, rbLk, rbPr;
    private RadioGroup rgAktifitas;
    RadioButton RB1, RB2, RB3;
    TextView txtRB;
    String date ="";
    Double BBI, IMT;
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
//        Log.d("Arap", String.valueOf(id_gender));
        loadDataUsersLogin();
        View rootView = inflater.inflate(R.layout.fragment_data_diri, container, false);
        imgProfile = (ImageView) rootView.findViewById(R.id.img_profile);
        txtEmail = (EditText) rootView.findViewById(R.id.txtEmail);
        txtNama = (EditText) rootView.findViewById(R.id.txtNama);
        txtUmur = (EditText) rootView.findViewById(R.id.txtUmur);
        txtTTL = (EditText) rootView.findViewById(R.id.txtTTL);
        txtTinggi = (EditText) rootView.findViewById(R.id.txtTinggi);
        txtBerat = (EditText) rootView.findViewById(R.id.txtBerat);
        imgBtn = (ImageButton) rootView.findViewById(R.id.imgBtn);
        genderRadio = (RadioGroup) rootView.findViewById(R.id.rgJk);
        rbLk = (RadioButton) rootView.findViewById(R.id.rbLk);
        rbPr = (RadioButton) rootView.findViewById(R.id.rbPr);


        rgAktifitas = (RadioGroup) rootView.findViewById(R.id.rgAktifitas);
        RB1 = (RadioButton) rootView.findViewById(R.id.RB1);
        RB2 = (RadioButton) rootView.findViewById(R.id.RB2);
        RB3 = (RadioButton) rootView.findViewById(R.id.RB3);
        txtRB = (TextView) rootView.findViewById(R.id.txtRB);
        if (!users_login.getTtl().equals("0000-00-00")){
            date = users_login.getTtl();
        }

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
        String urlProfileImg = users_login.getFoto().toString();
        if(urlProfileImg.equals("")){
            urlProfileImg = "https://s-media-cache-ak0.pinimg.com/originals/15/4e/da/154edabc2856e10ba5f8d03e236fe6fc.jpg";
        }
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

        if (users_login.getJk().equals("Laki-Laki")){
            rbLk.setChecked(true);
        } else if(users_login.getJk().equals("Perempuan")){
            rbPr.setChecked(true);
        } else {
            rbLk.setChecked(true);
        }

        RB1.setChecked(true);
        rgAktifitas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                //checkedId = rgAktifitas.getCheckedRadioButtonId();
                Log.d("testes", String.valueOf(R.id.RB1));
                if (checkedId == R.id.RB1){
                    txtRB.setText("Aktifitas baik : Keseharian anda 75% waktu digunakan untuk duduk atau istirahat," +
                            " dan 25% digunakan untuk berdiri atau beraktifitas.");
                } else if (checkedId == R.id.RB2){
                    txtRB.setText("Aktifitas sedang : Keseharian anda 60% waktu digunakan untuk duduk atau istirahat," +
                            " dan 40% digunakan untuk berdiri atau beraktifitas.");
                } else if (checkedId == R.id.RB3){
                    txtRB.setText("Aktifitas berat : Keseharian anda 25% waktu digunakan untuk duduk atau istirahat," +
                            " dan 75% digunakan untuk berdiri atau beraktifitas.");
                }
            }
        });

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
        if(is_parameters_update_valid()){
            String tinggi, berat, umur, jenisk;
            double tb, be, um;
            double hasila = 0.0;

            int id_gender  = genderRadio.getCheckedRadioButtonId();
            radioButton = (RadioButton)getActivity().findViewById(id_gender);
            tinggi  = txtTinggi.getText().toString();
            berat   = txtBerat.getText().toString();
            umur    = txtUmur.getText().toString();
            jenisk  = radioButton.getText().toString();

            Double fAktifitasLk = 0.0;
            Double fAktifitasPr = 0.0;
            if (RB1.isChecked()){
                fAktifitasLk = 1.56;
                fAktifitasPr = 1.55;
            } else if (RB2.isChecked()){
                fAktifitasLk = 1.76;
                fAktifitasPr = 1.70;
            } else if (RB3.isChecked()){
                fAktifitasLk = 2.10;
                fAktifitasPr = 2.00;
            }

            tb = Double.parseDouble(tinggi);
            be = Double.parseDouble(berat);
            um = Double.parseDouble(umur);

            BBI = ((tb - 100) - ((tb - 100) * 10/100));
            IMT = (be / ((tb/100)*(tb/100)));
            if (jenisk.equals("Laki-Laki")) {
                hasila = ((66.42 + (13.75 * be) + (5 *tb) + (6.78 * um)) * fAktifitasLk);
                DecimalFormat df = new DecimalFormat("0.00");
                Double abc = Double.valueOf(df.format(hasila));
                hasila = abc;
            } else if (jenisk.equals("Perempuan")) {
                hasila = ((655.1 + (9.65 * be) + (5 *tb) + (4.68 * um)) * fAktifitasPr);
                DecimalFormat df = new DecimalFormat("0.00");
                Double abc = Double.valueOf(df.format(hasila));
                hasila = abc;
            } else {
                Toast.makeText(getContext(),"Data Jenis Kelamin Kosong!!", Toast.LENGTH_SHORT).show();
            }

            if ( hasila < BBI){
                hasila = hasila + 500;
            } else {
                hasila = hasila - 500;
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

            SharedPreferences.Editor editor = getContext().getSharedPreferences("data2", MODE_PRIVATE).edit();
            editor.putString("BBI", String.valueOf(BBI));
            editor.putString("IMT", String.valueOf(IMT));
            editor.apply();

//            data2.add("aktifitas", String.valueOf(rgAktifitas.getCheckedRadioButtonId()));
//            data2.add("BBI", String.valueOf(BBI));
//            data2.add("IMT", String.valueOf(IMT));

            final FormData data = new FormData();
            data.add("method", "update_akun");
            data.add("id_user", users_login.getId_user());
            data.add("email", txtEmail.getText().toString());
            data.add("nama_user",txtNama.getText().toString());
            data.add("jk",radioButton.getText().toString());
            data.add("ttl",txtTTL.getText().toString());
            data.add("tinggi",txtTinggi.getText().toString());
            data.add("berat",txtBerat.getText().toString());
            data.add("umur", String.valueOf(hasil));
            data.add("kalori", String.valueOf(hasila));
            data.add("aktifitas", String.valueOf(rgAktifitas.getCheckedRadioButtonId()));
            data.add("BBI", String.valueOf(BBI));
            data.add("IMT", String.valueOf(IMT));
            InternetTask uploadTask = new InternetTask("Users", data);
            uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
                     @Override
                     public void OnInternetTaskFinished(InternetTask internetTask) {
                         try {
                             JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                             Log.d("cihuy2", jsonObject.toString());;
                             if (jsonObject.get("code").equals(200)){
                                 Toast.makeText(getContext(),"Update Sukses", Toast.LENGTH_SHORT).show();
                                 JSONObject js = jsonObject.getJSONObject("data");
                                 Log.d("cihuy3", js.toString());
                                 Log.d("cihuy3", data.toString());
                                 saveDataUsersLogin(js.toString());
                                 //saveDataUsersLogin(js.toString());
                                 BerandaFragment berandaFragment = new BerandaFragment();
                                 Bundle bundle = new Bundle();
//                                 bundle.putString("BBI", String.valueOf(BBI));
//                                 bundle.putString("IMT", String.valueOf(IMT));
                                 berandaFragment.setArguments(bundle);
                                 FragmentManager manager = getActivity().getSupportFragmentManager();
                                 manager.beginTransaction().replace(
                                         R.id.relativelayout_for_fragment,
                                         berandaFragment).commit();
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
        if(txtTinggi.getText().toString().equals("") || txtTinggi.getText().toString().equals("0")){
            txtTinggi.requestFocus();
            txtTinggi.setError("Input Data Tinggi Badan");
            return false;
        }else if(txtBerat.getText().toString().equals("") || txtBerat.getText().toString().equals("0")){
            txtBerat.requestFocus();
            txtBerat.setError("Input Data Berat Badan");
            return false;
        }else if (txtTTL.getText().toString().equals("0000-00-00")){
            txtTTL.requestFocus();
            txtTTL.setError("Input Data Tanggal Lahir");
            return false;
        }
            return true;
    }

    public void saveDataUsersLogin(String data){
        SharedPreferences sharedPref = getContext().getSharedPreferences("data_private", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data", data);
        editor.commit();
    }

    public void saveDataUsers(String data){
        SharedPreferences sharedPref = getContext().getSharedPreferences("data_private2", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("data2", data);
        editor.commit();
    }

    public void deleteDataUsersLogin(){
        SharedPreferences sharedPref = getContext().getSharedPreferences("data_private", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("data");
        editor.commit();
    }
}
