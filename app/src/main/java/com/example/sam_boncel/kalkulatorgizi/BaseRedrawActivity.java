package com.example.sam_boncel.kalkulatorgizi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sam_Boncel on 19/03/2017.
 */

public class BaseRedrawActivity extends BaseActivity {

    @Override
    public void setContentView(int layout){
        super.setContentView(layout);
        setToolbar();
    }

    public void setToolbar(){
        loadDataUsersLogin();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
                    toolbar1.setTitle("Beranda");
                    Toast.makeText(getApplicationContext(), "Beranda", Toast.LENGTH_SHORT).show();
                    BerandaFragment berandaFragment = new BerandaFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(
                            R.id.relativelayout_for_fragment,
                            berandaFragment,
                            berandaFragment.getTag()).commit();

                } else if (id == R.id.nav_makanan) {
                    Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
                    toolbar1.setTitle("Makanan");
                    Toast.makeText(getApplicationContext(), "Makanan", Toast.LENGTH_SHORT).show();
                    MakananFragment makananFragment = new MakananFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(
                            R.id.relativelayout_for_fragment,
                            makananFragment,
                            makananFragment.getTag()).commit();
                } else if (id == R.id.nav_olahraga) {
                    Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
                    toolbar1.setTitle("Olahraga");
                    Toast.makeText(getApplicationContext(), "Olahraga", Toast.LENGTH_SHORT).show();
                    OlahragaFragment olahragaFragment= new OlahragaFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(
                            R.id.relativelayout_for_fragment,
                            olahragaFragment,
                            olahragaFragment.getTag()).commit();
                } else if (id == R.id.nav_manage) {
                    Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
                    toolbar1.setTitle("Pengaturan");
                    Toast.makeText(getApplicationContext(), "Pengaturan", Toast.LENGTH_SHORT).show();
                    PengaturanFragment pengaturanFragment = new PengaturanFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(
                            R.id.relativelayout_for_fragment,
                            pengaturanFragment,
                            pengaturanFragment.getTag()).commit();
                } else if (id == R.id.nav_logout){
                    deleteDataUsersLogin();
                    Intent intent = new Intent(BaseRedrawActivity.this, FirstActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        View vv = navigationView.getHeaderView(0);
        TextView nama_user = (TextView) vv.findViewById(R.id.nama_user);
        nama_user.setText(users_login.getNama_user());
        TextView email_user = (TextView) vv.findViewById(R.id.email_user);
        email_user.setText(users_login.getEmail());
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
