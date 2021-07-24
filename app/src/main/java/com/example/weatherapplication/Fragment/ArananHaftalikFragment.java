package com.example.weatherapplication.Fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weatherapplication.Activity.ArananKonumActivity;
import com.example.weatherapplication.Adapter.HaftalikAdapter;
import com.example.weatherapplication.Model.HaftalikModel;
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

public class ArananHaftalikFragment extends Fragment {
    String lat, lon;

    private RecyclerView recyclerView;
    private ArrayList<HaftalikModel> havadurumuList;
    private HaftalikAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_aranan_haftalik, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    protected class JsonParse extends AsyncTask<Void, Void, Void>
    {
        int result_temp;
        long result_gun;
        int result_gece_temp;
        Double result_pop;
        String [] gunAdi = new String[8];
        int [] derece = new int[8];
        int [] gece_derece = new int[8];
        int [] pop = new int[8];
        Bitmap bitImage;

        @Override
        protected Void doInBackground(Void... voids) {

            String result2 = "";

            Double lat1 = Double.parseDouble(lat);
            Double lon1 = Double.parseDouble(lon);

            try {
                URL weather_url2 = new URL("https://api.openweathermap.org/data/2.5/onecall?lat=" + lat1 + "&lon=" + lon1 + "&exclude&appid=APIKEY");
                BufferedReader bufferedReader2 = null;
                bufferedReader2 = new BufferedReader(new InputStreamReader(weather_url2.openStream()));
                String line2 = null;
                while ((line2 = bufferedReader2.readLine()) != null) {
                    result2 += line2;
                }
                bufferedReader2.close();

                JSONObject jsonObject2 = new JSONObject(result2);
                JSONArray jsonArray2 = jsonObject2.getJSONArray("daily");
                for (int i = 1; i < 8; i++) {
                    JSONObject daily = jsonArray2.getJSONObject(i);
                    result_gun = daily.getLong("dt");
                    Date date = new Date(result_gun * 1000L);
                    SimpleDateFormat gun = new SimpleDateFormat("E");
                    gun.setTimeZone(TimeZone.getTimeZone("GMT+3"));
                    gunAdi[i] = gun.format(date);

                    result_pop = daily.getDouble("pop");
                    pop[i] = (int) (result_pop * 100);

                    JSONObject temp = daily.getJSONObject("temp");
                    Double valTemp = temp.getDouble("day");
                    Double geceDerece = temp.getDouble("night");
                    result_temp = (int) (valTemp - 273);
                    result_gece_temp = (int) (geceDerece -273);
                    derece[i] = result_temp;
                    gece_derece[i] = result_gece_temp;
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

            havadurumuList = new ArrayList<>();

            for(int i=1; i<8; i++)
            {
                if(gunAdi[i].equals("Pzt") || gunAdi[i].equals("Mon"))
                    gunAdi[i] = "Monday";
                else if(gunAdi[i].equals("Sal") || gunAdi[i].equals("Tue"))
                    gunAdi[i] = "Tuesday";
                else if(gunAdi[i].equals("Çar") || gunAdi[i].equals("Wed"))
                    gunAdi[i] = "Wednesday";
                else if(gunAdi[i].equals("Per") || gunAdi[i].equals("Thu"))
                    gunAdi[i] = "Thursday";
                else if(gunAdi[i].equals("Cum") || gunAdi[i].equals("Fri"))
                    gunAdi[i] = "Friday";
                else if (gunAdi[i].equals("Cmt") || gunAdi[i].equals("Sat"))
                    gunAdi[i] = "Saturday";
                else if (gunAdi[i].equals("Paz") || gunAdi[i].equals("Sun"))
                    gunAdi[i] = "Sunday";

                String gun_derece ;
                gun_derece = String.valueOf(derece[i]);
                String night_derece;
                night_derece = String.valueOf(gece_derece[i]);
                String yagis_orani;
                yagis_orani = String.valueOf(pop[i]);

                String gorsel= null;

                if (derece[i] < 0){
                    gorsel = "karli";
                } else if (derece[i] <= 15) {
                    gorsel = "bulutlu";
                }else if(derece[i]<=22){
                    gorsel = "gunesli_bulutlu";
                }else if(derece[i]<=38){
                    gorsel = "gunesli";
                }

                HaftalikModel h1 = new HaftalikModel(1,gorsel,gunAdi[i], yagis_orani+"%", gun_derece+"°"+" / "+night_derece+"°");
                havadurumuList.add(h1);
            }

            adapter = new HaftalikAdapter(getContext(),havadurumuList);

            recyclerView.setAdapter(adapter);

            super.onPostExecute(aVoid);
        }
    }

}