package com.example.nathy.sinopfilms.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.nathy.sinopfilms.Filmes;
import com.example.nathy.sinopfilms.R;
import com.example.nathy.sinopfilms.adapter.FilmesAdapter;

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

public class FilmesActivity extends AppCompatActivity {

    private List<Filmes> filmesList;
    private FilmesAdapter filmesAdapter;
    private ListView filmesListView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);
        getSupportActionBar().setTitle("Lista de Filmes");

        progressBar = findViewById(R.id.progressBar);
        filmesList = new ArrayList<>();
        filmesListView = findViewById(R.id.filmeListView);
        filmesAdapter = new FilmesAdapter(this, filmesList);
        filmesListView.setAdapter(filmesAdapter);

        int idGen = getIntent().getIntExtra("idGenero", 0);
        String idGenero = Integer.toString(idGen);

        String end = getString(R.string.web_service_url_filme, getString(R.string.api_key), getString(R.string.lang), idGenero);
        new ObtemFilmes().execute(end);

        filmesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Filmes filmes = (Filmes) filmesListView.getItemAtPosition(position);
                Intent intent = new Intent(FilmesActivity.this, FilmesSinopseActivity.class);
                intent.putExtra("titulo", filmes.titulo);
                intent.putExtra("overview",  filmes.overview);
                intent.putExtra("banner", filmes.banner);
                intent.putExtra("fundo", filmes.fundo);
                intent.putExtra("idFilme", filmes.idFilme);
                startActivity(intent);
            }
        });
    }

    private class ObtemFilmes extends AsyncTask<String, Void, String> {
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
            super.onPostExecute(jsonS);
            try{
                filmesList.clear();
                JSONObject json = new JSONObject(jsonS);
                JSONArray list = json.getJSONArray("results");
                for (int i = 0; i < list.length(); i++){
                    JSONObject filmes = list.getJSONObject(i);
                    int idGenero = filmes.getInt("id");
                    String titulo = filmes.getString("titulo");
                    String banner = filmes.getString("banner");
                    String overview = filmes.getString("o verview");
                    String fundo = filmes.getString("fundo");
                    Filmes f = new Filmes(idGenero, titulo, banner, overview, fundo);
                    filmesList.add(f);
                }

                filmesAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}

