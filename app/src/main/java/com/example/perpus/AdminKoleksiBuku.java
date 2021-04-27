package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import android.widget.ImageView;
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

public class AdminKoleksiBuku extends AppCompatActivity {

    FloatingActionButton ft_btnaddbuku,ft_btndelbuku,ft_btneditbuku;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerOptions<AdapterBuku> optionsBuku;
    FirebaseRecyclerAdapter<AdapterBuku, ViewHolderBuku> adapter;

    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_koleksi_buku);

        ref = FirebaseDatabase.getInstance().getReference().child("Buku");

        //recyclerview--------
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        //layout manager recyclerview dalam bentuk grid view
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        //--------------------------------------


        //floating button------------
        //tambah---------------------
        ft_btnaddbuku = findViewById(R.id.ft_btnaddbuku);
        ft_btnaddbuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminKoleksiBuku.this, AdminTambahKoleksiBuku.class);
                startActivity(intent);
                finish();
            }
        });
        //btn hapus
        ft_btndelbuku = findViewById(R.id.ft_btndelbuku);
        ft_btndelbuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminKoleksiBuku.this, "Pilih Data Yang Ingin Dihapus", Toast.LENGTH_SHORT).show();
                HapusData("");
            }
        });
        //btn edit
        ft_btneditbuku = findViewById(R.id.ft_btneditbuku);
        ft_btneditbuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminKoleksiBuku.this, "Pilih Data Yang Ingin Diubah", Toast.LENGTH_SHORT).show();
                EditData("");
            }
        });


        LoadData(""); //variabel memanggil data dari firebase
    }


    //metod memanggil data
    private void LoadData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

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
                        Intent intent = new Intent(AdminKoleksiBuku.this,BukuViewActivity.class);
                        intent.putExtra("BukuKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ViewHolderBuku onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_buku,parent,false);
                return new ViewHolderBuku(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    //metod hapus data
    private void HapusData(String data) {

        Query query=ref.orderByChild("No_Urut").startAt(data).endAt(data+"\uf8ff");

        optionsBuku=new FirebaseRecyclerOptions.Builder<AdapterBuku>().setQuery(query,AdapterBuku.class).build();
        adapter=new FirebaseRecyclerAdapter<AdapterBuku, ViewHolderBuku>(optionsBuku) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderBuku holder, final int position, @NonNull AdapterBuku model) {
                holder.txtviewbuku1.setText(model.getJudul_Buku());
                holder.txtviewbuku2.setText(model.getNo_Urut());
                Picasso.get().load(model.getImageUrl()).into(holder.imgviewbuku1);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminKoleksiBuku.this,BukuViewActivity.class);
                        intent.putExtra("BukuKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });



                holder.btn_rowbukudel.setVisibility(View.VISIBLE);
                holder.btn_rowbukudel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.imgviewbuku1.getContext());
                        builder.setTitle("Delet");
                        builder.setMessage("Delete....");



                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Buku").child(getRef(position).getKey()).removeValue();
                                FirebaseStorage.getInstance().getReference().child("Image_Buku").child(getRef(position).getKey()).delete();

                                Intent intent = new Intent(AdminKoleksiBuku.this, AdminKoleksiBuku.class);
                                Toast.makeText(AdminKoleksiBuku.this, "Data Berhasi Dihapus", Toast.LENGTH_SHORT).show();
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
            public ViewHolderBuku onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_buku,parent,false);
                return new ViewHolderBuku(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

    //metod edit data
    private void EditData(String data) {
        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat Data . . . . ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Query query=ref.orderByChild("No_Urut").startAt(data).endAt(data+"\uf8ff");

        optionsBuku=new FirebaseRecyclerOptions.Builder<AdapterBuku>().setQuery(query,AdapterBuku.class).build();
        adapter=new FirebaseRecyclerAdapter<AdapterBuku, ViewHolderBuku>(optionsBuku) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderBuku holder, final int position, @NonNull final AdapterBuku model) {
                holder.txtviewbuku1.setText(model.getJudul_Buku());
                holder.txtviewbuku2.setText(model.getNo_Urut());
                Picasso.get().load(model.getImageUrl()).into(holder.imgviewbuku1);

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminKoleksiBuku.this,BukuViewActivity.class);
                        intent.putExtra("BukuKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.btn_rowbukuedit.setVisibility(View.VISIBLE);
                holder.btn_rowbukuedit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final DialogPlus dialogPlus = DialogPlus.newDialog(holder.txtviewbuku1.getContext())
                                .setContentHolder(new ViewHolder(R.layout.activity_admin_edit_buku))
                                .setExpanded(true,1500)
                                .create();

                        dialogPlus.show();
                        View myview=dialogPlus.getHolderView();
                        //----------------------------------------------------
                        final EditText judul = myview.findViewById(R.id.title);

                        final EditText nourut = myview.findViewById(R.id.nomor);
                        final EditText kepengarangan = myview.findViewById(R.id.kepengarangan);
                        final EditText jilid = myview.findViewById(R.id.jilid);
                        final EditText edisi = myview.findViewById(R.id.edisi);
                        final EditText cetakan = myview.findViewById(R.id.cetakan);
                        final EditText isbn = myview.findViewById(R.id.isbn);
                        final EditText issn = myview.findViewById(R.id.issn);
                        final EditText tempat = myview.findViewById(R.id.tempat);
                        final EditText penerbit = myview.findViewById(R.id.penerbit);
                        final EditText tahun = myview.findViewById(R.id.tahun);
                        final EditText klasifikasi = myview.findViewById(R.id.klasifikasi);
                        final EditText berasal = myview.findViewById(R.id.berasal);
                        final EditText keterangan = myview.findViewById(R.id.keterangan);

                        Button update = myview.findViewById(R.id.btn_Update);

                        judul.setText(model.getJudul_Buku());

                        nourut.setText(model.getNo_Urut());
                        kepengarangan.setText(model.getKeterangan());
                        jilid.setText(model.getJilid());
                        edisi.setText(model.getEdisi());
                        cetakan.setText(model.getCetakan());
                        isbn.setText(model.getIsbn());
                        issn.setText(model.getIssn());
                        tempat.setText(model.getTempat_Terbit());
                        penerbit.setText(model.getPenerbit());
                        tahun.setText(model.getTahun_Terbit());
                        klasifikasi.setText(model.getKlasifikasi());
                        berasal.setText(model.getBerasal_Dari());
                        keterangan.setText(model.getKeterangan());

                        dialogPlus.show();
                        //refrence database
                        update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String,Object> map = new HashMap<>();
                                map.put("Judul_Buku",judul.getText().toString());

                                map.put("No_Urut",nourut.getText().toString());
                                map.put("Kepengarangan",kepengarangan.getText().toString());
                                map.put("Jilid",jilid.getText().toString());
                                map.put("Edisi",edisi.getText().toString());
                                map.put("Cetakan",cetakan.getText().toString());
                                map.put("Isbn",isbn.getText().toString());
                                map.put("Issn",issn.getText().toString());
                                map.put("Tempat_Terbit",tempat.getText().toString());
                                map.put("Penerbit",penerbit.getText().toString());
                                map.put("Tahun_Terbit",tahun.getText().toString());
                                map.put("Klasifikasi",klasifikasi.getText().toString());
                                map.put("Berasal_Dari",berasal.getText().toString());
                                map.put("Keterangan",keterangan.getText().toString());


                                FirebaseDatabase.getInstance().getReference().child("Buku")
                                        .child(getRef(position).getKey()).updateChildren(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AdminKoleksiBuku.this, "Data berhasil diupdate", Toast.LENGTH_SHORT).show();
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
            public ViewHolderBuku onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                progressDialog.dismiss();
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_buku,parent,false);
                return new ViewHolderBuku(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }




    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminKoleksiBuku.this, AdminHome.class);
        startActivity(intent);
        finish();
    }
}