package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextView txtid,txtpw;

    EditText id, pw;
    Button btn_login, btn_user;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        ref= FirebaseDatabase.getInstance().getReference().child("ID");

        txtid = findViewById(R.id.txtid);
        txtpw = findViewById(R.id.txtpw);

        id = findViewById(R.id.login_id);
        pw = findViewById(R.id.login_pw);

        btn_login = findViewById(R.id.btn_login);



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String userid = dataSnapshot.child("id").getValue().toString();
                String userpw = dataSnapshot.child("pw").getValue().toString();
                txtid.setText(userid);
                txtpw.setText(userpw);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void login (View v)
    {
        String setid = id.getText().toString();
        String setpw = pw.getText().toString();
        String userid = txtid.getText().toString();
        String userpw = txtpw.getText().toString();

        if (setid.equals(userid) && setpw.equals(userpw)){
            Intent intent = new Intent(Login.this,AdminHome.class);
            startActivity(intent);
        }

        else if (setid.equals("") || setpw.equals(""))
        {
            Toast.makeText(this, "HARAP MASUKAN ID DAN PASSWORD", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "ID DAN PASSWORD SALAH", Toast.LENGTH_SHORT).show();
        }

    }
}