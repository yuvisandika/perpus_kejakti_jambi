package com.example.perpus;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserHome extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        CardView btnbuku =findViewById(R.id.usercard1); // buku
        btnbuku.setOnClickListener(this);

        CardView btndigital =findViewById(R.id.usercard2); // digital
        btndigital.setOnClickListener(this);

        CardView btnabout =findViewById(R.id.usercard3); // about
        btnabout.setOnClickListener(this);

        CardView btnexit =findViewById(R.id.usercard4); // keluar
        btnexit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //menu 1 (koleksi buku)
        switch (v.getId()) {
            case R.id.usercard1:
                Intent moveIntent = new Intent(UserHome.this, UserKoleksiBuku.class);
                startActivity(moveIntent);
                break;
        }
        //menu 2 (koleksi digital)
        switch (v.getId()) {
            case R.id.usercard2:
                Intent moveIntent = new Intent(UserHome.this, UserKoleksiDigital.class);
                startActivity(moveIntent);
                break;
        }
        //menu 3 (koleksi digital)
        switch (v.getId()) {
            case R.id.usercard3:
                Intent moveIntent = new Intent(UserHome.this, About.class);
                startActivity(moveIntent);
                break;
        }




        //keluar
        switch (v.getId()) {
            case R.id.usercard4:
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