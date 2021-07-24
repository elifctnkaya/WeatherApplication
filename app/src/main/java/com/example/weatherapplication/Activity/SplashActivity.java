package com.example.weatherapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.weatherapplication.R;

public class SplashActivity extends AppCompatActivity {

        private LocationManager locationManager;
        private int izinKontrol;
        private String konumSaglayici = "gps";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);

            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            izinKontrol = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if(izinKontrol != PackageManager.PERMISSION_GRANTED) {
                //DAHA ÖNCE İZİN VERİLMEMİŞ
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
            }else{
                //DAHA ÖNCE İZİN VERİLMİŞ
                Location konum = locationManager.getLastKnownLocation(konumSaglayici);
                if(konum!=null)
                {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "Konum Alınamadı..", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 100) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(this, "İZİN VERİLDİ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"İZİN VERİLMEDİ",Toast.LENGTH_SHORT).show();
                }
            }
        }
}