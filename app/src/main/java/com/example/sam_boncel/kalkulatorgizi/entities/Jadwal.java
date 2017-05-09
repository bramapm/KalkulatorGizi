package com.example.sam_boncel.kalkulatorgizi.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by BRM on 5/9/2017.
 */

public class Jadwal {
    private String id_jadwal;
    private String id_user;
    private String id_olahraga;
    private String tanggal;
    private String kalori;

    private JSONObject json;

    public Jadwal(JSONObject json) throws JSONException {
        this.json = json;
        this.id_jadwal = this.json.getString("id_jadwal");
        this.id_user = this.json.getString("id_user");
        this.id_olahraga = this.json.getString("id_olahraga");
        this.tanggal = this.json.getString("tanggal");
        this.kalori = this.json.getString("kalori");
    }

    public String getId_jadwal() {
        return id_jadwal;
    }

    public void setId_jadwal(String id_jadwal) {
        this.id_jadwal = id_jadwal;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_olahraga() {
        return id_olahraga;
    }

    public void setId_olahraga(String id_olahraga) {
        this.id_olahraga = id_olahraga;
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
