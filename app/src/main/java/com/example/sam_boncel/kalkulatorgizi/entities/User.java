package com.example.sam_boncel.kalkulatorgizi.entities;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sam_Boncel on 12/03/2017.
 */

public class User {
    private String id_user;
    private String username;
    private String password;
    private String email;
    private String nama_user;
    private String jk;
    private String ttl;
    private String tinggi;
    private String berat;
    private String umur;
    private String status;
    private String kalori;

    private JSONObject json;

    public User(JSONObject json) throws JSONException{
        this.json = json;
        this.id_user = this.json.getString("id_user");
        this.username = this.json.getString("username");
        this.password = this.json.getString("password");
        this.email = this.json.getString("email");
        this.nama_user = this.json.getString("nama_user");
        this.jk = this.json.getString("jk");
        this.ttl = this.json.getString("ttl");
        this.tinggi = this.json.getString("tinggi");
        this.berat = this.json.getString("berat");
        this.umur = this.json.getString("umur");
        this.kalori = this.json.getString("kalori");
        this.status = this.json.getString("status");
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getTinggi() {
        return tinggi;
    }

    public void setTinggi(String tinggi) {
        this.tinggi = tinggi;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getStatus() {
        return status;
    }

    public String getKalori() {
        return kalori;
    }

    public void setKalori(String kalori) {
        this.kalori = kalori;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
