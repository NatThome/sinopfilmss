package com.example.nathy.sinopfilms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.nathy.sinopfilms.GeneroFilme;
import com.example.nathy.sinopfilms.R;
import com.example.nathy.sinopfilms.adapter.GeneroFilmeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<GeneroFilme> generoFilmeList;
    private GeneroFilmeAdapter adapter;
    private ListView generoListView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Genero");

        progressBar = findViewById(R.id.progressBar);
        generoFilmeList = new ArrayList<>();
        generoListView = findViewById(R.id.generoListView);

        adapter = new GeneroFilmeAdapter(this, generoFilmeList);
        generoListView.setAdapter(adapter);

        String end = getString(R.string.web_service_url_genero, getString(R.string.api_key), getString(R.string.lang));
        new getGeneros().execute(end);

        generoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GeneroFilme genero = (GeneroFilme) generoListView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, FilmesActivity.class);
                intent.putExtra("idGenero", genero.idGenero);
                startActivity(intent);
            }
        });
    }

    private class getGeneros extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... enderecos) {
            try{
                URL url = new URL(enderecos[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String linha = null;
                final StringBuilder stringBuilder = new StringBuilder("");
                while ((linha = reader.readLine()) != null)
                    stringBuilder.append(linha);
                reader.close();
                return stringBuilder.toString();
            }
            catch (MalformedURLException e){
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonS) {
            generoFilmeList.clear();
            try {
                JSONObject json = new JSONObject(jsonS);
                JSONArray list = json.getJSONArray("generos");
                for (int i = 0; i < list.length(); i++) {
                    JSONObject previsao = list.getJSONObject(i);
                    int genreId = previsao.getInt("id");
                    String genreName = previsao.getString("nome");
                    GeneroFilme forecast = new GeneroFilme(genreId, genreName);
                    generoFilmeList.add(forecast);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

