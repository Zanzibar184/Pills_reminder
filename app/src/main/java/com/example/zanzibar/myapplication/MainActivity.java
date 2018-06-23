package com.example.zanzibar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.zanzibar.myapplication.frames.Calendario;
import com.example.zanzibar.myapplication.frames.ContattiImportanti;
import com.example.zanzibar.myapplication.frames.Cure;
import com.example.zanzibar.myapplication.frames.FragmentImpostazioni;
import com.example.zanzibar.myapplication.frames.MieiFarmaci;
import com.example.zanzibar.myapplication.frames.Note;
import com.example.zanzibar.myapplication.frames.SmsAvviso;
import com.example.zanzibar.myapplication.maps.MapsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected NavigationView navigationView;
    FloatingActionButton fab = null;

    int tmpClicksBackPressed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(savedInstanceState==null) {
            fab.show();
            Cure cure = new Cure(fab);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                            .replace(R.id.fragmentmanager, cure).commit();
        }

    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawer(Gravity.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else if ((getSupportFragmentManager().getBackStackEntryCount() == 0)){
            super.onBackPressed();
        }
        /*
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count>1) {
                //Log.e("count", "is getBackStackEntryCount -> " + count);
                for (int i = 1; i <= count; ++i) {
                    getSupportFragmentManager().popBackStackImmediate();
                    //Log.e("getBackStack", "IS REMOVED " + i);
                }
            } else if(count == 1){
                fab.show();
                Cure cure = new Cure(fab);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).commit();
                //Log.i("super", "sto usando onBackPressed di default");
            } else {
                super.onBackPressed();
            }
        }
        */
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_home) {
            fab.show();
            Cure cure = new Cure(fab);
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragmentmanager, cure).addToBackStack(null).commit();

        } else if (id == R.id.nav_pills) {
            fab.show();
            MieiFarmaci mieiFarmaci = new MieiFarmaci(fab);
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragmentmanager, mieiFarmaci).addToBackStack(null).commit();

        } else if (id == R.id.nav_calendar) {
            fab.show();
            Calendario calendario = new Calendario(fab);
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragmentmanager, calendario).addToBackStack(null).commit();

        } else if (id == R.id.nav_pharamarcy) {
            fab.hide();
            startActivity(new Intent(this, MapsActivity.class));
            Cure cure = new Cure(fab);
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).addToBackStack(null).commit();
            navigationView.getMenu().getItem(0).setChecked(true);


        } else if (id == R.id.nav_contacts) {
            fab.show();
            ContattiImportanti contattiImportanti = new ContattiImportanti(fab);
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragmentmanager, contattiImportanti).addToBackStack(null).commit();


        } else if (id == R.id.nav_sms) {
            fab.hide();
            SmsAvviso smsAvviso = new SmsAvviso();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragmentmanager, smsAvviso).addToBackStack(null).commit();


        } else if (id == R.id.nav_notes) {
            fab.show();
            Note note = new Note(fab);
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragmentmanager, note).addToBackStack(null).commit();

        } else if (id == R.id.nav_settings) {
            fab.hide();
            FragmentImpostazioni impostazioni = new FragmentImpostazioni();
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.fragmentmanager, impostazioni).addToBackStack(null).commit();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
