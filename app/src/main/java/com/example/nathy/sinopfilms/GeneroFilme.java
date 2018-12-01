package com.example.nathy.sinopfilms;

public class GeneroFilme {
    public final int idGenero;
    public final String nomeGenero;

    public GeneroFilme(int idGenero, String nomeGenero) {
        this.idGenero = idGenero;
        this.nomeGenero = nomeGenero;
    }

    public String getId() {
        return String.valueOf(idGenero);
    }

    @Override
    public String toString() {
        return nomeGenero;
    }
}
