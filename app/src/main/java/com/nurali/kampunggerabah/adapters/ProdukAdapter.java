package com.nurali.kampunggerabah.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.nurali.kampunggerabah.R;
import com.nurali.kampunggerabah.activities.PengrajinDetailActivity;
import com.nurali.kampunggerabah.activities.ProdukDetailActivity;
import com.nurali.kampunggerabah.api.Helper;
import com.nurali.kampunggerabah.api.responses.PenggunaResponse;
import com.nurali.kampunggerabah.api.responses.ProdukResponse;

import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewHolder> {
    private static List<ProdukResponse.ProdukModel> list;
    Context context;

    public ProdukAdapter(List<ProdukResponse.ProdukModel> list, Context context) {
        ProdukAdapter.list = list;
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
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_produk, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, ProdukDetailActivity.class);
                i.putExtra("id_produk", list.get(position).getIdProduk());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.judul.setText(list.get(position).getNama());
        holder.harga.setText(Helper.formatRupiah(Integer.parseInt(list.get(position).getHargaSatuan())));

        Glide.with(context)
                .load(context.getString(R.string.base_url) + context.getString(R.string.produk_link) + list.get(position).getGambar())
                .centerCrop()
                .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, harga;
        private ImageView iv;
        private MaterialCardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judulProduk);
            harga = itemView.findViewById(R.id.harga);
            iv = itemView.findViewById(R.id.gambarProduk);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}

