package com.example.kouveepetshopcustomer.ui.produk;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kouveepetshopcustomer.MainActivity;
import com.example.kouveepetshopcustomer.R;
import com.example.kouveepetshopcustomer.ui.produk.ProdukDAO;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ViewProcessHolder> implements Filterable {
    private Context context;
    private ArrayList<ProdukDAO> item, itemFilterd;
    private String ip = MainActivity.getIp();
    private String url = MainActivity.getUrl();
    private Dialog mDialog;

    public ProdukAdapter(Context context, ArrayList<ProdukDAO> item) {
        this.context = context;
        this.item = item;
        this.itemFilterd = item;
    }

    @Override
    public ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk, parent, false);
        return new ViewProcessHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewProcessHolder holder, final int position) {
        String link = ip + url;
        String substring, concat;

        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

        final ProdukDAO data = itemFilterd.get(position);
        holder.nama.setText(data.nama);
        holder.harga.setText(formatRupiah.format(data.harga));
        concat = "Stok: "+data.jumlah;
        holder.jumlah.setText(concat);

        substring = data.link_gambar.substring(47);
        final String link_gambar = link + substring;
        Picasso.get().load(link_gambar).into(holder.gambar);

//        holder.itemList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDialog(data.id);
//            }
//        });
    }

//    private void openDialog(Integer id){
//        Bundle b = new Bundle();
//        b.putInt("id", id);
//        b.putInt("isKeranjang", 0);
//
//        Dialog_Transaksi_penjualan dialog_transaksi_penjualan = new Dialog_Transaksi_penjualan();
//        dialog_transaksi_penjualan.setArguments(b);
//        dialog_transaksi_penjualan.show(fragmentManager, "mTag");
//    }

    @Override
    public int getItemCount() {
        return itemFilterd.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    itemFilterd = item;
                } else {
                    ArrayList<ProdukDAO> filteredList = new ArrayList<>();
                    for (ProdukDAO row : item) {
                        if (row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getKategori().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    itemFilterd = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemFilterd;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemFilterd = (ArrayList<ProdukDAO>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {

        Integer id;
        TextView nama, harga, jumlah;
        CardView itemList;
        ImageView gambar;

        public ViewProcessHolder(@NonNull final View itemView) {
            super(itemView);

            context = itemView.getContext();
            nama = itemView.findViewById(R.id.nama);
            harga = itemView.findViewById(R.id.harga);
            jumlah = itemView.findViewById(R.id.jumlah);
            itemList = itemView.findViewById(R.id.list_produk_id);
            gambar = itemView.findViewById(R.id.gambar);
        }
    }
}