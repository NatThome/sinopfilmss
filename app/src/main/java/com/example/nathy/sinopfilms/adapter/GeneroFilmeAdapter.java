package com.example.nathy.sinopfilms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nathy.sinopfilms.GeneroFilme;
import com.example.nathy.sinopfilms.R;

import java.util.List;

public class GeneroFilmeAdapter extends ArrayAdapter<GeneroFilme> {

    private class ViewHolder {
        public TextView nomeGenero;
    }

    public GeneroFilmeAdapter(Context context, List<GeneroFilme> forecast){
        super (context, R.layout.list_genero, forecast);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_genero, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.nomeGenero = convertView.findViewById(R.id.generoTextView);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        GeneroFilme atual = getItem(position);
        viewHolder.nomeGenero.setText(atual.nomeGenero);
        return convertView;
    }
}
