package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;

public class AdminKoleksiDigital extends AppCompatActivity {

    FloatingActionButton ft_btnadddidital, ft_btndeldigital;


    RecyclerView recyclerView;
    FirebaseRecyclerOptions<AdapterEbook> optionsEbook;
    FirebaseRecyclerAdapter<AdapterEbook, ViewHolderEbook> adapter;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_koleksi_digital);

        ref = FirebaseDatabase.getInstance().getReference().child("Ebook");

        //ft btn add
        ft_btnadddidital = findViewById(R.id.ft_btnadddigital);
        ft_btnadddidital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminKoleksiDigital.this,AdminTambahDigital.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        LoadData("");

    }

    private void LoadData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Query query=ref.orderByChild("Nama").startAt(data).endAt(data+"\uf8ff");

        optionsEbook=new FirebaseRecyclerOptions.Builder<AdapterEbook>().setQuery(query,AdapterEbook.class).build();
        adapter=new FirebaseRecyclerAdapter<AdapterEbook, ViewHolderEbook>(optionsEbook) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderEbook holder, final int position, @NonNull AdapterEbook model) {

                holder.row_digitalnama.setText(model.getNama());

                //view
                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(AdminKoleksiDigital.this,EbookViewActivity.class);
                        intent.putExtra("EbookKey",getRef(position).getKey());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });

                //hapus
                holder.btn_rowdigitaldel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.row_digitalnama.getContext());
                        builder.setTitle("Delet");
                        builder.setMessage("Delete....");



                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Ebook").child(getRef(position).getKey()).removeValue();
                                FirebaseStorage.getInstance().getReference().child("Ebook").child(getRef(position).getKey()).delete();

                                Intent intent = new Intent(AdminKoleksiDigital.this, AdminKoleksiDigital.class);
                                Toast.makeText(AdminKoleksiDigital.this, "Data Berhasi Dihapus", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();

                    }
                });


            }

            @NonNull
            @Override
            public ViewHolderEbook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_digital,parent,false);
                return new ViewHolderEbook(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminKoleksiDigital.this, AdminHome.class);
        startActivity(intent);
        finish();
    }
}
