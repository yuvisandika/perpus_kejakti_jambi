package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdminTambahDigital extends AppCompatActivity {

    private static final int REQUEST_CODE_FILE = 101;

    private ImageView imgdigital; //gambar
    private ImageView camadd; //camera

    private EditText et_digitalnama;
    private Button btn_uploaddigital,btn_pilihdigital;

    Uri fileUri;
    boolean isFileAdded;

    DatabaseReference ref;
    StorageReference sref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tambah_digital);

        ref = FirebaseDatabase.getInstance().getReference().child("Ebook");
        sref = FirebaseStorage.getInstance().getReference().child("Ebook");

        et_digitalnama = findViewById(R.id.et_digitalnama);

        imgdigital = findViewById(R.id.imgdigital);

        btn_pilihdigital = findViewById(R.id.btn_pilihdigital);
        btn_pilihdigital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten=new Intent();
                inten.setType("application/pdf");
                inten.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(inten,REQUEST_CODE_FILE);

            }
        });

        btn_uploaddigital = findViewById(R.id.btn_uploaddigital);
        btn_uploaddigital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama=et_digitalnama.getText().toString();

                if (isFileAdded!=false && nama!=null)
                {
                    upload(nama);
                }
            }
        });

    }

    private void upload(final String nama) {



        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Data Sedang Di Upload . . . . ");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final String key=ref.push().getKey();
        sref.child(key+".pdf").putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sref.child(key +".pdf").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("Nama",nama);

                        hashMap.put("FileUrl",uri.toString());

                        ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),AdminKoleksiBuku.class));
                                Toast.makeText(AdminTambahDigital.this, "Upload Sukses", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //galery
        if (requestCode == REQUEST_CODE_FILE && data != null) {
            fileUri = data.getData();
            isFileAdded = true;
            imgdigital.setVisibility(View.VISIBLE);
        }
    }
    }



