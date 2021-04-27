package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminAnggotaView extends AppCompatActivity {

    private ImageView imganggota;
    TextView nama;
    TextView jenkel;
    TextView email;
    TextView nip;
    TextView notelp;
    TextView alamat;

    String nomor;
    Button btn_tlpanggota;

    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_anggota_view);

        imganggota=findViewById(R.id.imganggota);
        nama=findViewById(R.id.nama);
        jenkel=findViewById(R.id.jenkel);
        email=findViewById(R.id.email);
        nip=findViewById(R.id.nip);
        notelp=findViewById(R.id.notelp);
        alamat=findViewById(R.id.alamat);

        btn_tlpanggota = findViewById(R.id.btn_tlpanggota);

        //data referen
        ref= FirebaseDatabase.getInstance().getReference().child("Anggota");
        String AKey = getIntent().getStringExtra("AKey");

        ref.child(AKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String Alamat= dataSnapshot.child("Alamat").getValue().toString();
                    String Email= dataSnapshot.child("Email").getValue().toString();
                    String ImageUrl= dataSnapshot.child("ImageUrl").getValue().toString();
                    String Jenis_Kelamin= dataSnapshot.child("Jenis_Kelamin").getValue().toString();
                    String Nama = dataSnapshot.child("Nama").getValue().toString();
                    String Nip= dataSnapshot.child("Nip").getValue().toString();
                    String Nomor_Telpon= dataSnapshot.child("Nomor_Telpon").getValue().toString();

                    nomor = dataSnapshot.child("Nomor_Telpon").getValue().toString();

                    //implementation
                    Picasso.get().load(ImageUrl).into(imganggota);
                    alamat.setText(Alamat);
                    email.setText(Email);
                    jenkel.setText(Jenis_Kelamin);
                    nama.setText(Nama);
                    nip.setText(Nip);
                    notelp.setText(Nomor_Telpon);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onDialBUtton(View view) {
        String number = nomor;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+number));
        startActivity(intent);
    }
}
