package com.example.nathy.sinopfilms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nathy.sinopfilms.Filmes;
import com.example.nathy.sinopfilms.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class FilmesAdapter extends ArrayAdapter<Filmes> {
    private static Map<String, Bitmap> imagens = new HashMap<>();
    private static Map<String, Bitmap> backdrops = new HashMap<>();

    public static Map<String, Bitmap> getImagens() {
        return imagens;
    }

    public FilmesAdapter(@NonNull Context context, List<Filmes> filmesList) {
        super(context, -1, filmesList);
    }

    private class ViewHolder {
        public ImageView bannerImageView;
        public TextView tituloTextView;
        public TextView overview;
        public ImageView fundoImageView;
    }

    @SuppressLint("StringFormatInvalid")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();
        Filmes atual = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_filmes, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.tituloTextView = convertView.findViewById(R.id.tituloDescricaoTextView);
            viewHolder.overview = convertView.findViewById(R.id.overview);
            viewHolder.bannerImageView = convertView.findViewById(R.id.bannerImageView);
            viewHolder.fundoImageView = convertView.findViewById(R.id.fundoImageView);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        if (imagens.containsKey(atual.banner)){
            Bitmap imagem = imagens.get(atual.banner);
            viewHolder.bannerImageView.setImageBitmap(imagem);
        } else {
            new baixandoImagem(viewHolder.bannerImageView, atual.banner).
                    execute(context.getString(R.string.web_service_url_banner, atual.banner));

        }

        viewHolder.tituloTextView.setText(context.getString(R.string.tituloDoFilme, atual.titulo));
        viewHolder.overview.setText(atual.overview);
        return convertView;
    }
    private class baixandoImagem extends AsyncTask<String, Void, Bitmap> {
        private ImageView filmeImageView;
        private String nomeIcone;
        baixandoImagem(ImageView filmeImageView, String iconName){
            this.filmeImageView = filmeImageView;
            this.nomeIcone = iconName;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
            filmeImageView.setImageBitmap (imagem);
            imagens.put(nomeIcone,  imagem);
        }
    }
}

