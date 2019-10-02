package com.rizkyghofur.monitorhewan;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList<DatabaseHelper> listData;
    public RecyclerViewAdapter(ArrayList<DatabaseHelper> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String TanggalBaru = listData.get(position).getTanggal();
        final int IdBaru = listData.get(position).getId();
        final int SuhuBaru = listData.get(position).getSuhu();
        final int DetakJantungBaru = listData.get(position).getDetakJantung();
        final Long LatLongBaru = listData.get(position).getLatlong();

        holder.Tanggal.setText("Tanggal : "+TanggalBaru);
        holder.Id.setText("ID Device : "+IdBaru);
        holder.Suhu.setText("Suhu : "+SuhuBaru + "°C");
        holder.DetakJantung.setText("Detak Jantung : \n"+DetakJantungBaru + " Detak/Menit");
        holder.Latlong.setText("Lokasi : \n"+LatLongBaru);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Tanggal, Id, Suhu, DetakJantung, Latlong;

        ViewHolder(View itemView) {
            super(itemView);
            Tanggal = itemView.findViewById(R.id.tgl);
            Id = itemView.findViewById(R.id.iddev);
            Suhu = itemView.findViewById(R.id.suhu);
            DetakJantung = itemView.findViewById(R.id.dtkjtg);
            Latlong = itemView.findViewById(R.id.latlong);
        }
    }
}