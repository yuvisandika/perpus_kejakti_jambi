package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BukuViewActivity extends AppCompatActivity {

    private ImageView imgbuku;
    TextView judul;
    TextView jenis;
    TextView nourut;
    TextView kepengarangan;
    TextView jilid;
    TextView edisi;
    TextView cetakan;
    TextView isbn;
    TextView issn;
    TextView tempat;
    TextView penerbit;
    TextView tahun;
    TextView klasifikasi;
    TextView berasal;
    TextView keterangan;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_view);

        imgbuku=findViewById(R.id.imgbuku);
        judul=findViewById(R.id.title);
        jenis=findViewById(R.id.jenis);
        nourut=findViewById(R.id.nomor);
        kepengarangan=findViewById(R.id.kepengarangan);
        jilid=findViewById(R.id.jilid);
        edisi=findViewById(R.id.edisi);
        cetakan=findViewById(R.id.cetakan);
        isbn=findViewById(R.id.isbn);
        issn=findViewById(R.id.issn);
        tempat=findViewById(R.id.tempat);
        penerbit=findViewById(R.id.penerbit);
        tahun=findViewById(R.id.tahun);
        klasifikasi=findViewById(R.id.klasifikasi);
        berasal=findViewById(R.id.berasal);
        keterangan=findViewById(R.id.keterangan);

        ref= FirebaseDatabase.getInstance().getReference().child("Buku");
        String BukuKey=getIntent().getStringExtra("BukuKey");

        ref.child(BukuKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String Judul_Buku= dataSnapshot.child("Judul_Buku").getValue().toString();
                    String Jenis_Bahan= dataSnapshot.child("Jenis_Bahan").getValue().toString();
                    String No_Urut= dataSnapshot.child("No_Urut").getValue().toString();
                    String Kepengarangan= dataSnapshot.child("Kepengarangan").getValue().toString();
                    String Jilid= dataSnapshot.child("Jilid").getValue().toString();
                    String Edisi= dataSnapshot.child("Edisi").getValue().toString();
                    String Cetakan= dataSnapshot.child("Cetakan").getValue().toString();
                    String Isbn= dataSnapshot.child("Isbn").getValue().toString();
                    String Issn= dataSnapshot.child("Issn").getValue().toString();
                    String Tempat_Terbit= dataSnapshot.child("Tempat_Terbit").getValue().toString();
                    String Penerbit= dataSnapshot.child("Penerbit").getValue().toString();
                    String Tahun_Terbit= dataSnapshot.child("Tahun_Terbit").getValue().toString();
                    String Klasifikasi= dataSnapshot.child("Klasifikasi").getValue().toString();
                    String Berasal_Dari= dataSnapshot.child("Berasal_Dari").getValue().toString();
                    String Keterangan= dataSnapshot.child("Keterangan").getValue().toString();
                    String ImageUrl= dataSnapshot.child("ImageUrl").getValue().toString();

                    //implementation
                    Picasso.get().load(ImageUrl).into(imgbuku);
                    judul.setText(Judul_Buku);
                    jenis.setText(Jenis_Bahan);
                    nourut.setText(No_Urut);
                    kepengarangan.setText(Kepengarangan);
                    jilid.setText(Jilid);
                    edisi.setText(Edisi);
                    cetakan.setText(Cetakan);
                    isbn.setText(Isbn);
                    issn.setText(Issn);
                    tempat.setText(Tempat_Terbit);
                    penerbit.setText(Penerbit);
                    tahun.setText(Tahun_Terbit);
                    klasifikasi.setText(Klasifikasi);
                    berasal.setText(Berasal_Dari);
                    keterangan.setText(Keterangan);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}