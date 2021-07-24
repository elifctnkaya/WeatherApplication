package com.example.weatherapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weatherapplication.Adapter.KonumAdapter;
import com.example.weatherapplication.Database;
import com.example.weatherapplication.Konum;
import com.example.weatherapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class KonumAramaActivity extends AppCompatActivity {

    private EditText konumAl;
    private String konum;
    private RecyclerView rv;
    private Button konumSil;
    String konumID = "";
    private ArrayList<Konum> konumlar = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konum_arama);

        konumAl = findViewById(R.id.konumAl);
        rv = findViewById(R.id.rv);
        konumSil = findViewById(R.id.konumSil);

        konumGetir();

    }

    public void konumAraClick(View view)
    {

        if (konumAl.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "Konum Boş Olamaz!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            new JsonParse().execute();

        }

    }

    protected class JsonParse extends AsyncTask<Void, Void, Void>
    {
        String  result_lat;
        String result_lon;

        @Override
        protected Void doInBackground(Void... voids) {

            String result="";
            try {
                URL weather_url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + konumAl.getText().toString() + "&APPID=819e2ca5a72e9dbe18a76ffb57b25673&lang=tr");
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(weather_url.openStream()));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();

                JSONObject jsonObject = new JSONObject(result);

                JSONObject jsonObject_coord = jsonObject.getJSONObject("coord");
                result_lon = jsonObject_coord.getString("lon");
                result_lat = jsonObject_coord.getString("lat");

                Intent intent = new Intent(KonumAramaActivity.this, ArananKonumActivity.class);
                intent.putExtra("lon", result_lon);
                intent.putExtra("lat", result_lat);
                startActivity(intent);


                konumKaydet();

                konumGetir();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }


    public void konumKaydet() {
        if (!konumAl.getText().toString().trim().equals("")) {
            Database db = new Database(getApplicationContext());
            db.addKonum(konumAl.getText().toString());

            db.close();
            konumAl.setText("");
        }
    }
    void konumGetir() {
        Database db = new Database(getApplicationContext());
        konumlar = db.getKonumList();

        KonumAdapter adp = new KonumAdapter(this, konumlar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(adp);

        adp.setOnItemClickListener(onItemNoteClickListener);

        db.close();
    }
    View.OnClickListener onItemNoteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int i = viewHolder.getAdapterPosition();
            Konum item = konumlar.get(i);

            konumAl.setText(item.getKonum_adi());
            konumID = item.getKonum_id();
        }
    };

    public void konumSil() {
        if (!konumID.equals("")) {
            Database db = new Database(getApplicationContext());
            db.deleteKonum(konumID);
            db.close();

            Toast.makeText(getApplicationContext(), "Konum silindi", Toast.LENGTH_SHORT).show();
            konumID = "";
            konumAl.setText("");
            konumGetir();
        } else
            Toast.makeText(getApplicationContext(), "Lütfen silinecek konumu seçiniz", Toast.LENGTH_SHORT).show();
    }

    public void SilmeFonk(View view){
        konumSil();
    }

    public void geriTusuClick(View view){
        Intent intent = new Intent(KonumAramaActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(KonumAramaActivity.this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}