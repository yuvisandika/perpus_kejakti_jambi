package com.example.perpus;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

public class ViewHolderAnggota extends RecyclerView.ViewHolder {

    ImageView imgrowanggota; //metod memanggil gambar dari row_anggota.xml
    TextView txtrowanggota1,txtrowanggota2;
    Button row_btndel,row_btnedit;

    View v;
    public ViewHolderAnggota(@NotNull View itemView){
        super(itemView);
        imgrowanggota=itemView.findViewById(R.id.imgrowanggota); //id dari row.xml
        txtrowanggota1=itemView.findViewById(R.id.txtrowanggota1);
        txtrowanggota2=itemView.findViewById(R.id.txtrowanggota2);

        row_btndel=itemView.findViewById(R.id.row_btndel);
        row_btnedit=itemView.findViewById(R.id.row_btnedit);

        v=itemView;
    }
}
