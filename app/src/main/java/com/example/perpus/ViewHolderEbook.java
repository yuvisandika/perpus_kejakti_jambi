package com.example.perpus;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

public class ViewHolderEbook extends RecyclerView.ViewHolder {

    TextView row_digitalnama;
    Button btn_rowdigitaldel;
    View v;

    public ViewHolderEbook(@NotNull View itemView){
        super(itemView);

        row_digitalnama=itemView.findViewById(R.id.row_digitalnama);
        btn_rowdigitaldel = itemView.findViewById(R.id.btn_rowdigitaldel);

        v=itemView;
    }



}
