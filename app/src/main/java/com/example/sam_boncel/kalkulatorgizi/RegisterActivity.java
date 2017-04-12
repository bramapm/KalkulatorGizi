package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BaseActivity {
    EditText txtUsername, txtPassword, txtEmail, txtNamaUser;
    Button btnDaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
    }

    protected void initComponents(){
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtNamaUser = (EditText) findViewById(R.id.txtNama);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClick();
            }
        });
    }
    public void onRegisterClick() {
        if (is_parameters_register_valid()){
            FormData data = new FormData();
            data.add("method", "register");
            data.add("username", txtUsername.getText().toString());
            data.add("password", txtPassword.getText().toString());
            data.add("email", txtEmail.getText().toString());
            data.add("nama_user",txtNamaUser.getText().toString());
            InternetTask uploadTask = new InternetTask("Users", data);
            uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
                @Override
                public void OnInternetTaskFinished(InternetTask internetTask) {
                    try {
                        JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                        if (jsonObject.get("code").equals(200)){
                            //btnRegister.setClickable(false);
                            Toast.makeText(getApplicationContext(),"Registrasi Sukses", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(,"Registrasi Sukses", Snackbar.LENGTH_SHORT).show();
                            //Snackbar.make(this, "Registration Success", Snackbar.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }, 3000);
                        }else{
                            Toast.makeText(getApplicationContext(),"Registrasi Gagal", Toast.LENGTH_SHORT).show();
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
    }

    private boolean is_parameters_register_valid(){
        if (txtUsername.getText().toString().equals("")){
            txtUsername.requestFocus();
            txtUsername.setError("Please Input Your Username");
            return false;
        }else if (txtPassword.getText().toString().equals("")) {
            txtPassword.requestFocus();
            txtPassword.setError("Please Input Your Password");
            return false;
        }else if(txtNamaUser.getText().toString().equals("")){
            txtNamaUser.requestFocus();
            txtNamaUser.setError("Please Input Your Full Name");
            return false;
        }else if(txtEmail.getText().toString().equals("")){
            txtEmail.requestFocus();
            txtEmail.setError("Please Input Your Email");
            return false;
        }else
            return true;
    }
}
