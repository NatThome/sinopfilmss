package com.example.nathy.sinopfilms.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nathy.sinopfilms.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FilmesSinopseActivity extends AppCompatActivity {
    private TextView descricaoOverviewTextView;
    private TextView descricaoTituloTextView;
    private ImageView fundoImageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes_descricao   );

        getSupportActionBar().setTitle("Detalhes do Filme");
        progressBar = findViewById(R.id.progressBar);

        Bundle bundle = getIntent().getExtras();
        String titulo = bundle.getString("titulo");
        String overview = bundle.getString("overview");
        String banner = bundle.getString("banner");
        String fundo = bundle.getString("fundo");

        descricaoOverviewTextView = findViewById(R.id.overviewTextView);
        descricaoOverviewTextView.setText(overview);

        descricaoTituloTextView = findViewById(R.id.tituloDescricaoTextView);
        descricaoTituloTextView.setText(titulo);

        fundoImageView = findViewById(R.id.fundoImageView);
        new BaixandoImagem(fundo).execute(this.getString(R.string.web_service_url_banner, fundo));

        progressBar.setVisibility(View.INVISIBLE);

    }

    private class BaixandoImagem extends AsyncTask<String, Void, Bitmap> {

        private String nomeIcone;
        BaixandoImagem(String nomeIcone){

            this.nomeIcone = nomeIcone;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                Bitmap imagem = BitmapFactory.decodeStream(inputStream);
                return imagem;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap imagem) {
            fundoImageView.setImageBitmap (imagem);
        }
    }
}

