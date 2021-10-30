package com.example.pboyusuf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;

    ArrayList<Hasil> list;


    public MyAdapter(Context context, ArrayList<Hasil> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Hasil hasil = list.get(position);
        holder.Jawab.setText(hasil.getJawab());
        holder.Nilai.setText(hasil.getNilai());
        holder.Tebak.setText(hasil.getTebak());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Jawab, Nilai, Tebak;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Jawab = itemView.findViewById(R.id.tvfirstName);
            Tebak = itemView.findViewById(R.id.tvangka1);

            Nilai = itemView.findViewById(R.id.tvlastName);
        }
    }

}
