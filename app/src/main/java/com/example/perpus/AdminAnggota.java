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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AdminAnggota extends AppCompatActivity {


    RecyclerView recyclerView;
    FirebaseRecyclerOptions<AdapterAnggota> optionsAnggota;
    FirebaseRecyclerAdapter<AdapterAnggota, ViewHolderAnggota> adapter;

    FloatingActionButton ft_btndel,ft_btnedit,ft_btnadd;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_anggota);

        ref = FirebaseDatabase.getInstance().getReference().child("Anggota");

        //recyclerview--------
        recyclerView = findViewById(R.id.recyclerviewAnggota);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        //--------------------------------------

        //floating button------------
        //tambah
        ft_btnadd = findViewById(R.id.ft_btnadd);
        ft_btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminAnggota.this, AdminTambahAnggota.class);
                startActivity(intent);
            }
        });
        //edit
        ft_btnedit = findViewById(R.id.ft_btnedit);
        ft_btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditData("");
            }
        });
        //hapus
        ft_btndel = findViewById(R.id.ft_btndel);
        ft_btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusData("");
            }
        });

        LoadData("");
    }

    private void LoadData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Query query=ref.orderByChild("Nama").startAt(data).endAt(data+"\uf8ff");

        optionsAnggota = new FirebaseRecyclerOptions.Builder<AdapterAnggota>().setQuery(query, AdapterAnggota.class).build();
        adapter = new FirebaseRecyclerAdapter<AdapterAnggota, ViewHolderAnggota>(optionsAnggota) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolderAnggota holder, final int position, @NonNull AdapterAnggota model) {
                holder.txtrowanggota1.setText(model.getNama());
                holder.txtrowanggota2.setText(model.getNip());
                Picasso.get().load(model.getImageUrl()).into(holder.imgrowanggota);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminAnggota.this, AdminAnggotaView.class);
                        intent.putExtra("AKey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public ViewHolderAnggota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_anggota, parent, false);
                return new ViewHolderAnggota(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void EditData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Query query=ref.orderByChild("Nama").startAt(data).endAt(data+"\uf8ff");

        optionsAnggota = new FirebaseRecyclerOptions.Builder<AdapterAnggota>().setQuery(query, AdapterAnggota.class).build();
        adapter = new FirebaseRecyclerAdapter<AdapterAnggota, ViewHolderAnggota>(optionsAnggota) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderAnggota holder, final int position, @NonNull final AdapterAnggota model) {
                holder.txtrowanggota1.setText(model.getNama());
                holder.txtrowanggota2.setText(model.getNip());
                Picasso.get().load(model.getImageUrl()).into(holder.imgrowanggota);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminAnggota.this, AdminAnggotaView.class);
                        intent.putExtra("AKey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.row_btnedit.setVisibility(View.VISIBLE);
                holder.row_btnedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final DialogPlus dialogPlus = DialogPlus.newDialog(holder.txtrowanggota1.getContext())
                                .setContentHolder(new ViewHolder(R.layout.activity_admin_edit_anggota))
                                .setExpanded(true,1500)
                                .create();

                        dialogPlus.show();
                        View myview=dialogPlus.getHolderView();
                        //----------------------------------------------------
                        final EditText nama = myview.findViewById(R.id.nama);

                        final EditText jenkel = myview.findViewById(R.id.jenkel);

                        final EditText email = myview.findViewById(R.id.email);
                        final EditText nip = myview.findViewById(R.id.nip);
                        final EditText notelp = myview.findViewById(R.id.notelp);
                        final EditText alamat = myview.findViewById(R.id.alamat);

                        Button update = myview.findViewById(R.id.btn_update);

                        nama.setText(model.getNama());

                        jenkel.setText(model.getJenis_Kelamin());

                        email.setText(model.getEmail());
                        nip.setText(model.getNip());
                        notelp.setText(model.getNomor_Telpon());
                        alamat.setText(model.getAlamat());


                        dialogPlus.show();
                        //refrence database
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String,Object> map = new HashMap<>();

                                map.put("Nama",nama.getText().toString());
                                map.put("Jenis_Kelamin",jenkel.getText().toString());

                                map.put("Email",email.getText().toString());
                                map.put("Nip",nip.getText().toString());
                                map.put("Nomor_Telpon", notelp.getText().toString());
                                map.put("Alamat", alamat.getText().toString());


                                FirebaseDatabase.getInstance().getReference().child("Anggota")
                                        .child(getRef(position).getKey()).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AdminAnggota.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
                                                dialogPlus.dismiss();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogPlus.dismiss();
                                            }
                                        });
                            }
                        });
                    }
                });


            }

            @NonNull
            @Override
            public ViewHolderAnggota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_anggota, parent, false);
                return new ViewHolderAnggota(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void HapusData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Query query=ref.orderByChild("Nama").startAt(data).endAt(data+"\uf8ff");

        optionsAnggota = new FirebaseRecyclerOptions.Builder<AdapterAnggota>().setQuery(query, AdapterAnggota.class).build();
        adapter = new FirebaseRecyclerAdapter<AdapterAnggota, ViewHolderAnggota>(optionsAnggota) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderAnggota holder, final int position, @NonNull AdapterAnggota model) {
                holder.txtrowanggota1.setText(model.getNama());
                holder.txtrowanggota2.setText(model.getNip());
                Picasso.get().load(model.getImageUrl()).into(holder.imgrowanggota);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminAnggota.this, AdminAnggotaView.class);
                        intent.putExtra("AKey", getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.row_btndel.setVisibility(View.VISIBLE);
                holder.row_btndel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.txtrowanggota1.getContext());
                        builder.setTitle("Delet");
                        builder.setMessage("Delete....");



                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Anggota").child(getRef(position).getKey()).removeValue();
                                FirebaseStorage.getInstance().getReference().child("Image_Anggota").child(getRef(position).getKey()).delete();

                                Intent intent = new Intent(AdminAnggota.this, AdminAnggota.class);
                                Toast.makeText(AdminAnggota.this, "Data Berhasi Dihapus", Toast.LENGTH_SHORT).show();
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
            public ViewHolderAnggota onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_anggota, parent, false);
                return new ViewHolderAnggota(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminAnggota.this, AdminHome.class);
        startActivity(intent);
        finish();
    }
}