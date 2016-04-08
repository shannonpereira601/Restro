package com.example.admin.restro;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements LocationListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    GoogleApiClient googleApiClient;
    LocationManager locationManager;
    double userlongitudeNetwork, userlatitudeNetwork;
    TextView latlongValueNetwork;
    String l1;
    String l2;
    LatLng user;
    Location mCurrentLocation;
    private HashMap<String, Marker> markers = new HashMap<String, Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        userlongitudeNetwork = 0;
        userlatitudeNetwork = 0;
        latlongValueNetwork = (TextView) findViewById(R.id.latlonglocation);
        googleApiClient = new GoogleApiClient.Builder(getBaseContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        toggleNetworkUpdate();
        mMap.setOnMapLongClickListener(this);

        // mMap.setTrafficEnabled(true);
        //mMap.getUiSettings().setZoomControlsEnabled(true);

        user = new LatLng(userlatitudeNetwork, userlongitudeNetwork);
        LatLng fivespice = new LatLng(19.1767155, 72.8381069);
        LatLng kasbah = new LatLng(19.2383738, 72.8311794);
        LatLng flag = new LatLng(19.1079686, 72.8812817);
        LatLng spiceclub = new LatLng(19.1079686, 72.8812817);
        LatLng breeze = new LatLng(19.1079686, 72.8812817);
        LatLng bademiya = new LatLng(19.1079686, 72.8812817);
        LatLng nfeet = new LatLng(19.1079686, 72.8812817);

        //  Marker m1 = mMap.addMarker(new MarkerOptions().position(fusion).title("Global Fusion"));
        Marker m1 = mMap.addMarker(new MarkerOptions().position(fivespice).icon(BitmapDescriptorFactory.fromResource(R.drawable.mapicon)).title("Global Fusion"));
        markers.put("Fusion", m1);
        Marker m2 = mMap.addMarker(new MarkerOptions().position(nfeet).title("PopTates"));
        markers.put("PopTates", m2);
        Marker m3 = mMap.addMarker(new MarkerOptions().position(fivespice).title("5Spice"));
        markers.put("5Spice", m3);
        Toast.makeText(this, userlatitudeNetwork + " " + userlongitudeNetwork, Toast.LENGTH_SHORT).show();
    }

    public void onSearch(View view) {
        ImageView distance = (ImageView) findViewById(R.id.distancemap);
        if (haveNetworkConnection()) {
            EditText locationedit = (EditText) findViewById(R.id.mapedittext);
            String location = locationedit.getText().toString();
            List<Address> addressList = null;

            if (location != null || location.equals("")) {
                Geocoder geocoder = new Geocoder(this);
                try {
                    addressList = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (addressList.size() > 0) {
                    final Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title("NewMarker"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                    distance.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double lat1 = 19.2435659;
                            double long1 = 72.8536839;
                            if (userlongitudeNetwork != 0 && userlatitudeNetwork != 0) {
                                findlocation(userlatitudeNetwork, address.getLatitude(), userlongitudeNetwork, address.getLongitude());
                            } else {
                                Toast.makeText(getBaseContext(), "User Location not found", Toast.LENGTH_LONG).show();
                            }
                        }

                    });
                } else {
                    Snackbar.make(view, "Invalid Address", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        }

    }


    public void zoom(View view) {
        ImageView up = (ImageView) findViewById(R.id.up);
        ImageView down = (ImageView) findViewById(R.id.down);
        if (view == up) {
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
        }
        if (view == down) {
            mMap.animateCamera(CameraUpdateFactory.zoomOut());
        }
    }

    public void findlocation(double lat1, double lat2, double long1, double long2) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + lat1 + "," + long1 + "&daddr=" + lat2 + "," + long2));
        startActivity(i);
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

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleNetworkUpdate() {
        if (!checkLocation())
            return;
       /* Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            locationManager.removeUpdates(locationListenerNetwork);
            button.setText(R.string.resume);
        } else {*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerNetwork);
        Toast.makeText(this, "Network provider started running", Toast.LENGTH_LONG).show();
        // button.setText(R.string.pause);
        // }
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            userlongitudeNetwork = location.getLongitude();
            userlatitudeNetwork = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    latlongValueNetwork.setText(userlatitudeNetwork + " " + userlongitudeNetwork);
                    Toast.makeText(getBaseContext(), "Network Provider update " + userlongitudeNetwork + " " + userlatitudeNetwork, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCurrentLocation = LocationServices
                .FusedLocationApi
                .getLastLocation(googleApiClient);

        initCamera(mCurrentLocation);
    }

    private void initCamera(Location location) {
        CameraPosition position = CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),
                        location.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(position), null);
        //mMap.setTrafficEnabled(true);
        //mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(getAddressFromLatLng(latLng));
        Log.d("Clicked", "You Clicked on the map");
        options.icon(BitmapDescriptorFactory.defaultMarker());
        mMap.addMarker(options);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions options = new MarkerOptions().position(latLng);
        options.title(getAddressFromLatLng(latLng));
        Log.d("Clicked", "You Cliiiiicked on the map");
        options.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher)));

        mMap.addMarker(options);

    }

    private String getAddressFromLatLng(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getBaseContext());
        String address = "";
        try {
            address = geocoder
                    .getFromLocation(latLng.latitude, latLng.longitude, 1)
                    .get(0).getAddressLine(0);
        } catch (IOException e) {
        }

        return address;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
