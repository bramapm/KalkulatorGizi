package com.example.sam_boncel.kalkulatorgizi;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

public class HomeActivity extends BaseRedrawActivity {
TextView txtNama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BerandaFragment berandaFragment = new BerandaFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(
                R.id.relativelayout_for_fragment,
                berandaFragment,
                berandaFragment.getTag()).commit();

    }
}
