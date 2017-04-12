package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class FirstActivity extends BaseActivity {
    Button btnDaftar, btnMasuk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadDataUsersLogin();
        try {
            if(!users_login.getId_user().isEmpty()){
                Intent intent = new Intent(FirstActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }catch (NullPointerException nex){
            setContentView(R.layout.activity_first);
            initComponents();
        }
    }

    public void initComponents(){
        btnDaftar = (Button) findViewById(R.id.btnJoin);
        btnMasuk = (Button) findViewById(R.id.btnSignin);
    }

    public void btn_onClick(View view){
        Button b = (Button) view;
        if(b==this.btnDaftar){
            Intent i = new Intent(this.getApplicationContext(), RegisterActivity.class);
            this.startActivity(i);
        }else if(b==this.btnMasuk){
            Intent i = new Intent(this.getApplicationContext(), LoginActivity.class);
            this.startActivity(i);
        }
    }
}
