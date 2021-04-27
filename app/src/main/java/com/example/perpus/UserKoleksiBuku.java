package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class UserKoleksiBuku extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerOptions<AdapterBuku> optionsBuku;
    FirebaseRecyclerAdapter<AdapterBuku, ViewHolderBuku> adapter;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_koleksi_buku);

        ref = FirebaseDatabase.getInstance().getReference().child("Buku");

        //recyclerview--------
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        //layout manager recyclerview dalam bentuk grid view
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        //--------------------------------------

        LoadData("");

    }
    private void LoadData(String data) {

        Query query=ref.orderByChild("No_Urut").startAt(data).endAt(data+"\uf8ff");

        optionsBuku=new FirebaseRecyclerOptions.Builder<AdapterBuku>().setQuery(query,AdapterBuku.class).build();
        adapter=new FirebaseRecyclerAdapter<AdapterBuku, ViewHolderBuku>(optionsBuku) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderBuku holder, final int position, @NonNull AdapterBuku model) {
                holder.txtviewbuku1.setText(model.getJudul_Buku());
                holder.txtviewbuku2.setText(model.getNo_Urut());
                Picasso.get().load(model.getImageUrl()).into(holder.imgviewbuku1);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UserKoleksiBuku.this,BukuViewActivity.class);
                        intent.putExtra("BukuKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ViewHolderBuku onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_buku,parent,false);
                return new ViewHolderBuku(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

}