package rm.woozy.com.sajuguju;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import rm.woozy.com.sajuguju.Fragments.Account;
import rm.woozy.com.sajuguju.Fragments.Addrequest;
import rm.woozy.com.sajuguju.Fragments.History;
import rm.woozy.com.sajuguju.Fragments.NearestParlors;
import rm.woozy.com.sajuguju.Fragments.Notification;
import rm.woozy.com.sajuguju.Fragments.Parlors;
import rm.woozy.com.sajuguju.Preference.SharedPref;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment = null;
    String title = "";

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
                fragment = new Addrequest();
                title = "Request";

                FragmentManager fmanager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fmanager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                setTitle(title);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_notification);
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
        int id = item.getItemId();

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

        if (id == R.id.nav_notification) {
            // Handle the notification action
            fragment = new Notification();
            title = "Notification";
            Toast.makeText(this, "Notification", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_parlors) {

            fragment = new Parlors();
            title = "Parlors";
            Toast.makeText(this, "Parlors", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_nearestparlor) {

            fragment = new NearestParlors();
            title = "Nearest Parlors";
            Toast.makeText(this, "Nearest Parlors", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_History) {
            fragment = new History();
            title = "History";
            Toast.makeText(this, "History", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_account) {
            fragment = new Account();
            title = "Account";
            Toast.makeText(this, "Account", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_signout) {
            SharedPref pref = new SharedPref(MainActivity.this);
            if(pref.logout()){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }

        if(fragment!=null){
            FragmentManager fmanager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fmanager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
            setTitle(title);
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
