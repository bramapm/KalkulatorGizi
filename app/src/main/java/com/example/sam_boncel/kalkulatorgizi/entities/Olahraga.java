package com.example.sam_boncel.kalkulatorgizi.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BRM on 4/11/2017.
 */

public class Olahraga {
    private String id_olahraga;
    private String nama_olahraga;
    private String kkal;
    private String keterangan;
    private String foto;

    private JSONObject json;

    public Olahraga(JSONObject json) throws JSONException{
        this.json = json;
        this.id_olahraga = this.json.getString("id_olahraga");
        this.nama_olahraga = this.json.getString("nama_olahraga");
        this.kkal = this.json.getString("kkal");
        this.keterangan = this.json.getString("keterangan");
        this.foto = this.json.getString("foto");
    }

    public String getId_olahraga() {
        return id_olahraga;
    }

    public void setId_olahraga(String id_olahraga) {
        this.id_olahraga = id_olahraga;
    }

    public String getNama_olahraga() {
        return nama_olahraga;
    }

    public void setNama_olahraga(String nama_olahraga) {
        this.nama_olahraga = nama_olahraga;
    }

    public String getKkal() {
        return kkal;
    }

    public void setKkal(String kkal) {
        this.kkal = kkal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
