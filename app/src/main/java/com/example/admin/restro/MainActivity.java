package com.example.admin.restro;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.restro.Signing.Login;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Boolean check1=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLogin();
        if(!check1) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView name = (TextView) header.findViewById(R.id.username);
        TextView email = (TextView) header.findViewById(R.id.usermail);
        // String username = getIntent().getExtras().getString("name");
        //String fbemail = getIntent().getExtras().getString("email");
        //email.setText(fbemail);
        //name.setText(username);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView reclist = (RecyclerView) findViewById(R.id.rv);
        reclist.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        reclist.setLayoutManager(llm);
        RVAdapter adapter = new RVAdapter(getdata());
        reclist.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "No Action yet bro :(", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

    }

    public boolean checkLogin()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        check1 = false;
        check1 = sharedPreferences.getBoolean("check",false);
        return check1;
    }

    public List<Data> getdata() {
        List<Data> hotels = new ArrayList<>();
        int[] Photo = {R.drawable.roll, R.drawable.fish, R.drawable.chinese, R.drawable.indian, R.drawable.fish, R.drawable.roll, R.drawable.roll, R.drawable.fish, R.drawable.chinese, R.drawable.indian, R.drawable.fish, R.drawable.roll};
        String[] Hotel = getResources().getStringArray(R.array.Hotels);
        String[] Location = getResources().getStringArray(R.array.Location);
        for (int i = 0; i < Hotel.length; i++) {
            Data current = new Data();
            current.hotelphoto = Photo[i];
            current.hotelname = Hotel[i];
            current.location = Location[i];
            hotels.add(current);
        }
        return hotels;
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
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        final boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        int id = item.getItemId();

        if (id == R.id.n1) {

        } else if (id == R.id.n2) {


        } else if (id == R.id.n3) {

            boolean connected = haveNetworkConnection();

            if (!enabled) {
                Toast.makeText(getBaseContext(), "Please Turn On your GPS", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent1);
            }
            if (!connected && enabled) {
                Toast.makeText(getBaseContext(), "Network is Off", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
            if (enabled && connected) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.n4) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            LoginManager.getInstance().logOut();
            SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            check1 = false;
            editor.putBoolean("check", check1);
            editor.commit();

        } else if (id == R.id.feedback) {
            Intent intent = new Intent(MainActivity.this, Feedback.class);
            startActivity(intent);
        } else if (id == R.id.contactus) {
            Dialog dialog = new Dialog();
            dialog.show(getFragmentManager(), "dialog");
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}


