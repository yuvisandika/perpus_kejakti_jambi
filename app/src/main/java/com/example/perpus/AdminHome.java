package com.example.perpus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHome extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        CardView btnbuku =findViewById(R.id.card1); // buku
        btnbuku.setOnClickListener(this);

        CardView btnebook =findViewById(R.id.card2); //digital
        btnebook.setOnClickListener(this);

        CardView btnanggota =findViewById(R.id.card3); //anggota
        btnanggota.setOnClickListener(this);

        CardView btnpinjam =findViewById(R.id.card4); //peminjaman
        btnpinjam.setOnClickListener(this);

        CardView btnabout =findViewById(R.id.card5); //about
        btnabout.setOnClickListener(this);

        CardView bntkeluar =findViewById(R.id.card6); //exit
        bntkeluar.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        //btn buku
        switch (v.getId()) {
            case R.id.card1:
                Intent moveIntent = new Intent(AdminHome.this, AdminKoleksiBuku.class);
                startActivity(moveIntent);
                break;
        }
        //btn digital
        switch (v.getId()) {
            case R.id.card2:
                Intent moveIntent = new Intent(AdminHome.this, AdminKoleksiDigital.class);
                startActivity(moveIntent);
                break;
        }
        //btn anggota
        switch (v.getId()) {
            case R.id.card3:
                Intent moveIntent = new Intent(AdminHome.this, AdminAnggota.class);
                startActivity(moveIntent);
                break;
        }


        //peminjaman
        switch (v.getId()) {
            case R.id.card4:
                Intent moveIntent = new Intent(AdminHome.this, AdminPeminjaman.class);
                startActivity(moveIntent);
                break;
        }

        //about
        switch (v.getId()) {
            case R.id.card5:
                Intent moveIntent = new Intent(AdminHome.this, About.class);
                startActivity(moveIntent);
                break;
        }


        //bnt keluar
        switch (v.getId()) {
            case R.id.card6:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.logo_exit)
                        .setTitle("EXIT")
                        .setMessage("Kamu yakin ingin keluar?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
        }
    }

    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo_exit)
                .setTitle("EXIT")
                .setMessage("Kamu yakin ingin keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}