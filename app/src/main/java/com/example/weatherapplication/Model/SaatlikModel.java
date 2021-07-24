package com.example.weatherapplication.Model;

public class SaatlikModel {

    private int saatlik_id;
    private String saat_dilimi;
    private String saatlik_gorsel_adi;
    private String saatlik_derece;
    private String saatlik_yagmur_durumu;

    public SaatlikModel() {
    }

    public SaatlikModel(int saatlik_id, String saat_dilimi, String saatlik_gorsel_adi, String saatlik_derece, String saatlik_yagmur_durumu) {
        this.saatlik_id = saatlik_id;
        this.saat_dilimi = saat_dilimi;
        this.saatlik_gorsel_adi = saatlik_gorsel_adi;
        this.saatlik_derece = saatlik_derece;
        this.saatlik_yagmur_durumu = saatlik_yagmur_durumu;
    }

    public int getSaatlik_id() {
        return saatlik_id;
    }

    public void setSaatlik_id(int saatlik_id) {
        this.saatlik_id = saatlik_id;
    }

    public String getSaat_dilimi() {
        return saat_dilimi;
    }

    public void setSaat_dilimi(String saat_dilimi) {
        this.saat_dilimi = saat_dilimi;
    }

    public String getSaatlik_gorsel_adi() {
        return saatlik_gorsel_adi;
    }

    public void setSaatlik_gorsel_adi(String saatlik_gorsel_adi) {
        this.saatlik_gorsel_adi = saatlik_gorsel_adi;
    }

    public String getSaatlik_derece() {
        return saatlik_derece;
    }

    public void setSaatlik_derece(String saatlik_derece) {
        this.saatlik_derece = saatlik_derece;
    }

    public String getSaatlik_yagmur_durumu() {
        return saatlik_yagmur_durumu;
    }

    public void setSaatlik_yagmur_durumu(String saatlik_yagmur_durumu) {
        this.saatlik_yagmur_durumu = saatlik_yagmur_durumu;
    }
}
