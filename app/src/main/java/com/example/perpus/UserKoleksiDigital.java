package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;

public class UserKoleksiDigital extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<AdapterEbook> optionsEbook;
    FirebaseRecyclerAdapter<AdapterEbook, ViewHolderEbook> adapter;

    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_koleksi_digital);

        ref = FirebaseDatabase.getInstance().getReference().child("Ebook");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        LoadData("");

    }
    private void LoadData(String data) {

        Query query=ref.orderByChild("Nama").startAt(data).endAt(data+"\uf8ff");

        optionsEbook=new FirebaseRecyclerOptions.Builder<AdapterEbook>().setQuery(query,AdapterEbook.class).build();
        adapter=new FirebaseRecyclerAdapter<AdapterEbook, ViewHolderEbook>(optionsEbook) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderEbook holder, final int position, @NonNull AdapterEbook model) {

                holder.row_digitalnama.setText(model.getNama());
                holder.btn_rowdigitaldel.setVisibility(View.INVISIBLE);

                //view
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(UserKoleksiDigital.this,EbookViewActivity.class);
                        intent.putExtra("EbookKey",getRef(position).getKey());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });


            }

            @NonNull
            @Override
            public ViewHolderEbook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_digital,parent,false);
                return new ViewHolderEbook(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }


}