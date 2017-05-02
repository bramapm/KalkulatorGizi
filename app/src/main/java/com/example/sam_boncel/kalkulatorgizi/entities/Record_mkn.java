package com.example.sam_boncel.kalkulatorgizi.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BRM on 5/2/2017.
 */

public class Record_mkn {
    private String id_recordmkn;
    private String id_user;
    private String id_makanan;
    private String kat_waktu;
    private String tanggal;
    private String kalori;

    private JSONObject json;

    public Record_mkn(JSONObject json) throws JSONException {
        this.json = json;
        this.id_makanan = this.json.getString("id_makanan");
        this.id_recordmkn = this.json.getString("id_recordmkn");
        this.id_user = this.json.getString("id_user");
        this.tanggal = this.json.getString("tanggal");
        this.kalori = this.json.getString("kalori");
    }

    public String getId_recordmkn() {
        return id_recordmkn;
    }

    public void setId_recordmkn(String id_recordmkn) {
        this.id_recordmkn = id_recordmkn;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_makanan() {
        return id_makanan;
    }

    public void setId_makanan(String id_makanan) {
        this.id_makanan = id_makanan;
    }

    public String getKat_waktu() {
        return kat_waktu;
    }

    public void setKat_waktu(String kat_waktu) {
        this.kat_waktu = kat_waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKalori() {
        return kalori;
    }

    public void setKalori(String kalori) {
        this.kalori = kalori;
    }
}
