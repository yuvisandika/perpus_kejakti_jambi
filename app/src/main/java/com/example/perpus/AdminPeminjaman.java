package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

public class AdminPeminjaman extends AppCompatActivity {

    FloatingActionButton ft_btnadd,ft_btndel,ft_btnedit,ft_btncall;

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<AdapterPeminjaman> optionsPeminjaman;
    FirebaseRecyclerAdapter<AdapterPeminjaman, ViewHolderPeminjaman> adapter;

    DatabaseReference ref;

    String nomor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_peminjaman);

        ref = FirebaseDatabase.getInstance().getReference().child("Peminjaman");

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //floating button
        //btn tambah
        ft_btnadd = findViewById(R.id.ft_btnadd);
        ft_btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPeminjaman.this, AdminTambahPeminjaman.class);
                startActivity(intent);
            }
        });
        //edit
        ft_btnedit = findViewById(R.id.ft_btnedit);
        ft_btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditData("");
            }
        });
        //hapus
        ft_btndel = findViewById(R.id.ft_btndel);
        ft_btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HapusData("");
            }
        });
        //telpon
        ft_btncall = findViewById(R.id.ft_btncall);
        ft_btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallData("");
            }
        });





        LoadData("");
    }

    //load
    private void LoadData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        Query query=ref.orderByChild("Tgl_pinjam").startAt(data).endAt(data+"\uf8ff");

        optionsPeminjaman = new FirebaseRecyclerOptions.Builder<AdapterPeminjaman>().setQuery(query, AdapterPeminjaman.class).build();
        adapter = new FirebaseRecyclerAdapter<AdapterPeminjaman, ViewHolderPeminjaman>(optionsPeminjaman) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderPeminjaman holder, final int position, @NonNull AdapterPeminjaman model) {

                holder.row_txtnamabuku.setText(model.getBuku());
                holder.row_txtnamaanggota.setText(model.getNama());
                holder.row_peminjamanpinjam.setText(model.getTgl_pinjam());
                holder.row_peminjamankembali.setText(model.getTgl_kembali());

            }

            @NonNull
            @Override
            public ViewHolderPeminjaman onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_peminjaman, parent, false);
                return new ViewHolderPeminjaman(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    //edit
    private void EditData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        Query query=ref.orderByChild("Tgl_pinjam").startAt(data).endAt(data+"\uf8ff");

        optionsPeminjaman = new FirebaseRecyclerOptions.Builder<AdapterPeminjaman>().setQuery(query, AdapterPeminjaman.class).build();
        adapter = new FirebaseRecyclerAdapter<AdapterPeminjaman, ViewHolderPeminjaman>(optionsPeminjaman) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderPeminjaman holder, final int position, @NonNull AdapterPeminjaman model) {

                holder.row_txtnamabuku.setText(model.getBuku());
                holder.row_txtnamaanggota.setText(model.getNama());
                holder.row_peminjamanpinjam.setText(model.getTgl_pinjam());
                holder.row_peminjamankembali.setText(model.getTgl_kembali());

                //edit
                holder.row_btnedit.setVisibility(View.VISIBLE);
                holder.row_btnedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(AdminPeminjaman.this, AdminEditPeminjaman.class);
                        intent.putExtra("PKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ViewHolderPeminjaman onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_peminjaman, parent, false);
                return new ViewHolderPeminjaman(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    //hapus
    private void HapusData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        Query query=ref.orderByChild("Tgl_pinjam").startAt(data).endAt(data+"\uf8ff");

        optionsPeminjaman = new FirebaseRecyclerOptions.Builder<AdapterPeminjaman>().setQuery(query, AdapterPeminjaman.class).build();
        adapter = new FirebaseRecyclerAdapter<AdapterPeminjaman, ViewHolderPeminjaman>(optionsPeminjaman) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderPeminjaman holder, final int position, @NonNull AdapterPeminjaman model) {

                holder.row_txtnamabuku.setText(model.getBuku());
                holder.row_txtnamaanggota.setText(model.getNama());
                holder.row_peminjamanpinjam.setText(model.getTgl_pinjam());
                holder.row_peminjamankembali.setText(model.getTgl_kembali());

                //hapus
                holder.row_btndel.setVisibility(View.VISIBLE);
                holder.row_btndel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.row_txtnamabuku.getContext());
                        builder.setTitle("Delet");
                        builder.setMessage("Delete....");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Peminjaman").child(getRef(position).getKey()).removeValue();

                                Intent intent = new Intent(AdminPeminjaman.this, AdminPeminjaman.class);
                                Toast.makeText(AdminPeminjaman.this, "Data Berhasi Dihapus", Toast.LENGTH_SHORT).show();
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
            public ViewHolderPeminjaman onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_peminjaman, parent, false);
                return new ViewHolderPeminjaman(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
    //telpon
    private void CallData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        Query query=ref.orderByChild("Tgl_pinjam").startAt(data).endAt(data+"\uf8ff");

        optionsPeminjaman = new FirebaseRecyclerOptions.Builder<AdapterPeminjaman>().setQuery(query, AdapterPeminjaman.class).build();
        adapter = new FirebaseRecyclerAdapter<AdapterPeminjaman, ViewHolderPeminjaman>(optionsPeminjaman) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderPeminjaman holder, final int position, @NonNull final AdapterPeminjaman model) {

                holder.row_txtnamabuku.setText(model.getBuku());
                holder.row_txtnamaanggota.setText(model.getNama());
                holder.row_peminjamanpinjam.setText(model.getTgl_pinjam());
                holder.row_peminjamankembali.setText(model.getTgl_kembali());

                holder.row_btncall.setVisibility(View.VISIBLE);
                holder.row_btncall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = model.getNoTelp();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+number));
                        startActivity(intent);
                    }
                });


            }

            @NonNull
            @Override
            public ViewHolderPeminjaman onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_peminjaman, parent, false);
                return new ViewHolderPeminjaman(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminPeminjaman.this, AdminHome.class);
        startActivity(intent);
        finish();
    }
}