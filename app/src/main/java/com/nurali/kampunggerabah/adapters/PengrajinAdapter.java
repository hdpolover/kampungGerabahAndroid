package com.nurali.kampunggerabah.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.activities.PengrajinDetailActivity;
import com.nurali.kampunggerabah.api.ApiInterface;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;

import java.util.List;

public class PengrajinAdapter extends RecyclerView.Adapter<PengrajinAdapter.ViewHolder> {
    private static List<PenggunaResponse.PenggunaModel> list;
    Context context;

    public PengrajinAdapter(List<PenggunaResponse.PenggunaModel> list, Context context) {
        PengrajinAdapter.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pengrajin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PengrajinDetailActivity.class);
                i.putExtra("id_pengguna", list.get(position).getIdPengguna());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.nama.setText(list.get(position).getUsername());
        holder.alamat.setText(list.get(position).getAlamat());

        Glide.with(context)
                .load(context.getString(R.string.base_url) + context.getString(R.string.profile_link) + list.get(position).getFoto())
                .centerCrop()
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nama, alamat;
        private ImageView iv;
        private MaterialCardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.namaTv);
            alamat = itemView.findViewById(R.id.alamatTv);
            iv = itemView.findViewById(R.id.fotoIv);
            cv = itemView.findViewById(R.id.pengrajinCv);
        }
    }
}

