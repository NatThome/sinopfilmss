package com.example.nathy.sinopfilms;

public class Filmes {

    public final int idFilme;
    public final String titulo;
    public final String banner;
    public final String overview;
    public final String fundo;


    public String getIconName() {
        return banner;
    }

    public Filmes(int idFilme, String titulo,  String banner, String overview, String fundo) {
        this.idFilme = idFilme;
        this.titulo = titulo;
        this.banner = banner;
        this.overview = overview;
        this.fundo = fundo;
    }

    @Override
    public String toString() {
        return titulo;
    }
}