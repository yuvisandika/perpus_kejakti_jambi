package com.example.perpus;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class AdminTambahPeminjaman extends AppCompatActivity {

    private EditText et_pinjamnama,
                     et_pinjamnotelp,
                     et_pinjambuku,
                     et_pinjamnourut,
                     et_tglkembali;

    private Button btn_uploadpinjam,et_tglpinjam;

    private TextView txt_tglpinjam;

    DatePickerDialog.OnDateSetListener setListener;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tambah_peminjaman);

        ref = FirebaseDatabase.getInstance().getReference().child("Peminjaman");

        et_pinjamnama = findViewById(R.id.et_pinjamnama);
        et_pinjamnotelp = findViewById(R.id.et_pinjamnotelp);
        et_pinjambuku = findViewById(R.id.et_pinjambuku);
        et_pinjamnourut = findViewById(R.id.et_pinjamnourut);
        et_tglkembali = findViewById(R.id.et_tglkembali);

        //pilih tanggal
        et_tglpinjam = findViewById(R.id.et_tglpinjam);
        txt_tglpinjam = findViewById(R.id.txt_tglpinjam);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        et_tglpinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AdminTambahPeminjaman.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
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
                txt_tglpinjam.setText(date);
            }
        };

        //-------------------

        btn_uploadpinjam=findViewById(R.id.btn_uploadpinjam);
        btn_uploadpinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cheker tidak boleh kosong
                if (TextUtils.isEmpty(et_pinjamnama.getText().toString()))
                {
                    Toast.makeText(AdminTambahPeminjaman.this, "Silahkan isi nama peminjam", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(et_pinjamnotelp.getText().toString()))
                {
                    Toast.makeText(AdminTambahPeminjaman.this, "Silahkan ini nomor telpon", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(et_pinjambuku.getText().toString()))
                {
                    Toast.makeText(AdminTambahPeminjaman.this, "Silahkan ini masukan nama buku", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(txt_tglpinjam.getText().toString()))
                {
                    Toast.makeText(AdminTambahPeminjaman.this, "Silahkan ini pilih tanggal peminjaman", Toast.LENGTH_SHORT).show();
                }

                //upload
                else
                {
                    final String nama_peminjam = et_pinjamnama.getText().toString();
                    final String notelp_peminjam = et_pinjamnotelp.getText().toString();
                    final String buku_peminjam = et_pinjambuku.getText().toString();
                    final String nourut = et_pinjamnourut.getText().toString();
                    final String tgl_peminjam = txt_tglpinjam.getText().toString();
                    final String tgl_kembali = et_tglkembali.getText().toString();

                    if (nama_peminjam!=null &&
                            notelp_peminjam!=null &&
                            buku_peminjam!=null &&
                            nourut!=null &&
                            tgl_peminjam!=null &&
                            tgl_kembali!=null)
                    {
                        uplaod(nama_peminjam,notelp_peminjam,buku_peminjam,nourut,tgl_peminjam,tgl_kembali);
                    }
                }
            }
        });


    }

    private void uplaod(String nama_peminjam, String notelp_peminjam, String buku_peminjam, String nourut, String tgl_peminjam, String tgl_kembali) {

        final String key=ref.push().getKey();

        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Data Sedang Di Upload . . . . ");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        HashMap hashMap = new HashMap();
        hashMap.put("Nama",nama_peminjam);
        hashMap.put("NoTelp",notelp_peminjam);
        hashMap.put("Buku",buku_peminjam);
        hashMap.put("No_urut",nourut);
        hashMap.put("Tgl_pinjam",tgl_peminjam);
        hashMap.put("Tgl_kembali","Belum Dikembalikan");


        ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),AdminPeminjaman.class));
                Toast.makeText(AdminTambahPeminjaman.this, "Upload Sukses", Toast.LENGTH_SHORT).show();

            }
        });

    }
}