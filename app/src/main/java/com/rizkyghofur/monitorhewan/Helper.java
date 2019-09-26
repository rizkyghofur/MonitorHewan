package com.rizkyghofur.monitorhewan;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    int id;
    String tanggal;
    int detakjantung;
    int suhu;
    String latlong;

    public Helper(){

    }

    public Helper(int id, String tanggal, int detakjantung, int suhu, String latlong){
        this.id = id;
        this.tanggal = tanggal;
        this.detakjantung = detakjantung;
        this.suhu = suhu;
        this.latlong = latlong;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("tanggal", tanggal);
        result.put("detakjantung", detakjantung);
        result.put("suhu", suhu);
        result.put("latlong", latlong);
        return result;
    }
}
