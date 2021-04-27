package com.example.perpus;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

public class ViewHolderBuku extends RecyclerView.ViewHolder {
    ImageView imgviewbuku1; //metod memanggil gambar dari row_buku.xml
    TextView txtviewbuku1,txtviewbuku2;
    Button btn_rowbukudel,btn_rowbukuedit;

    View v;
    public ViewHolderBuku(@NotNull View itemView){
        super(itemView);
        imgviewbuku1=itemView.findViewById(R.id.imgviewbuku1); //id radi row.xml
        txtviewbuku1=itemView.findViewById(R.id.txtviewbuku1);
        txtviewbuku2=itemView.findViewById(R.id.txtviewbuku2);

        btn_rowbukudel=itemView.findViewById(R.id.btn_rowbukudel);
        btn_rowbukuedit=itemView.findViewById(R.id.btn_rowbukuedit);

        v=itemView;
    }



}
