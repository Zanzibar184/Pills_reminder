package com.example.zanzibar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (id == R.id.nav_home) {

            Cure cure = new Cure();
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).commit();

        } else if (id == R.id.nav_pills) {

            MieiFarmaci mieiFarmaci = new MieiFarmaci();
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, mieiFarmaci).commit();

        } else if (id == R.id.nav_calendar) {

            Calendario calendario = new Calendario();
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, calendario).commit();

        } else if (id == R.id.nav_pharamarcy) {

            startActivity(new Intent(this, MapsActivity.class));
            Cure cure = new Cure();
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, cure).commit();
            navigationView.getMenu().getItem(0).setChecked(true);


        } else if (id == R.id.nav_contacts) {

            ContattiImportanti contattiImportanti = new ContattiImportanti();
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, contattiImportanti).commit();


        } else if (id == R.id.nav_sms) {

            SmsAvviso smsAvviso = new SmsAvviso();
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, smsAvviso).commit();


        } else if (id == R.id.nav_notes) {

            Note note = new Note();
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, note).commit();

        } else if (id == R.id.nav_settings) {
            Impostazioni impostazioni = new Impostazioni();
            fragmentManager.beginTransaction().replace(R.id.fragmentmanager, impostazioni).commit();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
