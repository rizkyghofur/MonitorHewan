package com.rizkyghofur.monitorhewan;

public class DatabaseHelper {

    int id;
    String tanggal;
    int detakjantung;
    int suhu;
    Long latlong;

    public DatabaseHelper(){
    }

    public DatabaseHelper(int id, String tanggal, int detakjantung, int suhu, Long latlong){
        this.id = id;
        this.tanggal = tanggal;
        this.detakjantung = detakjantung;
        this.suhu = suhu;
        this.latlong = latlong;
    }

    public int getId() {
        return id;
    }
    public String getTanggal() {
        return tanggal;
    }
    public int getDetakJantung() {
        return detakjantung;
    }
    public int getSuhu() {
        return suhu;
    }
    public Long getLatlong() {
        return latlong;
    }
}
