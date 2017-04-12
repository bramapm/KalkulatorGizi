package com.example.sam_boncel.kalkulatorgizi.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam_Boncel on 22/03/2017.
 */

public class Makanan {
    private String id_makanan;
    private String nama_makanan;
    private String jenis;
    private String kkal;
    private String karbo;
    private String protein;
    private String lemak;
    private String foto;
    private String keterangan;

    private JSONObject json;

    public Makanan(JSONObject json) throws JSONException{
        this.json = json;
        this.id_makanan = this.json.getString("id_makanan");
        this.nama_makanan = this.json.getString("nama_makanan");
        this.jenis = this.json.getString("jenis");
        this.kkal = this.json.getString("kkal");
        this.karbo = this.json.getString("karbo");
        this.protein = this.json.getString("protein");
        this.lemak = this.json.getString("lemak");
        this.foto = this.json.getString("foto");
        this.keterangan = this.json.getString("keterangan");
    }

    public String getId_makanan() {
        return id_makanan;
    }

    public void setId_makanan(String id_makanan) {
        this.id_makanan = id_makanan;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKkal() {
        return kkal;
    }

    public void setKkal(String kkal) {
        this.kkal = kkal;
    }

    public String getKarbo() {
        return karbo;
    }

    public void setKarbo(String karbo) {
        this.karbo = karbo;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getLemak() {
        return lemak;
    }

    public void setLemak(String lemak) {
        this.lemak = lemak;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
