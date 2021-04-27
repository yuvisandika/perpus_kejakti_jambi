package com.example.perpus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

public class ViewHolderPeminjaman extends RecyclerView.ViewHolder {

    TextView row_txtnamabuku,row_txtnamaanggota,row_peminjamanpinjam,row_peminjamankembali;
    Button row_btnedit,row_btndel,row_btncall;
    View v;

    public ViewHolderPeminjaman(@NotNull View itemView){
        super(itemView);

        row_txtnamabuku=itemView.findViewById(R.id.row_txtnamabuku);
        row_txtnamaanggota=itemView.findViewById(R.id.row_txtnamaanggota);
        row_peminjamanpinjam=itemView.findViewById(R.id.row_peminjamanpinjam);
        row_peminjamankembali=itemView.findViewById(R.id.row_peminjamankembali);

        row_btnedit = itemView.findViewById(R.id.row_btnedit);
        row_btndel = itemView.findViewById(R.id.row_btndel);
        row_btncall = itemView.findViewById(R.id.row_btncall);

        v=itemView;
    }


}
