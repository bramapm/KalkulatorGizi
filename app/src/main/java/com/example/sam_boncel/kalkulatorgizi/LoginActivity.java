package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sam_boncel.kalkulatorgizi.lib.FormData;
import com.example.sam_boncel.kalkulatorgizi.lib.InternetTask;
import com.example.sam_boncel.kalkulatorgizi.lib.OnInternetTaskFinishedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    Button btnMasuk;
    EditText txtUsername, txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    public void initComponents(){
        btnMasuk = (Button) findViewById(R.id.btnSignin);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginClick();
            }
        });
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
    }

    private boolean is_parameters_login_valid(){
        if (txtUsername.getText().toString().equals("")){
            txtUsername.requestFocus();
            txtUsername.setError("Please Input Your Username");
            return false;
        }else if (txtPassword.getText().toString().equals("")) {
            txtPassword.requestFocus();
            txtPassword.setError("Please Input Your Password");
            return false;
        }else
            return true;
    }

    public void onLoginClick(){
        if (is_parameters_login_valid()){
            FormData data = new FormData();
            data.add("method", "login");
            data.add("username", txtUsername.getText().toString());
            data.add("password", txtPassword.getText().toString());
            InternetTask uploadTask = new InternetTask("Users", data);
            uploadTask.setOnInternetTaskFinishedListener(new OnInternetTaskFinishedListener() {
                @Override
                public void OnInternetTaskFinished(InternetTask internetTask) {
                    try {
                        JSONObject jsonObject = new JSONObject(internetTask.getResponseString());
                        if (jsonObject.get("code").equals(200)){
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            saveDataUsersLogin(jsonArray.getString(0));
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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
}
