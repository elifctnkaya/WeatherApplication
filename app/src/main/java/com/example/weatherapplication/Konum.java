package com.example.weatherapplication;

public class Konum {

    private String konum_id;
    private String konum_adi;

    public Konum() {
    }

    public Konum(String konum_id, String konum_adi) {
        this.konum_id = konum_id;
        this.konum_adi = konum_adi;
    }

    public String getKonum_id() {
        return konum_id;
    }

    public void setKonum_id(String konum_id) {
        this.konum_id = konum_id;
    }

    public String getKonum_adi() {
        return konum_adi;
    }

    public void setKonum_adi(String konum_adi) {
        this.konum_adi = konum_adi;
    }
}
