package com.example.weatherapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weatherapplication.Fragment.ArananHaftalikFragment;
import com.example.weatherapplication.Fragment.ArananSaatlikFragment;
import com.example.weatherapplication.R;

public class ArananKonumActivity extends AppCompatActivity {

    String lon, lat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aranan_konum);

        Intent intent = getIntent();
        lon = intent.getStringExtra("lon");
        lat = intent.getStringExtra("lat");

        ArananSaatlikGecis(new ArananSaatlikFragment());
        ArananHaftalikGecis(new ArananHaftalikFragment());
    }


    public void ArananHaftalikGecis(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ArananHaftalikFragment,fragment);
        fragmentTransaction.commit();
    }

    public void ArananSaatlikGecis(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ArananSaatlikFragment,fragment);
        fragmentTransaction.commit();
    }


    public void mainDonus(View view){
        Intent intent = new Intent(ArananKonumActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public String getLon()
    {
        return lon;
    }

    public String getLat()
    {
        return lat;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ArananKonumActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}