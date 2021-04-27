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

public class AdminTambahKoleksiBuku extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private static final int REQUEST_CODE_IMAGE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

    private ImageView imageadd; //gambar
    private ImageView camadd; //camera

    private EditText
            txtjudul, txtnourut, txtkepengarangan,
            txtjilid,txtedisi,txtcetakan,txtisbn,txtissn,
            txttempat,txtpeberbit,txttahun,txtnoklasifikasi,
            txtberasal,txtketerangan; //edittext

    private TextView txtprogres;
    private Button btnupload;
    //spinner
    private Spinner spinner;
    private String item;
    MSpinner mspinner;
    TextView txtjenis;
    String[] jenis = {"Jenis Bahan Pustaka Belum Dipilih","BUKU (B)","PERATURAN (P)","MAJALAH (M)"};
    //---------------------
    Uri imageUri;
    boolean isImageAdded;

    DatabaseReference ref;
    StorageReference sref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tambah_koleksi_buku);



        imageadd = findViewById(R.id.imgbuku); //gambar
        camadd=findViewById(R.id.camadd); //camera

        //fild edit text
        txtjudul = findViewById(R.id.title);
        txtnourut = findViewById(R.id.nomor);
        txtkepengarangan = findViewById(R.id.kepengarangan);
        txtjilid = findViewById(R.id.jilid);
        txtedisi = findViewById(R.id.edisi);
        txtcetakan = findViewById(R.id.cetakan);
        txtisbn = findViewById(R.id.isbn);
        txtissn = findViewById(R.id.issn);
        txttempat = findViewById(R.id.tempat);
        txtpeberbit=findViewById(R.id.penerbit);
        txttahun = findViewById(R.id.tahun);
        txtnoklasifikasi = findViewById(R.id.klasifikasi);
        txtberasal = findViewById(R.id.berasal);
        txtketerangan = findViewById(R.id.keterangan);
        //-----------------------------------------
        //spinner
        txtjenis=findViewById(R.id.txtspinner);
        spinner = findViewById(R.id.jenis);
        spinner.setOnItemSelectedListener(this);
        mspinner = new MSpinner();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,jenis);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //---------------

        btnupload=findViewById(R.id.btnsave);


        //--------------------------

        ref = FirebaseDatabase.getInstance().getReference().child("Buku");
        sref = FirebaseStorage.getInstance().getReference().child("Image_Buku");

        //image add
        imageadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten=new Intent();
                inten.setType("image/*");
                inten.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(inten,REQUEST_CODE_IMAGE);

            }
        });
        //cam add
        camadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,CAMERA_REQUEST_CODE);

            }
        });


        //btn upload
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String judul=txtjudul.getText().toString();
                final String jenis=txtjenis.getText().toString();
                final String nourut=txtnourut.getText().toString();
                final String kepengarangan=txtkepengarangan.getText().toString();
                final String jilid=txtjilid.getText().toString();
                final String edisi=txtedisi.getText().toString();
                final String cetakan=txtcetakan.getText().toString();
                final String isbn=txtisbn.getText().toString();
                final String issn=txtissn.getText().toString();
                final String tempat=txttempat.getText().toString();
                final String penerbit=txtpeberbit.getText().toString();
                final String tahun=txttahun.getText().toString();
                final String klasifikasi=txtnoklasifikasi.getText().toString();
                final String berasal=txtberasal.getText().toString();
                final String keterangan=txtketerangan.getText().toString();

                if (isImageAdded!=false && judul!=null && jenis!=null && nourut!=null && kepengarangan!=null && jilid!=null && edisi!=null && cetakan!=null && isbn!=null && issn!=null && tempat!=null && penerbit!=null && tahun!=null && klasifikasi!=null && berasal!=null && keterangan!=null)
                {
                    upload(judul,jenis,nourut,kepengarangan,jilid,edisi,cetakan,isbn,issn,tempat,penerbit,tahun,klasifikasi,berasal,keterangan);
                }
            }
        });

    }

    private void upload(final String judul, final String jenis, final String nourut, final String kepengarangan, final String jilid, final String edisi, final String cetakan, final String isbn, final String issn, final String tempat, final String penerbit, final String tahun, final String klasifikasi, final String berasal, final String keterangan) {



        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Data Sedang Di Upload . . . . ");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        final String key=ref.push().getKey();
        sref.child(key+".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sref.child(key +".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("Judul_Buku",judul);
                        hashMap.put("Jenis_Bahan",jenis);
                        hashMap.put("No_Urut",nourut);
                        hashMap.put("Kepengarangan",kepengarangan);
                        hashMap.put("Jilid",jilid);
                        hashMap.put("Edisi",edisi);
                        hashMap.put("Cetakan",cetakan);
                        hashMap.put("Isbn",isbn);
                        hashMap.put("Issn",issn);
                        hashMap.put("Tempat_Terbit",tempat);
                        hashMap.put("Penerbit",penerbit);
                        hashMap.put("Tahun_Terbit",tahun);
                        hashMap.put("Klasifikasi",klasifikasi);
                        hashMap.put("Berasal_Dari",berasal);
                        hashMap.put("Keterangan",keterangan);

                        hashMap.put("ImageUrl",uri.toString());

                        ref.child(key).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),AdminKoleksiBuku.class));
                                Toast.makeText(AdminTambahKoleksiBuku.this, "Upload Sukses", Toast.LENGTH_SHORT).show();

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
        if (requestCode==REQUEST_CODE_IMAGE && data!=null)
        {
            imageUri=data.getData();
            isImageAdded=true;
            imageadd.setImageURI(imageUri);
        }
        //camera
        if (requestCode== CAMERA_REQUEST_CODE){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            imageadd.setImageBitmap(image);
        }
    }




    //spinner--------------------------------
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item =spinner.getSelectedItem().toString();
        txtjenis.setText(item);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}