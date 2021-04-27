package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AdminEditPeminjaman extends AppCompatActivity {

    private Button et_tglkembali,btn_editpinjam;
    private TextView txtpinjambuku,txt_nama,txt_tglpinjam,txt_tglkembali;

    DatePickerDialog.OnDateSetListener setListener;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_peminjaman);

        //popup
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.5));
        //-----

        //textview
        txtpinjambuku = findViewById(R.id.txtpinjambuku);
        txt_nama = findViewById(R.id.txt_nama);
        txt_tglpinjam = findViewById(R.id.txt_tglpinjam);
        //---


        btn_editpinjam = findViewById(R.id.btn_editpinjam);

        //pilih ygl kembali
        et_tglkembali = findViewById(R.id.et_tglkembali);
        txt_tglkembali = findViewById(R.id.txt_tglkembali);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        et_tglkembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdminEditPeminjaman.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month+1;
                String date = day+"/"+month+"/"+year;
                txt_tglkembali.setText(date);
            }
        };

        //data ref
        ref = FirebaseDatabase.getInstance().getReference().child("Peminjaman");
        final String PKey = getIntent().getStringExtra("PKey");


        btn_editpinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,Object> map = new HashMap<>();
                map.put("Tgl_kembali",txt_tglkembali.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("Peminjaman")
                        .child(PKey).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Intent intent = new Intent(AdminEditPeminjaman.this,AdminPeminjaman.class);
                                startActivity(intent);
                                Toast.makeText(AdminEditPeminjaman.this, "Data Berhasil Diperbaharui", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });

            }
        });


        ref.child(PKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists())
                {
                    String nama_peminjam= dataSnapshot.child("Nama").getValue().toString();
                    String buku_peminjam= dataSnapshot.child("Buku").getValue().toString();
                    String tgl_peminjam= dataSnapshot.child("Tgl_pinjam").getValue().toString();
                    String tgl_kembali = dataSnapshot.child("Tgl_kembali").getValue().toString();

                    //implementation
                    txtpinjambuku.setText(buku_peminjam);
                    txt_nama.setText(nama_peminjam);
                    txt_tglpinjam.setText(tgl_peminjam);
                    txt_tglkembali.setText(tgl_kembali);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}