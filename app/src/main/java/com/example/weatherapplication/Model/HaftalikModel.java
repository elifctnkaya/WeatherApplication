package com.example.weatherapplication.Model;

public class HaftalikModel {

    private int haftalik_id;
    private String haftalik_gorsel_adi;
    private String haftalik_gun_adi;
    private String haftalik_gun_hava_durumu;
    private String haftalik_gun_derece;

    public HaftalikModel() {
    }

    public HaftalikModel(int haftalik_id, String haftalik_gorsel_adi, String haftalik_gun_adi, String haftalik_gun_hava_durumu, String haftalik_gun_derece) {
        this.haftalik_id = haftalik_id;
        this.haftalik_gorsel_adi = haftalik_gorsel_adi;
        this.haftalik_gun_adi = haftalik_gun_adi;
        this.haftalik_gun_hava_durumu = haftalik_gun_hava_durumu;
        this.haftalik_gun_derece = haftalik_gun_derece;
    }

    public int getHaftalik_id() {
        return haftalik_id;
    }

    public void setHaftalik_id(int haftalik_id) {
        this.haftalik_id = haftalik_id;
    }

    public String getHaftalik_gorsel_adi() {
        return haftalik_gorsel_adi;
    }

    public void setHaftalik_gorsel_adi(String haftalik_gorsel_adi) {
        this.haftalik_gorsel_adi = haftalik_gorsel_adi;
    }

    public String getHaftalik_gun_adi() {
        return haftalik_gun_adi;
    }

    public void setHaftalik_gun_adi(String haftalik_gun_adi) {
        this.haftalik_gun_adi = haftalik_gun_adi;
    }

    public String getHaftalik_gun_hava_durumu() {
        return haftalik_gun_hava_durumu;
    }

    public void setHaftalik_gun_hava_durumu(String haftalik_gun_hava_durumu) {
        this.haftalik_gun_hava_durumu = haftalik_gun_hava_durumu;
    }

    public String getHaftalik_gun_derece() {
        return haftalik_gun_derece;
    }

    public void setHaftalik_gun_derece(String haftalik_gun_derece) {
        this.haftalik_gun_derece = haftalik_gun_derece;
    }
}
