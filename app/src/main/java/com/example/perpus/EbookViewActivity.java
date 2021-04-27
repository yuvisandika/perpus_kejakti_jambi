package com.example.perpus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.net.URLEncoder;

public class EbookViewActivity extends AppCompatActivity {

    WebView webview;

    TextView txtweb;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook_view);

        //progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sedang Memuat PDF . . . . ");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        txtweb = findViewById(R.id.txtweb);

        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);

        ref = FirebaseDatabase.getInstance().getReference().child("Ebook");
        String EbookKey = getIntent().getStringExtra("EbookKey");



        ref.child(EbookKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String FileUrl = dataSnapshot.child("FileUrl").getValue().toString();

                    txtweb.setText(FileUrl);

                    webview.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                            super.onPageStarted(view, url, favicon);

                        }

                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            progressDialog.dismiss();

                        }
                    });

                    String url = "";
                    try {
                        url = URLEncoder.encode(FileUrl, "UTF-8");
                    } catch (Exception ex) {
                    }

                    webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}