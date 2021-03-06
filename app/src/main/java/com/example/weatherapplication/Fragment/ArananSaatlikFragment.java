package com.example.weatherapplication.Fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapplication.Activity.ArananKonumActivity;
import com.example.weatherapplication.Activity.MainActivity;
import com.example.weatherapplication.Adapter.SaatlikAdapter;
import com.example.weatherapplication.Model.SaatlikModel;
import com.example.weatherapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class ArananSaatlikFragment extends Fragment {

    private RecyclerView recyclerViewSaatlik;
    private ArrayList<SaatlikModel> saatlikModelArrayList;
    private SaatlikAdapter adapter;

    TextView konumAdi, zaman,mevcutDerece,description, suanDerece, feelsLike;
    ImageView mevcutIcon;

    String lat, lon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArananKonumActivity havaDurumu = (ArananKonumActivity) getActivity();
        lat = havaDurumu.getLat();
        lon = havaDurumu.getLon();

        new JsonParse().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aranan_saatlik, container, false);

        recyclerViewSaatlik = view.findViewById(R.id.recyclerViewSaatlik);
        recyclerViewSaatlik.setHasFixedSize(true);
        recyclerViewSaatlik.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));


        konumAdi = view.findViewById(R.id.konumAdi);
        zaman = view.findViewById(R.id.zaman);
        mevcutDerece = view.findViewById(R.id.mevcutDerece);
        mevcutIcon = view.findViewById(R.id.mevcutIcon);
        description = view.findViewById(R.id.description);
        suanDerece = view.findViewById(R.id.suanDerece);
        feelsLike = view.findViewById(R.id.feelsLike);

        SimpleDateFormat date = new SimpleDateFormat("E, d/M/yy");
        Date tarihSaat = new Date();
        zaman.setText(date.format(tarihSaat));

        return view;
    }


    protected class JsonParse extends AsyncTask<Void, Void, Void>{

        String result_main ="";
        int result_temp;
        int result_feels_like;
        String result_city;
        String result_icon="";
        String result_description;
        String hourly_icon;

        long result_saat;
        int result_saat_temp;
        Double result_saat_pop;
        String[] saatler = new String[24];
        int[] saat_pop = new int[24];
        int[] saat_derece = new int[24];

        int result_gece_derece;

        @Override
        protected Void doInBackground(Void... voids) {

            String result="";
            String result3="";
            String APIKEY = getResources().getString(R.string.APIKEY);

            Double lat1 = Double.parseDouble(lat);
            Double lon1 = Double.parseDouble(lon);

            try {
                URL weather_url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat1 + "&lon=" + lon1 + "&APPID=" + APIKEY);
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(weather_url.openStream()));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();

                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("weather");
                JSONObject jsonObject_weather = jsonArray.getJSONObject(0);
                result_main = jsonObject_weather.getString("main");
                result_description = jsonObject_weather.getString("description");
                result_icon = jsonObject_weather.getString("icon");


                JSONObject jsonObject_main = jsonObject.getJSONObject("main");
                Double temp = jsonObject_main.getDouble("temp");
                Double feels_like = jsonObject_main.getDouble("feels_like");


                result_city = jsonObject.getString("name");

                result_temp = (int) (temp - 273);
                result_feels_like = (int) (feels_like - 273);

                URL night_url = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&APPID=" + APIKEY);
                BufferedReader bufferedReader3 = null;
                bufferedReader3 = new BufferedReader(new InputStreamReader(night_url.openStream()));
                String line3 = null;
                while ((line3 = bufferedReader3.readLine()) != null) {
                    result3 += line3;
                }
                bufferedReader3.close();

                JSONObject jsonObject3 = new JSONObject(result3);

                JSONArray jsonArray3 = jsonObject3.getJSONArray("daily");
                JSONObject daily = jsonArray3.getJSONObject(0);

                JSONObject temp3 = daily.getJSONObject("temp");
                Double geceDerece = temp3.getDouble("night");
                result_gece_derece = (int) (geceDerece -273);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            //---------------------------------------saatlik verileri ??ekme-------------------------------
            String result2 = "";

            try {
                URL weather_url2 = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat + "&lon=" + lon + "&exclude&appid=" + APIKEY);
                BufferedReader bufferedReader2 = null;
                bufferedReader2 = new BufferedReader(new InputStreamReader(weather_url2.openStream()));
                String line2 = null;
                while ((line2 = bufferedReader2.readLine()) != null) {
                    result2 += line2;
                }
                bufferedReader2.close();

                JSONObject jsonObject2 = new JSONObject(result2);
                JSONArray jsonArray2 = jsonObject2.getJSONArray("hourly");
                for (int i = 1; i <24; i++) {
                    JSONObject hourly = jsonArray2.getJSONObject(i);

                    JSONArray weatherArray = hourly.getJSONArray("weather");
                    JSONObject weatherObject = weatherArray.getJSONObject(0);
                    hourly_icon = weatherObject.getString("icon");


                    result_saat = hourly.getLong("dt");
                    Date date = new Date(result_saat * 1000L);
                    SimpleDateFormat saat = new SimpleDateFormat("H:mm");
                    saat.setTimeZone(TimeZone.getTimeZone("GMT+3"));
                    saatler[i] = saat.format(date);

                    result_saat_pop = hourly.getDouble("pop");
                    saat_pop[i] = (int) (result_saat_pop * 100);


                    Double valTemp = hourly.getDouble("temp");
                    result_saat_temp = (int) (valTemp - 273);
                    saat_derece[i] = result_saat_temp;

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(result_icon.equals("01d"))
                mevcutIcon.setImageResource(R.drawable.clear_sky);
            else if(result_icon.equals("02d"))
                mevcutIcon.setImageResource(R.drawable.few_clouds);
            else if(result_icon.equals("03d"))
                mevcutIcon.setImageResource(R.drawable.scattered_clouds);
            else if(result_icon.equals("04d"))
                mevcutIcon.setImageResource(R.drawable.broken_clouds);
            else if(result_icon.equals("09d"))
                mevcutIcon.setImageResource(R.drawable.shower_rain);
            else if(result_icon.equals("10d"))
                mevcutIcon.setImageResource(R.drawable.rain);
            else if(result_icon.equals("11d"))
                mevcutIcon.setImageResource(R.drawable.thunderstorm);
            else if(result_icon.equals("13d"))
                mevcutIcon.setImageResource(R.drawable.snow);
            else if(result_icon.equals("50d"))
                mevcutIcon.setImageResource(R.drawable.mist);
            else if(result_icon.equals("01n"))
                mevcutIcon.setImageResource(R.drawable.clear_sky_night);
            else if(result_icon.equals("02n"))
                mevcutIcon.setImageResource(R.drawable.few_clouds_night);
            else if(result_icon.equals("03n"))
                mevcutIcon.setImageResource(R.drawable.scattered_clouds_night);
            else if (result_icon.equals("04n"))
                mevcutIcon.setImageResource(R.drawable.broken_clouds_night);
            else if(result_icon.equals("09n"))
                mevcutIcon.setImageResource(R.drawable.shower_rain);
            else if(result_icon.equals("10n"))
                mevcutIcon.setImageResource(R.drawable.rain);
            else if(result_icon.equals("11n"))
                mevcutIcon.setImageResource(R.drawable.thunderstorm);
            else if(result_icon.equals("13n"))
                mevcutIcon.setImageResource(R.drawable.snow);
            else if (result_icon.equals("50n"))
                mevcutIcon.setImageResource(R.drawable.mist);

            String icon = null;

            if(hourly_icon.equals("01d"))
                icon = "clear_sky";
            else if(hourly_icon.equals("02d"))
                icon = "few_clouds";
            else if(hourly_icon.equals("03d"))
                icon = "scattered_clouds";
            else if(hourly_icon.equals("04d"))
                icon = "broken_clouds";
            else if(hourly_icon.equals("09d"))
                icon = "shower_rain";
            else if(hourly_icon.equals("10d"))
                icon = "rain";
            else if(hourly_icon.equals("11d"))
                icon = "thunderstorm";
            else if(hourly_icon.equals("13d"))
                icon = "snow";
            else if(hourly_icon.equals("50d"))
                icon = "mist";
            else if(hourly_icon.equals("01n"))
                icon = "clear_sky_night";
            else if(hourly_icon.equals("02n"))
                icon = "few_clouds_night";
            else if(hourly_icon.equals("03n"))
                icon = "scattered_clouds_night";
            else if (hourly_icon.equals("04n"))
                icon = "broken_clouds_night";
            else if(hourly_icon.equals("09n"))
                icon = "shower_rain";
            else if(hourly_icon.equals("10n"))
                icon = "rain";
            else if(hourly_icon.equals("11n"))
                icon = "thunderstorm";
            else if(hourly_icon.equals("13n"))
                icon = "snow";
            else if (hourly_icon.equals("50n"))
                icon = "mist";

            konumAdi.setText(result_city);
            mevcutDerece.setText(result_temp+"??");
            description.setText(result_description);
            suanDerece.setText(result_temp+"??" + " / " + result_gece_derece+"??");
            feelsLike.setText("hissedilen "+result_feels_like+ "??");
            //mevcutIcon.setImageBitmap(bitImage);
            //mevcutIcon.setImageResource(R.drawable.clear_sky);

            saatlikModelArrayList = new ArrayList<>();

            for (int i=1; i<24; i++)
            {
                String saatlik_derece;
                saatlik_derece = String.valueOf(saat_derece[i]);
                String saat;
                saat = String.valueOf(saatler[i]);
                String pop;
                pop = String.valueOf(saat_pop[i]);

                SaatlikModel s1 = new SaatlikModel(1,saat,icon,saatlik_derece+"??",pop+"%");
                saatlikModelArrayList.add(s1);
            }

            adapter = new SaatlikAdapter(getContext(),saatlikModelArrayList);

            recyclerViewSaatlik.setAdapter(adapter);

            super.onPostExecute(aVoid);

        }
    }
}