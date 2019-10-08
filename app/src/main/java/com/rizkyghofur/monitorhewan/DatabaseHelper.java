package com.rizkyghofur.monitorhewan;

public class DatabaseHelper {

    int id_hewan;
    String tanggal;
    String time;
    int detakjantung;
    double suhu;
    double lat;
    double log;

    public DatabaseHelper(){
    }

    public DatabaseHelper(int id, String tanggal, String time, int detakjantung, double suhu, double lat, double log) {
        this.id_hewan = id;
        this.tanggal = tanggal;
        this.detakjantung = detakjantung;
        this.suhu = suhu;
        this.lat = lat;
        this.log = log;
        this.time = time;
    }

    public int getId() {
        return id_hewan;
    }
    public String getTanggal() {
        return tanggal;
    }

    public String getTime() {
        return time;
    }

    public int getDetakJantung() {
        return detakjantung;
    }

    public double getSuhu() {
        return suhu;
    }

    public double getLat() {
        return lat;
    }

    public double getLog() {
        return log;
    }
}