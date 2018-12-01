package com.example.nathy.sinopfilms;

public class FilmesSinopse {
    public String generoFilmes;
    public String filmes;
    public String informacoes;
    public String icone;

    public FilmesSinopse(String ngenero, String nfilme, String detalhe, String dicon){
        this.generoFilmes = ngenero;
        this.filmes = nfilme;
        this.informacoes = detalhe;
        this.icone = dicon;
    }


    public String getGeneroFilmes() {
        return generoFilmes;
    }

    public void setGeneroFilmes(String generoFilmes) {
        this.generoFilmes = generoFilmes;
    }

    public String getNfilme() {
        return filmes;
    }

    public void setNfilme(String nfilme) {
        this.filmes = nfilme;
    }

    public String getInformacoes() {
        return informacoes;
    }

    public void setInformacoes(String informacoes) {
        this.informacoes = informacoes;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }
}
