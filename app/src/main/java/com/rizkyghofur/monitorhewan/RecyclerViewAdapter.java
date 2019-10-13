package com.rizkyghofur.monitorhewan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
        final double SuhuBaru = listData.get(position).getSuhu();
        final int DetakJantungBaru = listData.get(position).getDetakJantung();
        final double Lat = listData.get(position).getLat();
        final double Log = listData.get(position).getLog();

        String LatLongBaru = String.format("%.2f", Lat) + ", " + String.format("%.2f", Log);

        holder.Tanggal.setText("Tanggal : "+TanggalBaru);
        holder.Id.setText("ID Device : "+IdBaru);
        holder.Suhu.setText("Suhu : "+SuhuBaru+ "°C");
        holder.DetakJantung.setText("Detak Jantung : \n"+DetakJantungBaru + " bpm");
        holder.Latlong.setText("Lokasi : \n"+LatLongBaru);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Tanggal, Id, Suhu, DetakJantung, Latlong;

        private Button btnSuhu, btnJantung, btnMap;

        ViewHolder(final View itemView) {
            super(itemView);
            Tanggal = itemView.findViewById(R.id.tgl);
            Id = itemView.findViewById(R.id.iddev);
            Suhu = itemView.findViewById(R.id.suhu);
            DetakJantung = itemView.findViewById(R.id.dtkjtg);
            Latlong = itemView.findViewById(R.id.latlong);

            btnSuhu = itemView.findViewById(R.id.gfsuhu);
            btnJantung = itemView.findViewById(R.id.gfkdtkjtg);
            btnMap = itemView.findViewById(R.id.gfklatlong);

            btnSuhu.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Toast.makeText(v.getContext(),"Button Suhu "+position, Toast.LENGTH_LONG).show();

                    Context context = v.getContext();
                    double SuhuBaru = listData.get(position).getSuhu();
                    Intent intent = new Intent(context, Suhu.class);
                    intent.putExtra("nilai_suhu", SuhuBaru);
                    intent.putExtra("nilai_x", "13");
                    context.startActivity(intent);


                }
            });

            btnJantung.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Toast.makeText(v.getContext(),"Button Jantung "+position, Toast.LENGTH_LONG).show();
                }
            });

            btnMap.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    Toast.makeText(v.getContext(),"Button Jantung "+position, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}