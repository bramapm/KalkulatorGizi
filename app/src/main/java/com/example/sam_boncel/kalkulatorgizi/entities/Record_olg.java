package com.example.sam_boncel.kalkulatorgizi.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bncl on 5/6/2017.
 */

public class Record_olg {
    private String id_recordolg;
    private String id_user;
    private String id_olg;
    private String kat_waktu;
    private String tanggal;
    private String kalori;

    private JSONObject json;

    public Record_olg (JSONObject json) throws JSONException{
        this.json = json;
        this.id_recordolg = this.json.getString("id_recordolg");
        this.id_user = this.json.getString("id_user");
        this.id_olg = this.json.getString("id_olg");
        this.kat_waktu = this.json.getString("kat_waktu");
        this.tanggal = this.json.getString("tanggal");
        this.kalori = this.json.getString("kalori");
    }

    public String getId_recordolg() {
        return id_recordolg;
    }

    public void setId_recordolg(String id_recordolg) {
        this.id_recordolg = id_recordolg;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_olg() {
        return id_olg;
    }

    public void setId_olg(String id_olg) {
        this.id_olg = id_olg;
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
