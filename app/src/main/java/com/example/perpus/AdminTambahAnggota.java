package com.example.perpus;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AdminTambahAnggota extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private ImageView imageadd;
    private EditText txtnama, txtemail, txtnip, txtnotelp, txtalamat;
    private TextView txtprogres, txtjenkel;
    private ProgressBar progresbar;
    private Button btnupload;

    Uri imageUri;
    boolean isImageAdded;

    DatabaseReference ref;
    StorageReference sref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tambah_anggota);

        imageadd = findViewById(R.id.imganggota);
        txtnama = findViewById(R.id.nama);
        txtjenkel = findViewById(R.id.jenkel);
        txtemail = findViewById(R.id.email);
        txtnip = findViewById(R.id.nip);
        txtnotelp = findViewById(R.id.notelp);
        txtalamat = findViewById(R.id.alamat);

        //progressbar
        txtprogres = findViewById(R.id.txtprogres);
        progresbar = findViewById(R.id.progresbar);
        btnupload = findViewById(R.id.btnsave);

        //--------------------------


        ref = FirebaseDatabase.getInstance().getReference().child("Anggota");
        sref = FirebaseStorage.getInstance().getReference().child("Image_Anggota");

        imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent();
                inten.setType("image/*");
                inten.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(inten, REQUEST_CODE_IMAGE);

            }
        });
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nama = txtnama.getText().toString();
                final String jenkel = txtjenkel.getText().toString();
                final String email = txtemail.getText().toString();
                final String nip = txtnip.getText().toString();
                final String notelp = txtnotelp.getText().toString();
                final String alamat = txtalamat.getText().toString();
                if (isImageAdded != false && nama != null && jenkel != null && email != null && nip != null && notelp != null && alamat != null) {
                    upload(nama, jenkel, email, nip, notelp, alamat);
                } else {
                    Toast.makeText(AdminTambahAnggota.this, "Gambar Belum DItambah", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void upload(final String nama, final String jenkel, final String email, final String nip, final String notelp, final String alamat) {
        txtprogres.setVisibility(View.VISIBLE);
        progresbar.setVisibility(View.VISIBLE);

        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Data Sedang Di Upload . . . . ");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        final String key = ref.push().getKey();
        sref.child(key + ".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sref.child(key + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("Nama", nama);
                        hashMap.put("Jenis_Kelamin", jenkel);
                        hashMap.put("Email", email);
                        hashMap.put("Nip", nip);
                        hashMap.put("Nomor_Telpon", notelp);
                        hashMap.put("Alamat", alamat);
                        hashMap.put("ImageUrl", uri.toString());

                        ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), AdminAnggota.class));
                                Toast.makeText(AdminTambahAnggota.this, "Sukses", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (taskSnapshot.getBytesTransferred() * 100) / taskSnapshot.getTotalByteCount();
                progresbar.setProgress((int) progress);
                txtprogres.setText(progress + " %");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE && data != null) {
            imageUri = data.getData();
            isImageAdded = true;
            imageadd.setImageURI(imageUri);
        }

    }


}
