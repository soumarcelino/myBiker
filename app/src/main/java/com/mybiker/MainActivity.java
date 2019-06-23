package com.mybiker;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    private TextView speedLabel, accuracyLabel;
    private Resources resources;
    public static Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speedLabel = (TextView) findViewById(R.id.speed_label);
        accuracyLabel = (TextView) findViewById(R.id.accuracy_label);
        resources = getResources();

        if(this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500,0,this);
        } else {
            Intent it = new Intent(this, RequestAuthorization.class);
            this.startActivity(it);
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        DecimalFormat formatter = new DecimalFormat("#.##");
        String speed = (String)formatter.format(location.getSpeed());
        String accuracy = (String)formatter.format(location.getAccuracy());

        speedLabel.setText(resources.getString(R.string.speed, speed));
        accuracyLabel.setText(resources.getString(R.string.acurracy, accuracy));

        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    public void openMap(View v){
        Intent it = new Intent(this, Map.class);
        startActivity(it);
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
}