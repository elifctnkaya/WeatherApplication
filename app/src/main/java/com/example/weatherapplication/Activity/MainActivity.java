package com.example.weatherapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.weatherapplication.Fragment.HaftalikFragment;
import com.example.weatherapplication.R;
import com.example.weatherapplication.Fragment.SaatlikFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener{

    private int izinKontrol;
    private String konumSaglayici = "gps";
    private LocationManager locationManager;
    Double lat, lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        izinKontrol = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (izinKontrol != PackageManager.PERMISSION_GRANTED) {
            //DAHA ÖNCE İZİN VERİLMEMİŞ
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            //DAHA ÖNCE İZİN VERİLMİŞ
            Location konum1 = locationManager.getLastKnownLocation(konumSaglayici);
            if (konum1 != null) {
                onLocationChanged(konum1);
            } else {
                Toast.makeText(this, "Konum Alınamadı..", Toast.LENGTH_SHORT).show();
            }
        }

        Saatlikgecis(new SaatlikFragment());

        Haftalikgecis(new HaftalikFragment());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "İZİN VERİLDİ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "İZİN VERİLMEDİ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double enlem = location.getLatitude();
        double boylam = location.getLongitude();

        lat = enlem;
        lon = boylam;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }


    public void Saatlikgecis(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.saatlikFragment, fragment);
        fragmentTransaction.commit();
    }

    public void Haftalikgecis(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.haftalikFragment, fragment);
        fragmentTransaction.commit();
    }

    public void konumArama(View view) {
        Intent intent = new Intent(MainActivity.this, KonumAramaActivity.class);
        startActivity(intent);
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

}